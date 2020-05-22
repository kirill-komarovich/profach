package com.profach.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.profach.databinding.HistoryListItemBinding
import com.profach.entities.Inquirer
import java.util.*

class HistoryAdapter(
    options: FirestorePagingOptions<Inquirer>,
    private val clickListener: Listener
) : FirestorePagingAdapter<Inquirer, HistoryAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Inquirer) {
        holder.bind(model, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: HistoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Inquirer, clickListener: Listener) {
            binding.result = item
            binding.clickListener = clickListener
            val dateFormat: java.text.DateFormat = DateFormat.getDateFormat(itemView.context)!!

            binding.inquirerDate.text = dateFormat.format(Date())
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HistoryListItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class Listener(val clickListener: (inquirer: Inquirer) -> Unit) {
        fun onClick(inquirer: Inquirer) = clickListener(inquirer)
    }
}
