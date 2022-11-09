package com.example.taskmanager.ui.task

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.data.Task
import com.example.taskmanager.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val onLongClick: (task: Task) -> Unit,
    private val onClick: (task: Task) -> Unit)
    :RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = arrayListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind()
        holder.itemView.setOnLongClickListener {
            onLongClick(tasks[position])
            false
        }

        holder.itemView.setOnClickListener {
            onClick(tasks[position])
        }
    }


    override fun getItemCount(): Int = tasks.size


    inner class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            if(adapterPosition % 2 == 0){
                binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.white))
            }else {
                binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                binding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
                binding.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
            val item = tasks[adapterPosition]
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
        }
    }
    fun addTask(task: Task){
        tasks.add(0, task)
        notifyItemChanged(0)
    }

    fun addAllTasks(list: List<Task>){
        tasks.clear()
        tasks.addAll(list)
        notifyDataSetChanged()
    }

    fun getAllTask(position: Int): Task{
        return tasks[position]
    }
}