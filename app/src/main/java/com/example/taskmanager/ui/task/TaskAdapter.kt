package com.example.taskmanager.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.data.Task
import com.example.taskmanager.databinding.ItemTaskBinding

class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = arrayListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = tasks.size
    inner class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            val item = tasks[adapterPosition]
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }
}