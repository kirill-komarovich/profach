package com.profach.viewmodels.result

import android.content.res.Resources
import com.profach.R
import com.profach.viewmodels.ResultViewModel

class HolomstockResultViewModel(answers: IntArray) : ResultViewModel(answers) {

    val biology: Float
        get() = calculateGroup(BIOLOGY_IDS)

    val geography: Float
        get() = calculateGroup(GEOGRAPHY_IDS)

    val geology: Float
        get() = calculateGroup(GEOLOGY_IDS)

    val medicine: Float
        get() = calculateGroup(MEDICINE_IDS)

    val light: Float
        get() = calculateGroup(LIGHT_IDS)

    val physics: Float
        get() = calculateGroup(PHYSICS_IDS)

    val chemistry: Float
        get() = calculateGroup(CHEMISTRY_IDS)

    val technique: Float
        get() = calculateGroup(TECHNIQUE_IDS)

    val engineering: Float
        get() = calculateGroup(ENGINEERING_IDS)

    val metalworking: Float
        get() = calculateGroup(METALWORKING_IDS)

    val woodworking: Float
        get() = calculateGroup(WOODWORKING_IDS)

    val construction: Float
        get() = calculateGroup(CONSTRUCTION_IDS)

    val transport: Float
        get() = calculateGroup(TRANSPORT_IDS)

    val aviation: Float
        get() = calculateGroup(AVIATION_IDS)

    val military: Float
        get() = calculateGroup(MILITARY_IDS)

    val history: Float
        get() = calculateGroup(HISTORY_IDS)

    val literature: Float
        get() = calculateGroup(LITERATURE_IDS)

    val journalism: Float
        get() = calculateGroup(JOURNALISM_IDS)

    val social: Float
        get() = calculateGroup(SOCIAL_IDS)

    val pedagogy: Float
        get() = calculateGroup(PEDAGOGY_IDS)

    val jurisprudence: Float
        get() = calculateGroup(JURISPRUDENCE_IDS)

    val service: Float
        get() = calculateGroup(SERVICE_IDS)

    val math: Float
        get() = calculateGroup(MATH_IDS)

    val economics: Float
        get() = calculateGroup(ECONOMICS_IDS)

    val languages: Float
        get() = calculateGroup(LANGUAGES_IDS)

    val art: Float
        get() = calculateGroup(ART_IDS)

    val acting: Float
        get() = calculateGroup(ACTING_IDS)

    val music: Float
        get() = calculateGroup(MUSIC_IDS)

    val sport: Float
        get() = calculateGroup(SPORT_IDS)

    override fun answerToValue(value: Int, id: Int): Int {
        return when(value) {
            0 -> 2
            3 -> -1
            4 -> -2
            else -> value
        }
    }

