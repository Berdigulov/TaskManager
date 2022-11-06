package com.example.taskmanager.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.data.Task
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.ui.home.HomeFragment


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {

            saveTask()
        }
    }
    private fun saveTask(){
        if(binding.etTitle.text?.isNotEmpty() == true){
            val task = Task(
                title = binding.etTitle.text.toString(),
                description = binding.etDesc.text.toString()
            )
            setFragmentResult(
                HomeFragment.TASK, bundleOf("key_task" to task)
            )
            findNavController().navigateUp()
        }else {
            binding.etTitle.error = "Input title"
        }
    }

}