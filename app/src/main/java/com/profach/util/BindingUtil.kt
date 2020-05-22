package com.profach.util

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.profach.R
import com.profach.entities.Inquirer

@BindingAdapter("translatedText")
fun setTranslatedText(view: TextView, key: String?) {
    key?.let {
        val res: Resources = view.resources
        view.text = res.getString(res.getIdentifier(it, "string",  view.context.packageName))
    }
}

@BindingAdapter(value = ["currentQuestion", "questionsCount"], requireAll = true)
fun setCurrentQuestionNumber(view: TextView, currentQuestion: Int, questionsCount: Int) {
    val res: Resources = view.resources
    view.text = String.format(res.getString(R.string.current_question), currentQuestion, questionsCount)
}

@BindingAdapter(value = ["inquirerType", "currentQuestion"], requireAll = true)
fun setCurrentQuestionLabel(view: TextView, inquirerType: String, currentQuestion: Int) {
    val res: Resources = view.resources
    val questionsResId = res.getIdentifier("${inquirerType}_questions", "array",  view.context.packageName)
    val questionsStringArray = res.getStringArray(questionsResId)

    view.text = questionsStringArray[currentQuestion]
}

@BindingAdapter("answerValue")
fun setAnswer(view: TextView, answerValue: Float) {
    view.text = String.format(view.text.toString(), answerValue)
}

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}