package com.example.taskmanager.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.taskmanager.data.loadImage
import com.example.taskmanager.data.local.Pref
import com.example.taskmanager.databinding.FragmentProfileBinding
import java.io.ByteArrayOutputStream


class ProfileFragment:Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var pref: Pref

    private var galleryActivityLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { result ->
            if (result != null) {
                binding.profileImage.loadImage(result.toString())
            } else {
                Log.d("dad", "onActivityResult: the result is null for some reason")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        binding.profileImage.setOnClickListener {
            galleryActivityLauncher.launch(arrayOf("image/*"))
            Log.d("dad","img")
        }

        binding.etName.setText(pref.getName())
        binding.etName.addTextChangedListener {
            pref.saveName(binding.etName.text.toString())
        }

        binding.etAge.setText(pref.getAge())
        binding.etAge.addTextChangedListener {
            pref.saveAge(binding.etAge.text.toString())
        }
        
    }
}