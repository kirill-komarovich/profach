package com.profach.viewmodels

import androidx.lifecycle.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.profach.entities.User
import com.profach.managers.AccountManager
import com.profach.repositories.UserRepository
import kotlinx.coroutines.launch

class StudentsViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _listQuery = MutableLiveData<Query>()
    val listQuery: LiveData<Query>
        get() = _listQuery

    fun initialQuery(): Query {
        val userId = AccountManager.currentUser!!.uid
        _listQuery.value = userRepository.buildStudentsListQuery(userId)
        return _listQuery.value!!
    }

    fun buildPagingOptions(query: Query, lifecycleOwner: LifecycleOwner? = null): FirestoreRecyclerOptions<User> {
        val builder = FirestoreRecyclerOptions.Builder<User>()
        lifecycleOwner?.let { builder.setLifecycleOwner(it) }
        builder.setQuery(query, User::class.java)

        return builder.build()
    }

    fun removeStudent(id: String) {
        viewModelScope.launch {
            userRepository.removeStudent(AccountManager.currentUser!!.uid, id)
        }
    }
}