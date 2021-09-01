package com.otavro.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.otavro.todo.R
import com.otavro.todo.databinding.ItemTaskBinding
import com.otavro.todo.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TaskViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding =  ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder : TaskViewHolder, position : Int){
        holder.bind(getItem(position))
    }


    inner class TaskViewHolder(
        private val binding : ItemTaskBinding
        ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.tvDateHour.text = "${item.date} ${item.hour}"
            binding.ivMore.setOnClickListener{
                showPopUp(item)
            }
        }

        private fun showPopUp(item : Task){
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.poup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

}