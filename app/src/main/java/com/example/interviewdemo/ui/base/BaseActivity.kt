package com.example.interviewdemo.ui.base

import android.app.AlertDialog
import android.app.Dialog

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.interviewdemo.R
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.ui.SplashScreen


/**
 * base class for all activities
 *created by Bhanu Prakash Joshi
 * Feb 2023
 * */
abstract class BaseActivity : AppCompatActivity() {

    private var alertDialogProgress: AlertDialog? = null
    lateinit var mDataBinding: ViewDataBinding
    lateinit var titleLogo: ImageView
    private val PERMISSIONS_REQUEST_CALL_PHONE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, setActivityLayout())
        initialize(savedInstanceState)
        setActionBarLogo()
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

    private fun setActionBarLogo() {
        if (mDataBinding.root.context !is SplashScreen) {

            supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            titleLogo = ImageView(supportActionBar?.themedContext)
            titleLogo.apply {
                scaleType = ImageView.ScaleType.CENTER
                setImageResource(R.mipmap.ic_dr_drive_title)
            }
            supportActionBar?.customView = titleLogo
        }
    }

    /**
     * Method to show progress loader
     */
    fun showProgressBar() {
        if (alertDialogProgress == null) {
            val alertDialogBuilderProgress: AlertDialog.Builder?
            val li = LayoutInflater.from(this)
            val promptsView: View = li.inflate(R.layout.progress_bar_layout, null)
            alertDialogBuilderProgress = AlertDialog.Builder(
                this
            )
            alertDialogBuilderProgress.setView(promptsView)
            alertDialogBuilderProgress
                .setCancelable(false)

            // create alert dialog
            alertDialogProgress = alertDialogBuilderProgress.create()
            alertDialogProgress!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialogProgress!!.setContentView(R.layout.progress_bar_layout)
            alertDialogProgress!!.window!!.setBackgroundDrawable(null)
        }

        // show it
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
    fun checkAndRequestPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showAlertBox("Permission required!", "Please grant phone call access.") {
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
                showAlertBox("Contact Unavailable!", "Sorry, cannot make phone call.") {}
            }
        }
    }

    fun showAlertBox(title: String, message: String, listener: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.setPositiveButton("Ok") { _, _ ->
            listener()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    fun showWorkshopDetailDialog(workshopDetail: WorkshopDetailItem) {
        val dialog = Dialog(this).apply {
            setContentView(R.layout.dialog_layout_workshop_full_detail)
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