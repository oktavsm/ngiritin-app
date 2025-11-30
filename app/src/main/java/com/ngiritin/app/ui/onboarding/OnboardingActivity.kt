package com.ngiritin.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngiritin.app.R
import com.ngiritin.app.ui.auth.AuthActivity
import com.ngiritin.app.ui.auth.AuthViewModel
import com.ngiritin.app.ui.navbar.BottomNavbarActivity

class OnboardingActivity : AppCompatActivity() {
    private val onboardingData = listOf(
        Triple("Stay in Control of Your Money", "Track your cash flow effortlessly and keep your finances organized without the stress", R.drawable.bg_onboarding_0),
        Triple("Add Transactions in Seconds", "Use manual input or let AI fill it for you with voice or casual text \n‘super quick, super smooth’.", R.drawable.bg_onboarding_1),
        Triple("Stay Within Your Budget", "Get notified when your spending hits certain limits so your budget stays safe and steady.", R.drawable.bg_onboarding_2),
        Triple("See Where Your Money Goes", "Visual charts and insights help you understand your habits and make smarter financial moves.", R.drawable.bg_onboarding_3)
    )

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

        viewPager = findViewById(R.id.viewPagerText)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        btnNext = findViewById(R.id.btnNext)
        btnGetStarted = findViewById(R.id.btnGetStarted)
        tvSkip = findViewById(R.id.tvSkip)

        val adapter = OnboardingAdapter(onboardingData)
        viewPager.adapter = adapter

        setupIndicators()
        updateIndicators(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
                toggleButtons(position)
            }
        })

        btnNext.setOnClickListener {
            if (viewPager.currentItem < onboardingData.size - 1) viewPager.currentItem += 1
        }
        btnGetStarted.setOnClickListener { finishOnboarding() }
        tvSkip.setOnClickListener { finishOnboarding() }
    }

    private fun setupIndicators() {
        indicatorLayout.removeAllViews()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(dpToPx(4), 0, dpToPx(4), 0)

        for (i in onboardingData.indices) {
            val dot = View(this)
            dot.layoutParams = LinearLayout.LayoutParams(dpToPx(10), dpToPx(10)).apply {
                setMargins(dpToPx(4), 0, dpToPx(4), 0)
            }
            dot.background = getDrawable(R.drawable.bg_indicator_inactive)
            indicatorLayout.addView(dot)
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in 0 until indicatorLayout.childCount) {
            val view = indicatorLayout.getChildAt(i)
            val params = view.layoutParams as LinearLayout.LayoutParams

            if (i == position) {
                params.width = dpToPx(32)
                view.background = getDrawable(R.drawable.bg_indicator_active)
            } else {
                params.width = dpToPx(10)
                view.background = getDrawable(R.drawable.bg_indicator_inactive)
            }
            view.layoutParams = params
        }
    }

    private fun toggleButtons(position: Int) {
        if (position == onboardingData.size - 1) {
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
        val intent = Intent(this@OnboardingActivity, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    inner class OnboardingAdapter(private val data: List<Triple<String, String, Int>>) :
        RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

        inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tvTitle)
            val tvDesc: TextView = view.findViewById(R.id.tvDesc)
            val ivSlideImage: ImageView = view.findViewById(R.id.ivSlideImage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding_text, parent, false)
            return OnboardingViewHolder(view)
        }

        override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
            val item = data[position]
            holder.tvTitle.text = item.first
            holder.tvDesc.text = item.second
            holder.ivSlideImage.setImageResource(item.third)
        }

        override fun getItemCount(): Int = data.size
    }
}