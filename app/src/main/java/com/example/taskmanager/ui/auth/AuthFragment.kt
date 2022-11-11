package com.example.taskmanager.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide.init
import com.example.taskmanager.databinding.FragmentAuthBinding
import com.example.taskmanager.ui.home.HomeFragment
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.R

 class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var mVerificationId:String
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        binding.sendOTPBtn.setOnClickListener {
            mVerificationId = binding.phoneEditTextNumber.text.toString().trim()
            if(mVerificationId.isNotEmpty()){
                mVerificationId = "+996$mVerificationId"
                Log.d("dad",mVerificationId)
                if(mVerificationId.length.equals(13)){
                    binding.phoneProgressBar.visibility = View.VISIBLE
                    val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(mVerificationId)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }else{
                    Toast.makeText(requireContext(),"Please Enter correct number \nCount of symbols " + mVerificationId.length.toString(),Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Please Enter Number",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init() {
        binding.phoneProgressBar.visibility = View.INVISIBLE
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(requireContext(),"Authenticate Successfully ",Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG","SignInWithCredential${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun sendToMain(){
        findNavController().navigate(com.example.taskmanager.R.id.navigation_home)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG","onVerificationFailed", e)
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG","onVerificationFailed", e)
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            findNavController().navigate(com.example.taskmanager.R.id.authAcceptFragment , bundleOf(
                OTP to verificationId))
            findNavController().navigate(com.example.taskmanager.R.id.authAcceptFragment , bundleOf(
                RESEND to token))
            findNavController().navigate(com.example.taskmanager.R.id.authAcceptFragment , bundleOf(
                PHONE to mVerificationId))
            binding.phoneProgressBar.visibility = View.INVISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            findNavController().navigate(com.example.taskmanager.R.id.navigation_home)
        }
    }

    companion object{
        const val OTP:String = "OTS"
        const val RESEND:String = "Token"
        const val PHONE:String = "phoneNumber"
    }


}