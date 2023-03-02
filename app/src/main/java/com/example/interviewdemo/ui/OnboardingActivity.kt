package com.example.interviewdemo.ui

import android.content.Intent

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.interviewdemo.R

import com.example.interviewdemo.databinding.ActivityOnboardingBinding
import com.example.interviewdemo.ui.adapters.OnboardingViewPagerAdapter
import com.example.interviewdemo.ui.base.BaseActivity
import com.example.interviewdemo.ui.registration.LoginActivity
import com.example.interviewdemo.utils.Animate
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : BaseActivity() {
    private lateinit var mViewPager: ViewPager2
    override fun setActivityLayout(): Int = R.layout.activity_onboarding

    override fun initialize(savedInstanceState: Bundle?) {
        mViewPager = bindings().vpOnboarding
        mViewPager.adapter = OnboardingViewPagerAdapter(this, this)
        mViewPager.offscreenPageLimit = 1
        TabLayoutMediator(bindings().tlPageIndicator, mViewPager) { _, _ -> }.attach()
        bindings().tvSkip.setOnClickListener {
            val intent =
                Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            Animate.animateSlideLeft(this)
            finish()
        }

        bindings().tvNext.setOnClickListener {
            if (mViewPager.currentItem ==2 ) {
                val intent =
                    Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                Animate.animateSlideLeft(this@OnboardingActivity)
                finish()
            } else {
                mViewPager.setCurrentItem(mViewPager.currentItem + 1, true)
            }
        }
    }
    /**
     * Method to return Data binding associated with current class
     */
    private fun bindings() = mDataBinding as ActivityOnboardingBinding

}