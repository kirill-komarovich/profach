package com.profach.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.profach.databinding.InquirersListItemBinding
import com.profach.entities.Inquirer
import net.cachapa.expandablelayout.ExpandableLayout

class InquirersAdapter(private val clickListener: Listener) : ListAdapter<Inquirer, InquirersAdapter.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    companion object {
        private const val UNSELECTED = -1
    }

    class ViewHolder private constructor(private val binding: InquirersListItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.inquirerItemContent.setInterpolator(DecelerateInterpolator())
            binding.inquirerItemTitle.setOnClickListener(this)
        }

        fun bind(item: Inquirer, clickListener: Listener) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        override fun onClick(view: View?) {
            binding.inquirerItemContent.apply {
                if (isExpanded) {
                    collapse()
                } else {
                    expand()
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = InquirersListItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Inquirer>() {
        override fun areItemsTheSame(oldItem: Inquirer, newItem: Inquirer): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Inquirer, newItem: Inquirer): Boolean {
            return oldItem == newItem
        }
    }

    class Listener(val clickListener: (inquirerType: Inquirer) -> Unit) {
        fun onClick(inquirer: Inquirer) = clickListener(inquirer)
    }
}