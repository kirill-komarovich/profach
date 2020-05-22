package com.profach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.profach.adapters.InquirersAdapter
import com.profach.databinding.InquirersFragmentBinding
import com.profach.viewmodels.InquirerViewModel

class InquirersFragment : Fragment() {
    private lateinit var binding: InquirersFragmentBinding
    private val viewModel: InquirerViewModel by viewModels()
    private val adapter = InquirersAdapter(
        InquirersAdapter.Listener {
            this.findNavController().navigate(InquirersFragmentDirections.actionInquirersToWalkthrough(it.type!!))
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InquirersFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.inquirersList.adapter = adapter

        viewModel.inquirers.observe(viewLifecycleOwner, Observer { inquirers ->
            inquirers?.let { adapter.submitList(it) }
        })

        return binding.root
    }
}