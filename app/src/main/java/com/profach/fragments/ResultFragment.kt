package com.profach.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.profach.BR
import com.profach.R
import com.profach.entities.Inquirer
import com.profach.managers.AccountManager
import com.profach.mappers.InquirerMapping
import com.profach.viewmodels.ResultViewModel

class ResultFragment : Fragment() {
    private lateinit var binding: ViewDataBinding
    private lateinit var currentInquirerType: Inquirer.Type
    private lateinit var viewModelFactory: ResultViewModel.Factory
    private val viewModel: ResultViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = ResultFragmentArgs.fromBundle(requireArguments())
        currentInquirerType = args.inquirerType
        viewModelFactory = ResultViewModel.Factory(currentInquirerType, args.answers)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            InquirerMapping.resultFragmentLayout(currentInquirerType),
            container,
            false
        )
        binding.setVariable(BR.viewModel, viewModel)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.result_menu, menu)

        // check if the activity resolves
        if (getShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareResult()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, viewModel.buildShareText(resources))
        return shareIntent
    }

    private fun shareResult() {
        startActivity(getShareIntent())
    }
}