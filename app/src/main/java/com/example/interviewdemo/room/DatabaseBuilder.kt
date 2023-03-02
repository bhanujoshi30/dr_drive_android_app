package com.example.interviewdemo.room

import android.content.Context
import androidx.room.Room
import com.example.interviewdemo.app.MainApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseBuilder {

    @Provides
    @Singleton
    @Synchronized fun getInstance(): AppDatabase {
        return buildRoomDB(MainApp.appContext)
    }

    private fun buildRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dr-drive-database"
        ).build()

    @Provides
    fun getWorkshopDetailDao(appDatabase: AppDatabase) = appDatabase.workshopDetailDao()

    @Provides
    fun getUserDetailDao(appDatabase: AppDatabase) = appDatabase.userDetailDao()
}