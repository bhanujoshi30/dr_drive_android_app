package com.example.interviewdemo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.interviewdemo.R
import com.example.interviewdemo.databinding.FragmentOnboardingBinding
import com.example.interviewdemo.databinding.FragmentWorkshopFullDetailBinding
import com.example.interviewdemo.models.WorkshopDetail
import com.example.interviewdemo.models.WorkshopDetailItem
import com.google.gson.Gson


private const val ARG_PARAM1 = "workshop_detail"


/**
 * A simple [Fragment] subclass.
 * Use the [WorkshopFullDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkshopFullDetailFragment : Fragment() {

    private var workshopDetailString: String? = null
    private lateinit var workshopDetail: WorkshopDetailItem
    private lateinit var binding: FragmentWorkshopFullDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workshopDetailString = it.getString(ARG_PARAM1)
        }
        if(!workshopDetailString.isNullOrEmpty()){
            workshopDetail = Gson().fromJson(workshopDetailString, WorkshopDetailItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate fragment
        binding = FragmentWorkshopFullDetailBinding.inflate(inflater, container, false)
        binding.apply {
            tvWorkshopName.text = workshopDetail.owner_name
            tvWorkshopAddress.text =  java.lang.StringBuilder().append(workshopDetail.facility_street)
                                                                .append(", ")
                                                                .append(workshopDetail.facility_city)
                                                                .append(", ")
                                                                .append(workshopDetail.facility_county)
            tvWorkshopOwner.text = workshopDetail.owner_name
            tvWorkshopZipcode.text = workshopDetail.facility_zip_code
            tvWorkshopLicenseExpiry.text = workshopDetail.expiration_date
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkshopFullDetailFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            WorkshopFullDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}