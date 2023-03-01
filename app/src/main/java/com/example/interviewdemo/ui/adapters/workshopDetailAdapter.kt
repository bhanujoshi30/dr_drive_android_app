package com.example.interviewdemo.ui.adapters

import android.app.Dialog
import android.content.Context

import android.view.LayoutInflater

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.interviewdemo.R
import com.example.interviewdemo.databinding.WorkshopDetailListItemBinding

import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.ui.MainActivity

class workshopDetailAdapter(
    private val workshopDetailsList: List<WorkshopDetailItem>,
    val clickListner: (WorkshopDetailItem) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<WorkshopDetailViewHolder>() {
    lateinit var binding: WorkshopDetailListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopDetailViewHolder {
        val itemBinding = WorkshopDetailListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkshopDetailViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return workshopDetailsList.size
    }

    override fun onBindViewHolder(holder: WorkshopDetailViewHolder, position: Int) {
        val workshopItem = workshopDetailsList[position]
        holder.bind(workshopItem, clickListner, context)
    }
}

class WorkshopDetailViewHolder(val itemBinding: WorkshopDetailListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(
        workshopDetail: WorkshopDetailItem,
        clickListner: (WorkshopDetailItem) -> Unit,
        context: Context
    ) {

        itemBinding.apply {
            tvWorkshopOwner.text = workshopDetail.owner_name?.lowercase()
            tvWorkshopAddress.text =
                java.lang.StringBuilder().append(workshopDetail.facility_street)
                    .append(", ")
                    .append(workshopDetail.facility_city).toString().lowercase()
            tvWorkshopname.text = workshopDetail.facility_name?.lowercase()

            ivPhoneCall.setOnClickListener {
                if (context is MainActivity) {
                    context.callWorkshopPhone(workshopDetail.facility)
                }
            }
        }


        itemBinding.root.setOnClickListener {
            showWorkshopDetailDialog(workshopDetail, context)
        }
    }
}

fun showWorkshopDetailDialog(workshopDetail: WorkshopDetailItem, context: Context) {
    val dialog = Dialog(context).apply {
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
