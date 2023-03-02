package com.example.interviewdemo.repositories.workshop

import com.example.interviewdemo.models.WorkshopDetail
import com.example.interviewdemo.models.WorkshopDetailItem
import retrofit2.Response


interface WorkshopDetailsRepository {

    suspend fun getWorkshopList(): Response<WorkshopDetail>

    suspend fun getWorkshopsDetails(): List<WorkshopDetailItem>

    suspend fun insertAll(workshopDetailList: MutableList<WorkshopDetailItem>)

    suspend fun deleteAll()
}