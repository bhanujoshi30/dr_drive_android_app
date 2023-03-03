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
import androidx.lifecycle.ViewModelProvider
import com.example.interviewdemo.R
import com.example.interviewdemo.models.UserDetail
import com.example.interviewdemo.ui.MainActivity
import com.example.interviewdemo.utils.Animate
import com.example.interviewdemo.utils.LoadingStatusType
import com.example.interviewdemo.viewmodels.UserDetailFormViewModel


class UserDetailFormLoginNav : Fragment() {

    private lateinit var viewModel: UserDetailFormViewModel
    private lateinit var fragView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // init ViewModel
        viewModel = ViewModelProvider(requireActivity())[UserDetailFormViewModel::class.java]
        //pageViewModel = ViewModelProviders.of(requireActivity()).get(PageViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragView = inflater.inflate(R.layout.fragment_user_detail_form, container, false)

        val btnSave = fragView.findViewById<Button>(R.id.btn_saveUserDet)

        btnSave.setOnClickListener {
            if (!fragView.findViewById<EditText>(R.id.et_user_name).text.isNullOrEmpty()) {
                viewModel.setUserData(
                    UserDetail(
                        fragView.findViewById<EditText>(R.id.et_user_name).text.toString(),
                        fragView.findViewById<EditText>(R.id.et_user_last_name).text?.toString(),
                        fragView.findViewById<EditText>(R.id.et_user_pincode).text?.toString(),
                        fragView.findViewById<EditText>(R.id.et_user_birth).text?.toString(),
                        fragView.findViewById<EditText>(R.id.et_user_gender).text?.toString()
                    )
                )
                val intent =
                    Intent(context, MainActivity::class.java)
                startActivity(intent)
                Animate.animateSlideLeft(this.requireActivity())
                activity?.finish()

            } else {
                Toast.makeText(context, "error: name is mandatory", Toast.LENGTH_SHORT).show()
            }


        }
        observeLiveData()
        return fragView
    }


    private fun observeLiveData() {
        viewModel.loadingStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is LoadingStatusType.Loaded -> Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT)
                    .show()
                else -> {}
            }
        }
    }
}