    override fun buildShareText(resources: Resources): String {
        return """
            |${resources.getString(R.string.holomstock_result_details)}

            |${String.format(resources.getString(R.string.holomstock_biology), biology)}
            |${String.format(resources.getString(R.string.holomstock_geography), geography)}
            |${String.format(resources.getString(R.string.holomstock_geology), geology)}
            |${String.format(resources.getString(R.string.holomstock_medicine), medicine)}
            |${String.format(resources.getString(R.string.holomstock_light), light)}
            |${String.format(resources.getString(R.string.holomstock_physics), physics)}
            |${String.format(resources.getString(R.string.holomstock_chemistry), chemistry)}
            |${String.format(resources.getString(R.string.holomstock_technique), technique)}
            |${String.format(resources.getString(R.string.holomstock_engineering), engineering)}
            |${String.format(resources.getString(R.string.holomstock_metalworking), metalworking)}
            |${String.format(resources.getString(R.string.holomstock_woodworking), woodworking)}
            |${String.format(resources.getString(R.string.holomstock_construction), construction)}
            |${String.format(resources.getString(R.string.holomstock_transport), transport)}
            |${String.format(resources.getString(R.string.holomstock_aviation), aviation)}
            |${String.format(resources.getString(R.string.holomstock_military), military)}
            |${String.format(resources.getString(R.string.holomstock_history), history)}
            |${String.format(resources.getString(R.string.holomstock_literature), literature)}
            |${String.format(resources.getString(R.string.holomstock_journalism), journalism)}
            |${String.format(resources.getString(R.string.holomstock_social), social)}
            |${String.format(resources.getString(R.string.holomstock_pedagogy), pedagogy)}
            |${String.format(resources.getString(R.string.holomstock_jurisprudence), jurisprudence)}
            |${String.format(resources.getString(R.string.holomstock_service), service)}
            |${String.format(resources.getString(R.string.holomstock_math), math)}
            |${String.format(resources.getString(R.string.holomstock_economics), economics)}
            |${String.format(resources.getString(R.string.holomstock_languages), languages)}
            |${String.format(resources.getString(R.string.holomstock_art), art)}
            |${String.format(resources.getString(R.string.holomstock_acting), acting)}
            |${String.format(resources.getString(R.string.holomstock_music), music)}
            |${String.format(resources.getString(R.string.holomstock_sport), sport)}
        """.trimMargin()
    }

    companion object {
        private val BIOLOGY_IDS = listOf(0, 29, 58, 87, 116, 145)
        private val GEOGRAPHY_IDS = listOf(1, 30, 59, 88, 117, 146)
        private val GEOLOGY_IDS = listOf(2, 31, 60, 89, 118, 147)
        private val MEDICINE_IDS = listOf(3, 32, 61, 90, 119, 148)
        private val LIGHT_IDS = listOf(4, 33, 62, 91, 120, 149)
        private val PHYSICS_IDS = listOf(5, 34, 63, 92, 121, 150)
        private val CHEMISTRY_IDS = listOf(6, 35, 64, 93, 122, 151)
        private val TECHNIQUE_IDS = listOf(7, 36, 65, 94, 123, 152)
        private val ENGINEERING_IDS = listOf(8, 37, 66, 95, 124, 153)
        private val METALWORKING_IDS = listOf(9, 38, 67, 96, 125, 154)
        private val WOODWORKING_IDS = listOf(10, 39, 68, 97, 126, 155)
        private val CONSTRUCTION_IDS = listOf(11, 40, 69, 98, 127, 156)
        private val TRANSPORT_IDS = listOf(12, 41, 70, 99, 128, 157)
        private val AVIATION_IDS = listOf(13, 42, 71, 100, 129, 158)
        private val MILITARY_IDS = listOf(14, 43, 72, 101, 130, 159)
        private val HISTORY_IDS = listOf(15, 44, 73, 102, 131, 160)
        private val LITERATURE_IDS = listOf(16, 45, 74, 103, 132, 161)
        private val JOURNALISM_IDS = listOf(17, 46, 75, 104, 133, 162)
        private val SOCIAL_IDS = listOf(18, 47, 76, 105, 134, 163)
        private val PEDAGOGY_IDS = listOf(19, 48, 77, 106, 135, 164)
        private val JURISPRUDENCE_IDS = listOf(20, 49, 78, 107, 136, 165)
        private val SERVICE_IDS = listOf(21, 50, 79, 108, 137, 166)
        private val MATH_IDS = listOf(22, 51, 80, 109, 138, 167)
        private val ECONOMICS_IDS = listOf(23, 52, 81, 110, 139, 168)
        private val LANGUAGES_IDS = listOf(24, 53, 82, 111, 140, 169)
        private val ART_IDS = listOf(25, 54, 83, 112, 141, 170)
        private val ACTING_IDS = listOf(26, 55, 84, 113, 142, 171)
        private val MUSIC_IDS = listOf(27, 56, 85, 114, 143, 172)
        private val SPORT_IDS = listOf(28, 57, 86, 115, 144, 173)
    }
}
