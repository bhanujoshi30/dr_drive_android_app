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
) : RecyclerView.Adapter<workshopDetailViewHolder>() {
    lateinit var binding: WorkshopDetailListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): workshopDetailViewHolder {
        val itemBinding = WorkshopDetailListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return workshopDetailViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return workshopDetailsList.size
    }

    override fun onBindViewHolder(holder: workshopDetailViewHolder, position: Int) {
        val workshopItem = workshopDetailsList[position]
        holder.bind(workshopItem, clickListner, context)
    }
}

class workshopDetailViewHolder(val itemBinding: WorkshopDetailListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(
        workshopDetail: WorkshopDetailItem,
        clickListner: (WorkshopDetailItem) -> Unit,
        context: Context
    ) {

        itemBinding.apply {
            tvWorkshopOwner.text = workshopDetail.owner_name
            tvWorkshopAddress.text =
                java.lang.StringBuilder().append(workshopDetail.facility_street)
                    .append(", ")
                    .append(workshopDetail.facility_city)
            tvWorkshopname.text = workshopDetail.facility_name

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
        setContentView(R.layout.fragment_workshop_full_detail)
        show()
    }

    val tvWorkshopName = dialog.findViewById<TextView>(R.id.tv_workshop_name)
    val tvWorkshopAddress = dialog.findViewById<TextView>(R.id.tv_workshop_address)
    val tvWorkshopOwner = dialog.findViewById<TextView>(R.id.tv_workshop_owner)
    val tvWorkshopZipcode = dialog.findViewById<TextView>(R.id.tv_workshop_zipcode)
    val tvWorkshopLicense = dialog.findViewById<TextView>(R.id.tv_workshop_license_expiry)

    tvWorkshopName.text = workshopDetail.facility_name
    tvWorkshopAddress.text = workshopDetail.facility_street
    tvWorkshopOwner.text = workshopDetail.owner_name
    tvWorkshopZipcode.text = workshopDetail.facility_zip_code
    tvWorkshopLicense.text = workshopDetail.expiration_date

}
