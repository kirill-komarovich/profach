package com.profach.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.Query
import com.profach.entities.Inquirer
import com.profach.repositories.InquirerRepository

class HistoryViewModel : ViewModel() {
    private val inquirerRepository = InquirerRepository()

    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setPageSize(PER_PAGE)
        .build()

    private val _listQuery = MutableLiveData<Query>()
    val listQuery: LiveData<Query>
        get() = _listQuery

    fun initialQuery(userId: String): Query {
        _listQuery.value = inquirerRepository.buildListQuery(listOf(userId))
        return _listQuery.value!!
    }

    fun buildPagingOptions(query: Query, lifecycleOwner: LifecycleOwner? = null): FirestorePagingOptions<Inquirer> {
        val builder = FirestorePagingOptions.Builder<Inquirer>()
        lifecycleOwner?.let { builder.setLifecycleOwner(it) }
        builder.setQuery(query, pagedListConfig, Inquirer::class.java)

        return builder.build()
    }

    companion object {
        private const val PER_PAGE = 15
        private const val PREFETCH_DISTANCE = 2
    }
}
