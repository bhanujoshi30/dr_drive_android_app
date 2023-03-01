package com.example.interviewdemo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class to hold workshop item
 */

@Entity
data class WorkshopDetailItem(
    @ColumnInfo(name = "business_type") val business_type: String?,
    @ColumnInfo(name = "expiration_date") val expiration_date: String?,
    @ColumnInfo(name = "facility") @PrimaryKey val facility: String,
    @ColumnInfo(name = "facility_city") val facility_city: String?,
    @ColumnInfo(name = "facility_county") val facility_county: String?,
    @ColumnInfo(name = "facility_name") val facility_name: String?,
    @ColumnInfo(name = "facility_name_overflow") val facility_name_overflow: String?,
    @ColumnInfo(name = "facility_state") val facility_state: String?,
    @ColumnInfo(name = "facility_street") val facility_street: String?,
    @ColumnInfo(name = "facility_zip_code") val facility_zip_code: String?,
    @ColumnInfo(name = "last_renewal_date") val last_renewal_date: String?,
    @ColumnInfo(name = "origional_issuance_date") val origional_issuance_date: String?,
    @ColumnInfo(name = "owner_name") val owner_name: String?,
    @ColumnInfo(name = "owner_name_overflow") val owner_name_overflow: String?
)