package com.example.interviewdemo.ui.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.interviewdemo.R
import com.example.interviewdemo.ui.MainActivity
import com.example.interviewdemo.utils.Constants
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class MobileNumberLoginNav : Fragment() {
    private lateinit var et_phoneNo: EditText
    private lateinit var btn_getOtp: Button
    private lateinit var fragView: View
    // this stores the phone number of the user
    var number : String =""

    // create instance of firebase auth
    lateinit var auth: FirebaseAuth

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.fragment_mobile_number_login, container, false)
        et_phoneNo = fragView.findViewById(R.id.et_phone_no)
        btn_getOtp = fragView.findViewById(R.id.btn_getOtp)

        btn_getOtp.setOnClickListener {
            val bundle = bundleOf("user_input" to et_phoneNo.text.toString())
            it.findNavController().navigate(R.id.action_mobile_number_login_to_otp_verify_login, bundle)
        }
        firebaseTaskInit()
        return fragView
    }
fun firebaseTaskInit(){
    auth=FirebaseAuth.getInstance()

    // start verification on click of the button
    btn_getOtp.setOnClickListener {
        login()
    }

    // Callback function for Phone Auth
    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // This method is called when the verification is completed
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            startActivity(Intent(context, MainActivity::class.java))

        }

        // Called when verification is failed add log statement to see the exception
        override fun onVerificationFailed(e: FirebaseException) {
            Log.d(Constants.LOG_TAGG , "onVerificationFailed  $e")
        }

        // On code is sent by the firebase this method is called
        // in here we start a new activity where user can enter the OTP
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token

           //call next fragment
            val bundle = bundleOf(Constants.FIREBASE_VERIFICATION to storedVerificationId)
            fragView.findNavController().navigate(R.id.action_mobile_number_login_to_otp_verify_login, bundle)
        }
    }
}
    private fun login() {
        number = et_phoneNo.text.trim().toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()){
            number = "+91$number"
            sendVerificationCode(number)
        }else{
            Toast.makeText(context,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(it) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        Log.d("GFG" , "Auth started")
    }
}