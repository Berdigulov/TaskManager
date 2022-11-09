package com.example.taskmanager.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.data.Task
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.ui.home.HomeFragment


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var isEmptyData = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task = arguments?.getSerializable(HomeFragment.TASK) as Task?

        isEmptyData = task == null
        if(isEmptyData){
            binding.btnAdd.text = "Add"
        }else{
            binding.btnAdd.text = "Update"
        }

        binding.etTitle.setText(task?.title)
        binding.etDesc.setText(task?.description)

        binding.btnAdd.setOnClickListener {
            if(isEmptyData){
                saveTask()
            }else{
                updateTask(task)
            }
        }
    }

    private fun updateTask(task: Task?) {
        if(binding.etTitle.text?.isNotEmpty() == true){
            task?.title = binding.etTitle.text.toString()
            task?.description = binding.etDesc.text.toString()
            if(task != null) {
                App.db.dao().update(task)
            }
            findNavController().navigateUp()
        }else {
            binding.etTitle.error = "Input title"
        }
    }

    private fun saveTask(){
        if(binding.etTitle.text?.isNotEmpty() == true){
            App.db.dao().insert(Task(
                title = binding.etTitle.text.toString(),
                description = binding.etDesc.text.toString()
            )
            )
            findNavController().navigateUp()
        }else {
            binding.etTitle.error = "Input title"
        }
    }

}