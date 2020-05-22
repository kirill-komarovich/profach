package com.profach.viewmodels

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.profach.entities.User
import com.profach.managers.AccountManager
import com.profach.repositories.UserRepository
import kotlinx.coroutines.launch

class UpgradeAccountViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val userRepository = UserRepository()

    private val _isUpgraded = MutableLiveData(false)
    val isUpgraded: LiveData<Boolean>
        get() = _isUpgraded

    fun updateRole() {
        viewModelScope.launch {
            userRepository.updateUserRole(auth.currentUser!!.uid, User.Role.TEACHER)
            AccountManager.currentUserProfile!!.role = User.Role.TEACHER
            _isUpgraded.value = true
        }
    }
}