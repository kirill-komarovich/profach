package com.profach.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.profach.R
import com.profach.databinding.UpgradeAccountFragmentBinding
import com.profach.viewmodels.UpgradeAccountViewModel

class UpgradeAccountFragment : Fragment() {
    private lateinit var binding: UpgradeAccountFragmentBinding
    private val viewModel: UpgradeAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UpgradeAccountFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.upgradeAccountButton.setOnClickListener { viewModel.updateRole() }

        viewModel.isUpgraded.observe(viewLifecycleOwner, Observer {
            if (it) {
                val snack = Snackbar.make(requireView(), R.string.upgrade_account_success, Snackbar.LENGTH_SHORT)
                snack.view.setBackgroundColor(Color.GREEN)
                snack.setTextColor(Color.BLACK)
                snack.show()
                this.findNavController().navigateUp()
            }
        })
        return binding.root
    }
}
