package com.example.taskmanager.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentAuthAcceptBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class AuthAcceptFragment : Fragment() {

    private lateinit var binding: FragmentAuthAcceptBinding
    private lateinit var OTP:String
    private lateinit var phoneNumber:String
    private lateinit var resendToken:PhoneAuthProvider.ForceResendingToken
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthAcceptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OTP = arguments?.getString(AuthFragment.OTP).toString()
        resendToken = arguments?.get(AuthFragment.RESEND) as PhoneAuthProvider.ForceResendingToken
        phoneNumber = arguments?.get(AuthFragment.PHONE) as String

        firebaseAuth = FirebaseAuth.getInstance()
        binding.otpProgressBar.visibility = View.INVISIBLE
        addTextChangedListener()
        resendOTPVisability()

        binding.resendTextView.setOnClickListener {
            resendVerificationCode()
            resendOTPVisability()
        }

        binding.verifyOTPBtn.setOnClickListener {
            val typedOTP =
                    binding.otpEditText1.text.toString() +
                    binding.otpEditText2.text.toString() +
                    binding.otpEditText3.text.toString() +
                    binding.otpEditText4.text.toString() +
                    binding.otpEditText5.text.toString() +
                    binding.otpEditText6.text.toString()

            if(typedOTP.isNotEmpty()){
                if(typedOTP.length == 6){
                    val credential:PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    binding.otpProgressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                }else{
                    Toast.makeText(requireContext(),"Please Enter correct OTP", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Please Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resendVerificationCode(){
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
            OTP = verificationId
            resendToken = token
        }
    }

    private fun resendOTPVisability(){
        binding.otpEditText1.setText("")
        binding.otpEditText2.setText("")
        binding.otpEditText3.setText("")
        binding.otpEditText4.setText("")
        binding.otpEditText5.setText("")
        binding.otpEditText6.setText("")
        binding.resendTextView.visibility = View.INVISIBLE
        binding.resendTextView.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.resendTextView.visibility = View.VISIBLE
            binding.resendTextView.isEnabled = true
        },60000)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    binding.otpProgressBar.visibility = View.VISIBLE
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

    private fun addTextChangedListener(){
        binding.otpEditText1.addTextChangedListener(EditTextWatcher(binding.otpEditText1))
        binding.otpEditText2.addTextChangedListener(EditTextWatcher(binding.otpEditText2))
        binding.otpEditText3.addTextChangedListener(EditTextWatcher(binding.otpEditText3))
        binding.otpEditText4.addTextChangedListener(EditTextWatcher(binding.otpEditText4))
        binding.otpEditText5.addTextChangedListener(EditTextWatcher(binding.otpEditText5))
        binding.otpEditText6.addTextChangedListener(EditTextWatcher(binding.otpEditText6))
    }

    inner class EditTextWatcher(private val view: View): TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

            val text = s.toString()
            when(view.id){
                R.id.otpEditText1 -> if(text.length == 1) binding.otpEditText2.requestFocus()
                R.id.otpEditText2 -> if(text.length == 1) binding.otpEditText3.requestFocus() else if(text.isEmpty()) binding.otpEditText1.requestFocus()
                R.id.otpEditText3 -> if(text.length == 1) binding.otpEditText4.requestFocus() else if(text.isEmpty()) binding.otpEditText2.requestFocus()
                R.id.otpEditText4 -> if(text.length == 1) binding.otpEditText5.requestFocus() else if(text.isEmpty()) binding.otpEditText3.requestFocus()
                R.id.otpEditText5 -> if(text.length == 1) binding.otpEditText3.requestFocus() else if(text.isEmpty()) binding.otpEditText4.requestFocus()
                R.id.otpEditText6 -> if(text.isEmpty()) binding.otpEditText5.requestFocus()
            }
        }

    }
}
