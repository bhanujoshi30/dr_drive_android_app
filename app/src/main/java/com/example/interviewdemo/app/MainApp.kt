package com.example.interviewdemo.app

import android.app.Application
import android.content.Context
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt Application class
 * Created by Bhanu Prakash Joshi
 * Hilt implementation for DI
 */
@HiltAndroidApp
class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }

}