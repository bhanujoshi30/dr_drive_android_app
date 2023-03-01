package com.example.interviewdemo.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class testWorkShop(

    val business_type: String?,
    val expiration_date: String?,
     val facility: String,
     val facility_city: String?,
   val facility_county: String?,
    val facility_name: String?,
     val facility_name_overflow: String?,
 val facility_state: String?,
    val facility_street: String?,
     val facility_zip_code: String?,
     val georeference: Georeference?,
   val last_renewal_date: String?,
    val origional_issuance_date: String?,
  val owner_name: String?,
     val owner_name_overflow: String?
)
