package com.profach.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.profach.R
import com.profach.adapters.StudentsAdapter
import com.profach.databinding.StudentsFragmentBinding
import com.profach.managers.AccountManager
import com.profach.viewmodels.StudentsViewModel

class StudentsFragment : Fragment() {
    private lateinit var binding: StudentsFragmentBinding
    private val viewModel: StudentsViewModel by viewModels()
    private lateinit var adapter: StudentsAdapter
    private val onItemClickListener = StudentsAdapter.Listener {
        findNavController().navigate(StudentsFragmentDirections.actionStudentsToHistory(it.id))
    }
    private val onDeleteListener = StudentsAdapter.DeleteListener {
        viewModel.removeStudent(it.id!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val options = viewModel.buildPagingOptions(viewModel.initialQuery(), viewLifecycleOwner)
        adapter = StudentsAdapter(options, onItemClickListener, onDeleteListener)

        binding = StudentsFragmentBinding.inflate(inflater)

        binding.studentsList.adapter = adapter
        binding.lifecycleOwner = this
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.studentsList)
        viewModel.listQuery.observe(viewLifecycleOwner, Observer {
            val newOptions = viewModel.buildPagingOptions(it)

            adapter.updateOptions(newOptions)
        })
        return binding.root
    }

    class SwipeToDeleteCallback(private val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        private val icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24)!!
        private val background = ColorDrawable(Color.RED)

        init {
            icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val itemView = viewHolder.itemView
            val backgroundCornerOffset = 20
            background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            background.draw(c)

            val itemHeight = itemView.bottom - itemView.top
            val intrinsicWidth: Int = icon.intrinsicWidth
            val intrinsicHeight: Int = icon.intrinsicWidth

            val iconLeft: Int = itemView.right - backgroundCornerOffset - intrinsicWidth
            val iconRight: Int = itemView.right - backgroundCornerOffset
            val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val iconBottom = iconTop + intrinsicHeight
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            icon.draw(c)

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (viewHolder as StudentsAdapter.ViewHolder).removeItem()
        }

        override fun isItemViewSwipeEnabled(): Boolean = true
    }
}