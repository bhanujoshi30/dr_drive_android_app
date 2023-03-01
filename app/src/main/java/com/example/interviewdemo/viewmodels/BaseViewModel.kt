package com.example.interviewdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewdemo.R
import com.example.interviewdemo.app.MainApp
import com.example.interviewdemo.utils.LoadingStatusType
import kotlinx.coroutines.CoroutineExceptionHandler
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {
    val loadingStatusLiveData by lazy { MutableLiveData<LoadingStatusType>() }
    val responseErrorLiveData by lazy { MutableLiveData<ResponseType.Error>() }
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        loadingStatusLiveData.postValue(LoadingStatusType.Loaded)
        when (exception) {
            is SocketTimeoutException -> {
                responseErrorLiveData.postValue(ResponseType.Error(MainApp.appContext.getString(R.string.ConnectionTimeout)))
            }
            is UnknownHostException -> {
                responseErrorLiveData.postValue(ResponseType.Error(MainApp.appContext.getString(R.string.InternetCheck)))
            }
            is IOException -> {
                responseErrorLiveData.postValue(ResponseType.Error(MainApp.appContext.getString(R.string.Timeout)))
            }
            else -> {
                responseErrorLiveData.postValue(ResponseType.Error(MainApp.appContext.getString(R.string.TechnicalError)))
            }
        }
    }
}