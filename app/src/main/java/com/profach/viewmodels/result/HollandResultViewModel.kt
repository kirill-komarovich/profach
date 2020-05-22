package com.profach.viewmodels.result

import android.content.res.Resources
import com.profach.R
import com.profach.viewmodels.ResultViewModel

class HollandResultViewModel(answers: IntArray) : ResultViewModel(answers) {

    val realistic: Float
        get() = calculateGroup(REALISTIC_IDS)

    val intellectual: Float
        get() = calculateGroup(INTELLECTUAL_IDS)

    val social: Float
        get() = calculateGroup(SOCIAL_IDS)

    val conventional: Float
        get() = calculateGroup(CONVENTIONAL_IDS)

    val enterprising: Float
        get() = calculateGroup(ENTERPRISING_IDS)

    val artistic: Float
        get() = calculateGroup(ARTISTIC_IDS)

    override fun calculateGroup(groupIds: HashMap<Int, String>): Float {
        return super.calculateGroup(groupIds) / groupIds.keys.size * 100
    }

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
            |${String.format(resources.getString(R.string.holland_realistic), realistic)}
            |${String.format(resources.getString(R.string.holland_intellectual), intellectual)}
            |${String.format(resources.getString(R.string.holland_social), social)}
            |${String.format(resources.getString(R.string.holland_conventional), conventional)}
            |${String.format(resources.getString(R.string.holland_enterprising), enterprising)}
            |${String.format(resources.getString(R.string.holland_artistic), artistic)}
            
            |${resources.getString(R.string.holland_result_details)}
        """.trimMargin()
    }

    companion object {
        private const val A_TYPE = "a"
        private const val B_TYPE = "b"

        private val REALISTIC_IDS = hashMapOf(
            1 to A_TYPE,
            2 to A_TYPE,
            3 to A_TYPE,
            4 to A_TYPE,
            5 to A_TYPE,
            16 to A_TYPE,
            17 to A_TYPE,
            18 to A_TYPE,
            19 to A_TYPE,
            20 to A_TYPE,
            31 to A_TYPE,
            32 to A_TYPE,
            33 to A_TYPE,
            34 to A_TYPE,
            35 to A_TYPE
        )

        private val INTELLECTUAL_IDS = hashMapOf(
            1 to B_TYPE,
            6 to A_TYPE,
            7 to A_TYPE,
            8 to A_TYPE,
            9 to A_TYPE,
            16 to B_TYPE,
            21 to A_TYPE,
            22 to A_TYPE,
            23 to A_TYPE,
            24 to A_TYPE,
            31 to B_TYPE,
            36 to A_TYPE,
            37 to A_TYPE,
            38 to A_TYPE,
            39 to A_TYPE
        )

        private val SOCIAL_IDS = hashMapOf(
            2 to B_TYPE,
            6 to B_TYPE,
            10 to A_TYPE,
            11 to A_TYPE,
            12 to A_TYPE,
            17 to B_TYPE,
            21 to B_TYPE,
            25 to A_TYPE,
            26 to A_TYPE,
            27 to A_TYPE,
            32 to B_TYPE,
            36 to B_TYPE,
            40 to A_TYPE,
            41 to A_TYPE,
            42 to A_TYPE
        )

        private val CONVENTIONAL_IDS = hashMapOf(
            3 to B_TYPE,
            7 to B_TYPE,
            10 to B_TYPE,
            13 to A_TYPE,
            14 to A_TYPE,
            18 to B_TYPE,
            22 to B_TYPE,
            25 to B_TYPE,
            28 to A_TYPE,
            29 to A_TYPE,
            33 to B_TYPE,
            37 to B_TYPE,
            40 to B_TYPE,
            43 to A_TYPE
        )

        private val ENTERPRISING_IDS = hashMapOf(
            4 to B_TYPE,
            8 to B_TYPE,
            11 to B_TYPE,
            13 to B_TYPE,
            15 to A_TYPE,
            19 to B_TYPE,
            23 to B_TYPE,
            26 to B_TYPE,
            28 to B_TYPE,
            30 to A_TYPE,
            34 to B_TYPE,
            38 to B_TYPE,
            41 to B_TYPE,
            43 to B_TYPE
        )

        private val ARTISTIC_IDS = hashMapOf(
            5 to B_TYPE,
            9 to B_TYPE,
            12 to B_TYPE,
            14 to B_TYPE,
            15 to B_TYPE,
            20 to B_TYPE,
            24 to B_TYPE,
            27 to B_TYPE,
            29 to B_TYPE,
            30 to B_TYPE,
            35 to B_TYPE,
            39 to B_TYPE,
            42 to B_TYPE
        )
    }
}
