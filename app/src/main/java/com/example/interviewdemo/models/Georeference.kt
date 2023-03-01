package com.example.interviewdemo.models

import androidx.room.ColumnInfo
import androidx.room.TypeConverter
import com.google.gson.Gson

data class Georeference(
    val coordinates: List<Double>? = null,
    val type: String? =null
)
//class GeoreferenceConverter {
//
//    @TypeConverter
//    fun toGeoreference(value: String?): Georeference {
//        if (value == null || value.isEmpty()) {
//            return Georeference(List(1) { 0.0 },"")
//        }else{
//            return Gson().fromJson(value, Georeference::class.java)
//        }
//    }
//
//    @TypeConverter
//    fun toString(geoRef: Georeference?): String {
//
//        val string = ""
//
//        if (geoRef == null) {
//            return string
//        }
//
//       return Gson().toJson(geoRef, Georeference::class.java)
//    }
//}