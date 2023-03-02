package com.example.interviewdemo.ui



import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interviewdemo.R
import com.example.interviewdemo.databinding.ActivityMainBinding
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.ui.adapters.workshopDetailAdapter
import com.example.interviewdemo.ui.base.BaseActivity
import com.example.interviewdemo.ui.registration.LoginActivity
import com.example.interviewdemo.utils.Animate
import com.example.interviewdemo.utils.BaseCallbackTypes
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
        setLoggedInStatus(true)
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
                adapter = workshopDetailAdapter(it) { selectedItem: WorkshopDetailItem, callbackType: BaseCallbackTypes ->
                    when(callbackType){
                        BaseCallbackTypes.PhoneCall -> callWorkshopPhone(selectedItem.facility)
                        BaseCallbackTypes.ShowDetails ->{showWorkshopDetailDialog(selectedItem)}
                    }
                }
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

        mainViewModel.userDetailLiveData.observe(this){
            for (userDetail in it){
                setActionBarLogo(java.lang.StringBuilder().append(" Welcome, ").append(userDetail.first_name).toString())
                break
            }
        }
        mainViewModel.dbClearStatus.observe(this){
            if(it){
                setLoggedInStatus(false)
                val intent =
                    Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                Animate.animateSlideLeft( this@MainActivity)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> mainViewModel.logout()
            R.id.refresh -> mainViewModel.recallDataFromServer()
        }
        return super.onOptionsItemSelected(item)
    }
}