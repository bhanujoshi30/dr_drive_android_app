package com.example.interviewdemo.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.interviewdemo.models.UserDetail
import com.example.interviewdemo.models.WorkshopDetailItem

@Dao
interface UserDetailDao {

    @Query("SELECT * FROM UserDetail")
    suspend fun getUserDetail(): List<UserDetail>

    @Insert
    suspend fun insertUserDetails(userDet: MutableList<UserDetail>)

    @Query("DELETE FROM UserDetail")
    suspend fun deleteUserInfo()
}