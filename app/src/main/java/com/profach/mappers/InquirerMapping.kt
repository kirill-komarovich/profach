package com.profach.mappers

import com.profach.R
import com.profach.entities.Inquirer
import com.profach.viewmodels.ResultViewModel
import com.profach.viewmodels.result.*

object InquirerMapping {
    fun resultViewModelFor(type: Inquirer.Type, answers: IntArray) : ResultViewModel {
        return when(type) {
            Inquirer.Type.YOVAYSHI -> YovayshiResultViewModel(answers)
            Inquirer.Type.KLIMOV -> KlimovResultViewModel(answers)
            Inquirer.Type.HOLOMSTOCK -> HolomstockResultViewModel(answers)
            Inquirer.Type.HOLLAND -> HollandResultViewModel(answers)
        }
    }

    fun resultFragmentLayout(type: Inquirer.Type): Int {
        return when(type) {
            Inquirer.Type.YOVAYSHI -> R.layout.yovayshi_result_fragment
            Inquirer.Type.KLIMOV -> R.layout.klimov_result_fragment
            Inquirer.Type.HOLOMSTOCK -> R.layout.holomstock_result_fragment
            Inquirer.Type.HOLLAND -> R.layout.holland_result_fragment
        }
    }
}