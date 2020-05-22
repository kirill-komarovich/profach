package com.profach.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.profach.entities.Inquirer

class InquirerViewModel : ViewModel() {
    private var _inquirers = MutableLiveData(Inquirer.supported())
    val inquirers: LiveData<List<Inquirer>>
        get() = _inquirers
}