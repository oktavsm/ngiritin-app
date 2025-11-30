package com.ngiritin.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngiritin.app.R
import com.ngiritin.app.ui.auth.AuthActivity
import com.ngiritin.app.ui.auth.AuthViewModel
import com.ngiritin.app.ui.navbar.BottomNavbarActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var indicatorLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: FloatingActionButton
    private lateinit var btnGetStarted: Button
    private lateinit var tvSkip: TextView

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authViewModel.checkCurrentUser() != null) {
            val intent = Intent(this, BottomNavbarActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_onboarding)

        initViews()
        setupViewPager()
        setupButtons()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPagerText)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        btnNext = findViewById(R.id.btnNext)
        btnGetStarted = findViewById(R.id.btnGetStarted)
        tvSkip = findViewById(R.id.tvSkip)
    }

    private fun setupViewPager() {
        val adapter = OnboardingAdapter(OnboardingContent.list)
        viewPager.adapter = adapter

        setupIndicators(OnboardingContent.list.size)
        updateIndicators(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
                toggleButtons(position, OnboardingContent.list.size)
            }
        })
    }

    private fun setupButtons() {
        btnNext.setOnClickListener {
            if (viewPager.currentItem < OnboardingContent.list.size - 1) {
                viewPager.currentItem += 1
            }
        }
        btnGetStarted.setOnClickListener { finishOnboarding() }
        tvSkip.setOnClickListener { finishOnboarding() }
    }

    private fun setupIndicators(count: Int) {
        indicatorLayout.removeAllViews()
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(dpToPx(4), 0, dpToPx(4), 0)

        for (i in 0 until count) {
            val dot = View(this)
            dot.layoutParams = LinearLayout.LayoutParams(dpToPx(10), dpToPx(10)).apply {
                setMargins(dpToPx(4), 0, dpToPx(4), 0)
            }
            dot.background = ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive)
            indicatorLayout.addView(dot)
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in 0 until indicatorLayout.childCount) {
            val view = indicatorLayout.getChildAt(i)
            val params = view.layoutParams as LinearLayout.LayoutParams

            if (i == position) {
                params.width = dpToPx(32)
                view.background = ContextCompat.getDrawable(this, R.drawable.bg_indicator_active)
            } else {
                params.width = dpToPx(10)
                view.background = ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive)
            }
            view.layoutParams = params
        }
    }

    private fun toggleButtons(position: Int, size: Int) {
        if (position == size - 1) {
            btnNext.visibility = View.GONE
            tvSkip.visibility = View.GONE
            btnGetStarted.visibility = View.VISIBLE
        } else {
            btnNext.visibility = View.VISIBLE
            tvSkip.visibility = View.VISIBLE
            btnGetStarted.visibility = View.GONE
        }
    }

    private fun finishOnboarding() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}