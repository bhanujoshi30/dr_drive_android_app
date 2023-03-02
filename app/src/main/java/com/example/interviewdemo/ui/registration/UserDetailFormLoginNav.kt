package com.example.interviewdemo.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.interviewdemo.R
import com.example.interviewdemo.ui.MainActivity
import com.example.interviewdemo.viewmodels.UserDetailFormViewModel

class UserDetailFormLoginNav : Fragment() {

    companion object {
        fun newInstance() = UserDetailFormLoginNav()
    }

    private lateinit var viewModel: UserDetailFormViewModel
    private lateinit var fragView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.fragment_user_detail_form, container, false)
        val btnSave = fragView.findViewById<Button>(R.id.btn_saveUserDet)
        btnSave.setOnClickListener {
            val intent =
                Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return fragView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserDetailFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}