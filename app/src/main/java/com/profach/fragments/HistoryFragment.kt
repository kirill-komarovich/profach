package com.profach.fragments

import android.os.Bundle
import android.util.Log
import com.profach.adapters.HistoryAdapter
import com.profach.databinding.HistoryFragmentBinding
import com.profach.managers.AccountManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.profach.viewmodels.HistoryViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: HistoryFragmentBinding
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter
    private lateinit var userId: String
    private val onItemClickListener = HistoryAdapter.Listener {
        this.findNavController().navigate(
            HistoryFragmentDirections.actionHistoryToResult(inquirerType = it.type!!, answers = it.answers!!.toIntArray())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = HistoryFragmentArgs.fromBundle(requireArguments())

        userId = args.userId ?: AccountManager.currentUser!!.uid
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(HISTORY_LIST_BUNDLE_KEY, binding.historyList.layoutManager!!.onSaveInstanceState())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val options = viewModel.buildPagingOptions(viewModel.initialQuery(userId), viewLifecycleOwner)
        adapter = HistoryAdapter(options, onItemClickListener)
        binding = HistoryFragmentBinding.inflate(inflater)
        binding.historyList.layoutManager?.onRestoreInstanceState(savedInstanceState?.getParcelable(HISTORY_LIST_BUNDLE_KEY))
        binding.historyList.adapter = adapter
        binding.lifecycleOwner = this

        viewModel.listQuery.observe(viewLifecycleOwner, Observer {
            val newOptions = viewModel.buildPagingOptions(it)

            adapter.updateOptions(newOptions)
        })

        return binding.root
    }

    companion object {
        private const val HISTORY_LIST_BUNDLE_KEY = "historyListState"
    }
}