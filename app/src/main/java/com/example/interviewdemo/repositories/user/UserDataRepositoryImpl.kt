package com.example.interviewdemo.repositories.user

import com.example.interviewdemo.models.UserDetail
import com.example.interviewdemo.room.dao.UserDetailDao
import javax.inject.Inject

/**
 * User Detail repository
 */
class UserDataRepositoryImpl @Inject constructor(
    private val userDetailDao: UserDetailDao
) : UserDataRepository{
    override suspend fun getUserData(): List<UserDetail> {
        return userDetailDao.getUserDetail()
    }

    override suspend fun insertUserData(userDet: MutableList<UserDetail>) {
        return userDetailDao.insertUserDetails(userDet)
    }

    override suspend fun deleteUserData() {
        return userDetailDao.deleteUserInfo()
    }

}