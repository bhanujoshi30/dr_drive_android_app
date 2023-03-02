package com.example.interviewdemo.ui


import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.example.interviewdemo.R
import com.example.interviewdemo.databinding.ActivitySplashBinding
import com.example.interviewdemo.ui.base.BaseActivity
import com.example.interviewdemo.ui.registration.LoginActivity
import com.example.interviewdemo.utils.Animate
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Splash activity with lottie animation
 *created by Bhanu Prakash Joshi
 * Feb 2023
 * */

class SplashScreen : BaseActivity() {
    private lateinit var splashAnimation : LottieAnimationView
    override fun setActivityLayout(): Int = R.layout.activity_splash

    override fun initialize(savedInstanceState: Bundle?) {
        initSplashProperties()
        initSplashTextProperty()

    }
    /**
     * Method to return Data binding associated with current class
     */
    private fun bindings() = mDataBinding as ActivitySplashBinding

    /**
     * Method to end splash gracefully
     */
   private fun endSplash(){
        if(getLoggedInStatus()){
            val intent =
                Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }else{
            val intent =
                Intent(applicationContext, OnboardingActivity::class.java)
            startActivity(intent)
        }
        Animate.animateSlideLeft( this@SplashScreen)
        finish()
    }
    private fun initSplashProperties(){
        splashAnimation = bindings().appSplash
        splashAnimation.setOnClickListener {
            endSplash()
        }
        splashAnimation.addAnimatorListener(object : Animator.AnimatorListener  {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                endSplash()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
    }

    private fun initSplashTextProperty(){
        bindings().tvAppName.setOnClickListener {
            endSplash()
        }
    }
}