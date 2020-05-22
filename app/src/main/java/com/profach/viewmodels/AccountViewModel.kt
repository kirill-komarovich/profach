package com.profach.viewmodels

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.profach.R
import com.profach.managers.AccountManager
import com.profach.repositories.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val studentsRepository = UserRepository()
    private val userRepository = UserRepository()

    private val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(application, googleSignInOptions)

    private val _authenticationType = MutableLiveData<AuthenticationTypes>()

    private val _currentUser = MutableLiveData<FirebaseUser?>(AccountManager.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    init {
        auth.addAuthStateListener { firebaseAuth ->
            viewModelScope.launch {
                val user = firebaseAuth.currentUser

                user?.let {
                    AccountManager.currentUserProfile = userRepository.findProfile(it.uid)
                } ?: run {
                    _authenticationType.value = null
                }
                _currentUser.value = user
            }
        }
    }

    fun signInUser(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signOutUser() {
        viewModelScope.launch {
            socialSignOut()
            auth.signOut()
        }
    }

    private suspend fun socialSignOut() {
        when (_authenticationType.value) {
            AuthenticationTypes.GOOGLE -> googleSignInClient.signOut().await()
        }
    }

    fun authenticateWithGoogle(data: Intent?) {
        viewModelScope.launch {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("Login", "Google sign in failed", e)
            }
        }
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        try {
            auth.signInWithCredential(credential).await()
            _authenticationType.value = AuthenticationTypes.GOOGLE
        } catch (e: FirebaseAuthException) {
            Log.w("AuthViewModel", "signInWithCredential:failure", e)
        }
    }

    fun addStudent(email: String, group: String) {
        viewModelScope.launch {
            studentsRepository.addStudent(AccountManager.currentUser!!.uid, email, group)
        }
    }

    enum class AuthenticationTypes {
        GOOGLE
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
                return AccountViewModel(application) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}