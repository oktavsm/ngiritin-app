package com.ngiritin.app.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngiritin.app.R

class OnboardingActivity : AppCompatActivity() {

    // --- DATA ---
    private val onboardingData = listOf(
        Triple("Stay in Control of Your Money", "Track your cash flow effortlessly and keep your finances organized without the stress", R.drawable.bg_onboarding_0),
        Triple("Add Transactions in Seconds", "Use manual input or let AI fill it for you with voice or casual text \n‘super quick, super smooth’.", R.drawable.bg_onboarding_1),
        Triple("Stay Within Your Budget", "Get notified when your spending hits certain limits so your budget stays safe and steady.", R.drawable.bg_onboarding_2),
        Triple("See Where Your Money Goes", "Visual charts and insights help you understand your habits and make smarter financial moves.", R.drawable.bg_onboarding_3)
    )

    // --- VIEW ---
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: FloatingActionButton
    private lateinit var btnGetStarted: Button
    private lateinit var tvSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPagerText)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        btnNext = findViewById(R.id.btnNext)
        btnGetStarted = findViewById(R.id.btnGetStarted)
        tvSkip = findViewById(R.id.tvSkip)

        // 1. Setup Adapter
        val adapter = OnboardingAdapter(onboardingData)
        viewPager.adapter = adapter

        // 2. Setup Indikator (INI YANG BARU)
        // Kita bikin titik-titiknya dulu sesuai jumlah data
        setupIndicators()

        // 3. Set indikator awal biar yang pertama nyala
        updateIndicators(0)

        // 4. Listener Geser
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

    // --- FUNGSI BARU: Bikin Titik Otomatis ---
    private fun setupIndicators() {
        indicatorLayout.removeAllViews() // Bersihin dulu isi XML (si dummy dibuang)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // Kasih jarak antar titik (Kiri 4dp, Kanan 4dp)
        layoutParams.setMargins(dpToPx(4), 0, dpToPx(4), 0)

        for (i in onboardingData.indices) {
            val dot = View(this)
            // Set tinggi default dot jadi 10dp
            dot.layoutParams = LinearLayout.LayoutParams(dpToPx(10), dpToPx(10)).apply {
                setMargins(dpToPx(4), 0, dpToPx(4), 0)
            }
            // Pasang background default (abu-abu)
            dot.background = getDrawable(R.drawable.bg_indicator_inactive)

            // Masukin ke layout
            indicatorLayout.addView(dot)
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in 0 until indicatorLayout.childCount) {
            val view = indicatorLayout.getChildAt(i)

            // Ambil layoutParams yang sudah ada (biar margin gak ilang)
            val params = view.layoutParams as LinearLayout.LayoutParams

            if (i == position) {
                // Yang Aktif: Lebar 32dp, Warna Biru
                params.width = dpToPx(32)
                view.background = getDrawable(R.drawable.bg_indicator_active)
            } else {
                // Yang Pasif: Lebar 10dp, Warna Abu
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
        Toast.makeText(this, "Mantap! Masuk ke Login.", Toast.LENGTH_SHORT).show()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    // --- ADAPTER ---
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