package com.profach.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.profach.databinding.StudentsListItemBinding
import com.profach.entities.User

class StudentsAdapter(
    options: FirestoreRecyclerOptions<User>,
    private val clickListener: Listener,
    private val onDeleteListener: DeleteListener
) : FirestoreRecyclerAdapter<User, StudentsAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: User) {
        holder.bind(model, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onDeleteListener)
    }


    class ViewHolder private constructor(val binding: StudentsListItemBinding, private val onDeleteListener: DeleteListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User, clickListener: Listener) {
            binding.student = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        fun removeItem() {
            onDeleteListener.onDelete(binding.student!!)
        }

        companion object {
            fun from(parent: ViewGroup, onDeleteListener: DeleteListener): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = StudentsListItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding, onDeleteListener)
            }
        }
    }

    class Listener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }

    class DeleteListener(val deleteListener: (user: User) -> Unit) {
        fun onDelete(user: User) = deleteListener(user)
    }
}