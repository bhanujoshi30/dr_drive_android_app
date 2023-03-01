package com.example.interviewdemo.network

import com.example.interviewdemo.models.WorkshopDetail
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("icjc-x44x.json")
    suspend fun getWorkshopDetailsList(): Response<WorkshopDetail>
}