package com.profach.viewmodels.result

import android.content.res.Resources
import com.profach.R
import com.profach.viewmodels.ResultViewModel

class KlimovResultViewModel(answers: IntArray) : ResultViewModel(answers) {

    val nature: Float
        get() = calculateGroup(NATURE_IDS)

    val tech: Float
        get() = calculateGroup(TECH_IDS)

    val people: Float
        get() = calculateGroup(PEOPLE_IDS)

    val logic: Float
        get() = calculateGroup(LOGIC_IDS)

    val art: Float
        get() = calculateGroup(ART_IDS)

    override fun answerToValue(value: Int, id: Int, groupIds: HashMap<Int, String>): Int {
        return when(groupIds.get(id)!!) {
            A_TYPE -> when(value) {
                0 -> 1
                1 -> 0
                else -> throw IllegalArgumentException()
            }
            B_TYPE -> when(value) {
                1 -> 0
                0 -> 1
                else -> throw IllegalArgumentException()
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun buildShareText(resources: Resources): String {
        return """
            |${String.format(resources.getString(R.string.klimov_nature), nature)}
            |${String.format(resources.getString(R.string.klimov_tech), tech)}
            |${String.format(resources.getString(R.string.klimov_people), people)}
            |${String.format(resources.getString(R.string.klimov_logic), logic)}
            |${String.format(resources.getString(R.string.klimov_art), art)}
        """.trimMargin()
    }

    companion object {
        private const val A_TYPE = "a"
        private const val B_TYPE = "b"

        private val NATURE_IDS = hashMapOf(
            0 to A_TYPE,
            2 to B_TYPE,
            5 to A_TYPE,
            9 to A_TYPE,
            10 to A_TYPE,
            12 to B_TYPE,
            15 to A_TYPE,
            19 to A_TYPE
        )
        private val TECH_IDS = hashMapOf(
            0 to B_TYPE,
            3 to A_TYPE,
            6 to B_TYPE,
            8 to A_TYPE,
            10 to B_TYPE,
            13 to A_TYPE,
            16 to B_TYPE,
            18 to A_TYPE
        )
        private val PEOPLE_IDS = hashMapOf(
            1 to A_TYPE,
            3 to B_TYPE,
            5 to B_TYPE,
            7 to  A_TYPE,
            11 to A_TYPE,
            13 to B_TYPE,
            15 to B_TYPE,
            17 to A_TYPE
        )
        private val LOGIC_IDS = hashMapOf(
            1 to B_TYPE,
            4 to A_TYPE,
            8 to B_TYPE,
            9 to B_TYPE,
            11 to B_TYPE,
            14 to A_TYPE,
            18 to B_TYPE,
            19 to B_TYPE
        )
        private val ART_IDS = hashMapOf(
            2 to A_TYPE,
            4 to B_TYPE,
            6 to A_TYPE,
            7 to B_TYPE,
            12 to A_TYPE,
            14 to B_TYPE,
            16 to A_TYPE,
            17 to B_TYPE
        )
    }
}
