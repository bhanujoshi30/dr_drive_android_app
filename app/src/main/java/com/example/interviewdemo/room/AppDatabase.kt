package com.example.interviewdemo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.room.dao.WorkshopDetailsDao

@Database(entities = [WorkshopDetailItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun workshopDetailDao(): WorkshopDetailsDao

}