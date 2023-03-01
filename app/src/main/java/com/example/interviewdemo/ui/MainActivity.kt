package com.example.interviewdemo.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interviewdemo.R
import com.example.interviewdemo.databinding.ActivityMainBinding
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.ui.adapters.workshopDetailAdapter
import com.example.interviewdemo.ui.base.BaseActivity
import com.example.interviewdemo.utils.LoadingStatusType
import com.example.interviewdemo.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity :
 * created by Bhanu Prakash Joshi
 * Feb 2023
 * */

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun setActivityLayout(): Int = R.layout.activity_main

    override fun initialize(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getWorkShopDetails() // call data from repository
        observeLiveData()
    }
    /**
     * Method to return Data binding associated with current class
     */
  private fun bindings() = mDataBinding as ActivityMainBinding

    private fun observeLiveData() {
        mainViewModel.workshopDetailLiveData.observe(this) {
            bindings().MyRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = workshopDetailAdapter(it,{ selectedItem: WorkshopDetailItem ->
                    listItemClicked(selectedItem)
                },this@MainActivity)
            }
        }

        mainViewModel.loadingStatusLiveData.observe(this) {
            when (it) {
                is LoadingStatusType.Loading -> showProgressBar()
                is LoadingStatusType.Loaded -> hideProgressLoader()
                else -> {}
            }
        }

        mainViewModel.responseErrorLiveData.observe(this) {
        }
    }
    private fun listItemClicked(workshopDetailItem: WorkshopDetailItem){
        Toast.makeText(this@MainActivity,"Selected Supplier is ${workshopDetailItem.facility}", Toast.LENGTH_LONG).show()
    }
}