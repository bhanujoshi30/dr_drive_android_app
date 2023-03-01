package com.example.interviewdemo.utils

import android.app.Activity
import android.content.Context
import com.example.interviewdemo.R

object Animate {
    fun animateSlideLeft(context: Context) {
        (context as Activity).overridePendingTransition(
            R.anim.animate_slide_left_enter,
            R.anim.animate_slide_left_exit
        )
    }
}