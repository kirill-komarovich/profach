package com.profach.viewmodels.result

import android.content.res.Resources
import com.profach.R
import com.profach.viewmodels.ResultViewModel

class YovayshiResultViewModel(answers: IntArray) : ResultViewModel(answers) {
    val art: Float
        get() = calculateGroup(ART_IDS)

    val tech: Float
        get() = calculateGroup(TECH_IDS)

    val people: Float
        get() = calculateGroup(PEOPLE_IDS)

    val mentalWork: Float
        get() = calculateGroup(MENTAL_WORK_IDS)

    val physicalWork: Float
        get() = calculateGroup(PHYSICAL_WORK_IDS)

    val material: Float
        get() = calculateGroup(MATERIAL_IDS)

    override fun answerToValue(value: Int, id: Int, groupIds: HashMap<Int, String>): Int {
        return when(groupIds.get(id)!!) {
            A_TYPE -> when(value) {
                0 -> 3
                1 -> 2
                2 -> 1
                3 -> 0
                else -> throw IllegalArgumentException()
            }
            B_TYPE -> when(value) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
                else -> throw IllegalArgumentException()
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun buildShareText(resources: Resources): String {
        return """
            |${String.format(resources.getString(R.string.yovayshi_art), art)}
            |${String.format(resources.getString(R.string.yovayshi_tech), tech)}
            |${String.format(resources.getString(R.string.yovayshi_people), people)}
            |${String.format(resources.getString(R.string.yovayshi_mental_work), mentalWork)}
            |${String.format(resources.getString(R.string.yovayshi_physical_work), physicalWork)}
            |${String.format(resources.getString(R.string.yovayshi_material), material)}
        """.trimMargin()
    }

    companion object {
        private const val A_TYPE = "a"
        private const val B_TYPE = "b"

        private val ART_IDS = hashMapOf(
            0 to A_TYPE,
            4 to B_TYPE,
            7 to A_TYPE,
            9 to A_TYPE,
            10 to B_TYPE,
            16 to A_TYPE,
            20 to A_TYPE,
            22 to A_TYPE,
            23 to B_TYPE,
            27 to A_TYPE
        )
        private val TECH_IDS = hashMapOf(
            0 to B_TYPE,
            2 to B_TYPE,
            5 to A_TYPE,
            7 to B_TYPE,
            11 to A_TYPE,
            13 to A_TYPE,
            14 to B_TYPE,
            24 to A_TYPE,
            25 to A_TYPE,
            28 to B_TYPE
        )
        private val PEOPLE_IDS = hashMapOf(
            1 to A_TYPE,
            3 to A_TYPE,
            5 to B_TYPE,
            8 to  A_TYPE,
            11 to B_TYPE,
            15 to A_TYPE,
            16 to B_TYPE,
            18 to B_TYPE,
            22 to B_TYPE,
            27 to B_TYPE
        )
        private val MENTAL_WORK_IDS = hashMapOf(
            3 to B_TYPE,
            6 to A_TYPE,
            9 to B_TYPE,
            12 to A_TYPE,
            13 to B_TYPE,
            17 to A_TYPE,
            19 to A_TYPE,
            20 to B_TYPE,
            25 to B_TYPE,
            29 to A_TYPE
        )
        private val PHYSICAL_WORK_IDS = hashMapOf(
            1 to B_TYPE,
            4 to A_TYPE,
            12 to B_TYPE,
            14 to A_TYPE,
            17 to B_TYPE,
            19 to B_TYPE,
            21 to A_TYPE,
            23 to A_TYPE,
            24 to B_TYPE,
            26 to A_TYPE
        )
        private val MATERIAL_IDS = hashMapOf(
            2 to A_TYPE,
            6 to B_TYPE,
            8 to B_TYPE,
            10 to A_TYPE,
            15 to B_TYPE,
            18 to A_TYPE,
            21 to B_TYPE,
            26 to B_TYPE,
            28 to A_TYPE,
            29 to B_TYPE
        )
    }
}
