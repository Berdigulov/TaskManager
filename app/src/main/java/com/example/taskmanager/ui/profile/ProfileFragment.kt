package com.example.taskmanager.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentNotificationsBinding
import com.example.taskmanager.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private val mContent =
        registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.ivProfile.setImageURI(it)
            })

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivProfile.setOnClickListener {
            mContent.launch("image/*")
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}