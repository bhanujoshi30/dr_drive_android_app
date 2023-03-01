package com.example.interviewdemo.di

import com.example.interviewdemo.network.ApiInterface
import com.example.interviewdemo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private var apiInstance: ApiInterface? = null

    @Provides
    fun getApiInterface(): ApiInterface {
        if(apiInstance == null) {
            val logging = HttpLoggingInterceptor().apply { HttpLoggingInterceptor.Level.BODY }
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.apply {
                writeTimeout(300, TimeUnit.SECONDS)
                readTimeout(300, TimeUnit.SECONDS)
                connectTimeout(300, TimeUnit.SECONDS)
                addInterceptor(logging)
            }
            val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
            apiInstance = retrofit.create(ApiInterface::class.java)
        }
        return apiInstance!!
    }
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}