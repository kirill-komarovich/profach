package com.profach.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.profach.MainActivity
import com.profach.databinding.AccountFragmentBinding
import com.profach.entities.User
import com.profach.managers.AccountManager
import com.profach.viewmodels.AccountViewModel

class AccountFragment : Fragment() {
    private lateinit var binding: AccountFragmentBinding
    private val viewModel: AccountViewModel by viewModels { AccountViewModel.Factory(requireActivity().application) }
    private val dialog = AddStudentDialogFragment { email, group ->
        viewModel.addStudent(email, group)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            binding.authenticatedLayout.visibility = isVisibleConverter(it != null)
            binding.signInButton.visibility = isVisibleConverter(it == null)
        })

        val isTeacher = AccountManager.currentUserProfile?.let { it.role != null && it.role == User.Role.TEACHER } ?: false
        binding.signInButton.setOnClickListener {
            startActivityForResult(viewModel.signInUser(), MainActivity.ResultCodes.SIGN_IN.ordinal)
        }
        binding.signOutButton.setOnClickListener { viewModel.signOutUser() }
        binding.historyButton.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountToHistory())
        }
        binding.addStudentButton.visibility = isVisibleConverter(isTeacher)
        binding.addStudentButton.setOnClickListener { openAddPsychologistDialog() }
        binding.studentsButton.visibility = isVisibleConverter(isTeacher)
        binding.studentsButton.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountToStudents())
        }
        binding.upgradeButton.visibility = isVisibleConverter(!isTeacher)
        binding.upgradeButton.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.actionAccountToUpgradeAccount())
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MainActivity.ResultCodes.SIGN_IN.ordinal) {
            viewModel.authenticateWithGoogle(data)
        }
    }

    private fun openAddPsychologistDialog() {
        dialog.show(parentFragmentManager, DIALOG_TAG)
    }

    private fun isVisibleConverter(condition: Boolean): Int {
        return if(condition) { View.VISIBLE } else { View.GONE }
    }

    companion object {
        private const val DIALOG_TAG = "AddStudentsDialogFragment"
    }
}