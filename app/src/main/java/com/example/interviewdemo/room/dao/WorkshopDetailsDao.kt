package com.example.interviewdemo.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.interviewdemo.models.WorkshopDetailItem

@Dao
interface WorkshopDetailsDao {

    @Query("SELECT * FROM WorkshopDetailItem")
    suspend fun getWorkshopDetails(): List<WorkshopDetailItem>

    @Insert
    suspend fun insertWorkshopDetails(medicine: MutableList<WorkshopDetailItem>)

    @Query("DELETE FROM WorkshopDetailItem")
    suspend fun deleteAllWorkshopDetails()

}