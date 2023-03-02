package com.example.interviewdemo.ui.base

import android.app.AlertDialog
import android.app.Dialog

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics

import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.interviewdemo.R
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.ui.OnboardingActivity
import com.example.interviewdemo.ui.SplashScreen
import com.example.interviewdemo.ui.registration.LoginActivity
import com.example.interviewdemo.utils.Constants


/**
 * base class for all activities
 *created by Bhanu Prakash Joshi
 * Feb 2023
 * */
abstract class BaseActivity : AppCompatActivity() {

    private var alertDialogProgress: AlertDialog? = null
    lateinit var mDataBinding: ViewDataBinding
    private val PERMISSIONS_REQUEST_CALL_PHONE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, setActivityLayout())
        initialize(savedInstanceState)
        setActionBarLogo("Dr. Drive")
    }

    /**
     * Method to set layout xml
     *
     * @return layout id
     */
    abstract fun setActivityLayout(): Int

    /**
     * Method to initialize the class data
     *
     * @param savedInstanceState - savedInstanceState
     */
    abstract fun initialize(savedInstanceState: Bundle?)

    fun setActionBarLogo(titleText: String) {
        if (mDataBinding.root.context !is SplashScreen
            && mDataBinding.root.context !is OnboardingActivity
            && mDataBinding.root.context !is LoginActivity
        ) {

            supportActionBar!!.apply {
                title = titleText
//                setIcon(R.mipmap.ic_dr_drive_title)
                setDisplayUseLogoEnabled(true)
                setDisplayShowHomeEnabled(true)
                setBackgroundDrawable(resources.getDrawable(R.drawable.actionbar_background))
            }
        } else {
            supportActionBar?.hide() //hides action bar on runtime
        }
    }

    fun getLoggedInStatus(): Boolean {
        val sfData = getSharedPreferences(Constants.SF_NAME, MODE_PRIVATE)
        return sfData.getBoolean(Constants.LOGIN_STATUS_KEY, false)
    }

    fun setLoggedInStatus(sfValue:Boolean) {
        val sharedPreferences = getSharedPreferences(Constants.SF_NAME, MODE_PRIVATE)
        val sfEdit = sharedPreferences.edit()
        sfEdit.putBoolean(Constants.LOGIN_STATUS_KEY, sfValue)
        sfEdit.apply()
    }

    /**
     * Method to show progress loader
     */
    fun showProgressBar() {
        if (alertDialogProgress == null) {
            val alertDialogBuilderProgress: AlertDialog.Builder?
            val li = LayoutInflater.from(this)
            val promptsView: View = li.inflate(R.layout.progress_bar_layout, null)
            alertDialogBuilderProgress = AlertDialog.Builder(this)
            alertDialogBuilderProgress.setView(promptsView)
            alertDialogBuilderProgress.setCancelable(false)
            alertDialogProgress = alertDialogBuilderProgress.create()
            alertDialogProgress!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialogProgress!!.setContentView(R.layout.progress_bar_layout)
            alertDialogProgress!!.window!!.setBackgroundDrawable(null)
        }
        alertDialogProgress!!.show()
    }

    /**
     * Method to hide progress loader
     */
    fun hideProgressLoader() {
        if (alertDialogProgress != null && alertDialogProgress!!.isShowing) {
            alertDialogProgress?.dismiss()
        }
    }

    /**
     * Method to check and request user to grant manifest permissions
     * */
    private fun checkAndRequestPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showAlertBox("Permission required!", "Please allow call access.") {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    PERMISSIONS_REQUEST_CALL_PHONE
                )
            }
        } else {
            return true
        }
        return false
    }

    fun callWorkshopPhone(phoneNo: String?) {
        if (checkAndRequestPermissions()) {
            if (!phoneNo.isNullOrEmpty()) {
                val intent =
                    Intent(
                        Intent.ACTION_CALL,
                        Uri.parse(
                            java.lang.StringBuilder().append("tel:").append(phoneNo).toString()
                        )
                    )
                startActivity(intent)
            } else {
                showAlertBox("Contact Unavailable!", "Phone call failed.") {}
            }
        }
    }

    private fun showAlertBox(title: String, message: String, listener: () -> Unit) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(R.mipmap.ic_dr_drive_title)
        builder.setPositiveButton("Ok") { _, _ ->
            listener()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun showWorkshopDetailDialog(workshopDetail: WorkshopDetailItem) {
        val dialog = Dialog(this).apply {
            setContentView(R.layout.dialog_layout_workshop_full_detail)
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)

            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels
            window?.setLayout((width * 0.75).toInt(), (height * 0.5).toInt())
            show()
        }

        val tvWorkshopName = dialog.findViewById<TextView>(R.id.tv_workshop_name)
        val tvWorkshopAddress = dialog.findViewById<TextView>(R.id.tv_workshop_address)
        val tvWorkshopOwner = dialog.findViewById<TextView>(R.id.tv_workshop_owner)
        val tvWorkshopZipcode = dialog.findViewById<TextView>(R.id.tv_workshop_zipcode)
        val tvWorkshopLicense = dialog.findViewById<TextView>(R.id.tv_workshop_license_expiry)

        tvWorkshopName.text = workshopDetail.facility_name?.lowercase()
        tvWorkshopAddress.text = workshopDetail.facility_street?.lowercase()
        tvWorkshopOwner.text = workshopDetail.owner_name?.lowercase()
        tvWorkshopZipcode.text = workshopDetail.facility_zip_code?.lowercase()
        tvWorkshopLicense.text = workshopDetail.expiration_date?.lowercase()

    }
}