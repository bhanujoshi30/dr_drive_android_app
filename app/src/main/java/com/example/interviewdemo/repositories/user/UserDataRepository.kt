package com.example.interviewdemo.repositories.user

import com.example.interviewdemo.models.UserDetail


interface UserDataRepository {
    suspend fun getUserData(): List<UserDetail>
    suspend fun insertUserData(userDet: MutableList<UserDetail>)
    suspend fun deleteUserData()
}