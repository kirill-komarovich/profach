package com.profach.fragments

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.profach.R
import com.profach.databinding.WalkthroughFragmentBinding
import com.profach.entities.Inquirer
import com.profach.managers.AccountManager
import com.profach.viewmodels.WalkthroughViewModel

class WalkthroughFragment : Fragment() {
    private lateinit var binding: WalkthroughFragmentBinding
    private lateinit var viewModelFactory: WalkthroughViewModel.Factory
    private val viewModel: WalkthroughViewModel by viewModels { viewModelFactory }
    private val onFinished = { answers: List<Int> ->
        if (answers.any { it == -1 }) {
            val snack = Snackbar.make(requireView(), R.string.blank_error, Snackbar.LENGTH_SHORT)
            snack.view.setBackgroundColor(Color.RED)
            snack.show()
        } else {
            AccountManager.currentUser?.let { saveInquire(answers, it.uid) }
            this.findNavController().navigate(
                WalkthroughFragmentDirections.actionWalkthroughToResult(
                    viewModel.inquirerType.value!!,
                    viewModel.answers.value!!.toIntArray()
                )
            )
        }
        Unit
    }
    private val answersArray by lazy {
        val type = viewModel.inquirerType.value!!.toLowerCase()
        val answersId = resources.getIdentifier("${type}_answers", "array", requireContext().packageName)
        resources.getStringArray(answersId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = WalkthroughFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = WalkthroughViewModel.Factory(args.inquirerType, onFinished)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WalkthroughFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setAnswers()

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer {
            applyAnswers(it)
        })

        return binding.root
    }

    private fun setAnswers() {
        answersArray.forEachIndexed { index, string ->
            val button = RadioButton(context)
            button.id = index
            button.text = string
            binding.questionAnswers.addView(button)
        }

        binding.questionAnswers.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setCurrentQuestionValue(checkedId)
        }
    }

    private fun applyAnswers(currentQuestion: Int) {
        binding.questionAnswers.apply {
            check(viewModel.answers.value!![currentQuestion])
            children.forEachIndexed { index, view ->
                val button = view as RadioButton
                button.text = answersArray[index]
            }
        }
    }

    private fun saveInquire(answers: List<Int>, userId: String) {
        val inquirer = Inquirer(answers = answers, userId = userId, type = viewModel.inquirerType.value!!)

        viewModel.saveInquire(inquirer)
    }
}