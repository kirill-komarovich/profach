package com.profach.viewmodels

import androidx.lifecycle.*
import com.profach.entities.Inquirer
import com.profach.repositories.InquirerRepository
import kotlinx.coroutines.launch

class WalkthroughViewModel(inquirerType: Inquirer.Type, private val onFinished: (answers: List<Int>) -> Unit) : ViewModel() {
    private val inquirerRepository = InquirerRepository()

    private val _inquirerType = MutableLiveData<Inquirer.Type>()
    val inquirerType: LiveData<Inquirer.Type>
        get() = _inquirerType

    private val _currentQuestion = MutableLiveData(0)
    val currentQuestion: LiveData<Int>
        get() = _currentQuestion

    private val _answers = MutableLiveData<MutableList<Int>>()
    val answers: LiveData<MutableList<Int>>
        get() = _answers

    init {
        _inquirerType.value = inquirerType
        _answers.value = MutableList(questionsCount()) { -1 }
    }

    fun nextQuestion() {
        val nextQuestion = currentQuestion.value!!.plus(1)
        if (nextQuestion == questionsCount()) {
            onFinished(answers.value!!.toList())
        } else {
            _currentQuestion.value = nextQuestion
        }
    }

    fun previousQuestion() {
        _currentQuestion.value = _currentQuestion.value?.plus(-1)
    }

    fun setCurrentQuestionValue(value: Int) {
        answers.value!![currentQuestion.value!!] = value
    }

    fun questionsCount(): Int {
        return when(inquirerType.value!!) {
            Inquirer.Type.YOVAYSHI -> 30
            Inquirer.Type.KLIMOV -> 20
            Inquirer.Type.HOLOMSTOCK -> 174
            Inquirer.Type.HOLLAND -> 42
        }
    }

    fun saveInquire(inquirer: Inquirer) {
        viewModelScope.launch {
            inquirerRepository.save(inquirer)
        }
    }


    class Factory(private val inquirerType: Inquirer.Type, private val onFinished: (answers: List<Int>) -> Unit) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WalkthroughViewModel::class.java)) {
                return WalkthroughViewModel(inquirerType, onFinished) as T
            }

            throw IllegalAccessException("Unknown ViewModel class")
        }
    }
}