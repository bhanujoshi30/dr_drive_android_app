package com.example.interviewdemo.ui.registration

import android.os.Bundle

import com.example.interviewdemo.R
import com.example.interviewdemo.databinding.ActivityLoginBinding
import com.example.interviewdemo.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {


    override fun setActivityLayout(): Int = R.layout.activity_login


    override fun initialize(savedInstanceState: Bundle?){
        bindings()

    }
    private fun bindings() = mDataBinding as ActivityLoginBinding

}