package com.example.interviewdemo.utils

sealed class LoadingStatusType{
    object Loading : LoadingStatusType()
    object Loaded : LoadingStatusType()
}
