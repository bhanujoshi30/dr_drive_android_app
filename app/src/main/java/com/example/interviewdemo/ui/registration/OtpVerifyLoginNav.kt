package com.example.interviewdemo.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.interviewdemo.R
import com.example.interviewdemo.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class OtpVerifyLoginNav : Fragment() {

    // get reference of the firebase auth
    lateinit var auth: FirebaseAuth
    private lateinit var fragView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.fragment_otp_verify_login, container, false)

        val btnVerify = fragView.findViewById<Button>(R.id.btn_verify)
        val storedVerificationId = requireArguments().getString(Constants.FIREBASE_VERIFICATION)

        btnVerify.setOnClickListener {
            (activity as LoginActivity).showProgressBar()
            val otp = fragView.findViewById<EditText>(R.id.et_otp).text.trim().toString()
            if (otp.isNotEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(context, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
        return fragView
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        (activity as LoginActivity).hideProgressLoader()
                        fragView.findNavController().navigate(R.id.action_otp_verify_login_to_user_detail_form)
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(context,"Invalid OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}