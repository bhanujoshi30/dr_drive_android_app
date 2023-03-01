package com.example.interviewdemo.ui.adapters


import android.view.LayoutInflater

import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView

import com.example.interviewdemo.databinding.WorkshopDetailListItemBinding

import com.example.interviewdemo.models.WorkshopDetailItem

import com.example.interviewdemo.utils.BaseCallbackTypes


class workshopDetailAdapter(
    private val workshopDetailsList: List<WorkshopDetailItem>,
    val clickListner: (WorkshopDetailItem, BaseCallbackTypes) -> Unit,
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
        holder.bind(workshopItem, clickListner)
    }
}

class WorkshopDetailViewHolder(private val itemBinding: WorkshopDetailListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(
        workshopDetail: WorkshopDetailItem,
        clickListener: (WorkshopDetailItem, BaseCallbackTypes) -> Unit,
    ) {

        itemBinding.apply {
            tvWorkshopOwner.text = workshopDetail.owner_name?.lowercase()
            tvWorkshopAddress.text =
                java.lang.StringBuilder().append(workshopDetail.facility_street)
                    .append(", ")
                    .append(workshopDetail.facility_city).toString().lowercase()
            tvWorkshopname.text = workshopDetail.facility_name?.lowercase()

            ivPhoneCall.setOnClickListener {
                clickListener(workshopDetail, BaseCallbackTypes.PhoneCall)
            }
        }


        itemBinding.root.setOnClickListener {
            clickListener(workshopDetail, BaseCallbackTypes.ShowDetails)
        }
    }
}
