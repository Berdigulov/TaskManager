package com.example.taskmanager.ui.home

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note.NOTE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.data.Task
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.ui.task.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: TaskAdapter
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter(requireContext(),this::onLongClick,this::onClick)
    }

    private fun onClick(task: Task){
        findNavController().navigate(R.id.taskFragment, bundleOf(TASK to task))
    }

    private fun onLongClick(task: Task){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete?")
        alertDialog.setPositiveButton("Yes"){
            d,i ->
            App.db.dao().delete(task)
            getAllTask()
            d.dismiss()
        }
        alertDialog.setNegativeButton("No"){
                d,i ->
            d.dismiss()
        }
        alertDialog.create().show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.taskRecycler.adapter = adapter
        getAllTask()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun getAllTask(){
        val list = App.db.dao().getAll()
        adapter.addAllTasks(list.reversed())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val TASK = "task.key"
    }
}