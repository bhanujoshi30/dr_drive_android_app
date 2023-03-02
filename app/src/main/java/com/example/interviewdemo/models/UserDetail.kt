package com.example.interviewdemo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Data class to hold user details
 */
@Entity
data class UserDetail (
    @ColumnInfo(name = "first_name") @PrimaryKey  val first_name: String,
    @ColumnInfo(name = "last_name") val last_name: String?,
    @ColumnInfo(name = "pincode") val pincode: String?,
    @ColumnInfo(name = "dob") val dob: String?,
    @ColumnInfo(name = "gender") val gender: String?
        )