package com.profach.viewmodels

import android.content.res.Resources
import androidx.lifecycle.*
import com.profach.entities.Inquirer
import com.profach.mappers.InquirerMapping
import com.profach.repositories.InquirerRepository
import com.profach.viewmodels.result.KlimovResultViewModel
import com.profach.viewmodels.result.YovayshiResultViewModel
import kotlinx.coroutines.launch

abstract class ResultViewModel(answers: IntArray) : ViewModel() {
    private val _answers = MutableLiveData<List<Int>>()
    val answers: LiveData<List<Int>>
        get() = _answers

    init {
        _answers.value = answers.toList()
    }

    protected open fun calculateGroup(groupIds: List<Int>, offset: Int = 0): Float {
        val groupItems = answers.value!!.filterIndexed { index, _ -> groupIds.contains(index) }
        val values = groupItems.mapIndexed { index, value ->  answerToValue(value, index) + offset }
        return values.sum().toFloat()
    }

    protected open fun calculateGroup(groupIds: HashMap<Int, String>): Float {
        val items = answers.value!!.mapIndexed { index, value -> index to value }.toMap()
        val filteredItems = items.filterKeys { groupIds.containsKey(it) }
        val values = filteredItems.map { answerToValue(it.value, it.key, groupIds) }

        return values.sum().toFloat()
    }

    protected open fun answerToValue(value: Int, id: Int): Int = value
    protected open fun answerToValue(value: Int, id: Int, groupIds: HashMap<Int, String>): Int = value

    abstract fun buildShareText(resources: Resources) : String

    class Factory(private val inquirerType: Inquirer.Type, private val answers: IntArray) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (ResultViewModel::class.java.isAssignableFrom(modelClass)) {
                return InquirerMapping.resultViewModelFor(inquirerType, answers) as T
            }
            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}