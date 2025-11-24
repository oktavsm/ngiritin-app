package com.ngiritin.app.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    // 1. Data Teks (Judul & Deskripsi)
    private val onboardingData = listOf(
        Pair("Stay in Control of Your Money", "Track your cash flow effortlessly and keep your finances organized without the stress."),
        Pair("Know Where Your Money Goes", "Visualize your spending habits with intuitive charts and detailed reports."),
        Pair("Save More for Your Dreams", "Set smart budgets and achieve your financial goals faster than ever."),
        Pair("Secure and Private", "Your financial data is encrypted and safe. Only you have access to your wallet.")
    )

    // 2. Data Gambar Background (Sesuai urutan slide)
    // NANTI GANTI: R.drawable.bg_onboarding_full dengan nama file gambar kamu yang beda-beda
    private val backgroundImages = listOf(
        R.drawable.bg_onboarding_0, // Background Slide 1
        R.drawable.bg_onboarding_1, // Background Slide 2 (Ganti ini nanti, misal: R.drawable.bg_slide_2)
        R.drawable.bg_onboarding_2, // Background Slide 3 (Ganti ini nanti)
        R.drawable.bg_onboarding_3  // Background Slide 4 (Ganti ini nanti)
    )

    // --- VIEW ---
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var ivFullBackground: ImageView // Ini si background yang mau kita ganti-ganti

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // 1. Inisialisasi View
        viewPager = findViewById(R.id.viewPagerText)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        ivFullBackground = findViewById(R.id.ivFullBackground) // Kenalan sama ImageView background
        val btnNext = findViewById<FloatingActionButton>(R.id.btnNext)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)

        // 2. Pasang Adapter
        val adapter = OnboardingAdapter(onboardingData)
        viewPager.adapter = adapter

        // 3. Pasang Mata-mata (Listener)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // A. Update Titik-titik Indikator
                updateIndicators(position)

                // B. Update Background sesuai posisi halaman
                updateBackground(position)
            }
        })

        // 4. Logika Tombol Next
        btnNext.setOnClickListener {
            if (viewPager.currentItem < onboardingData.size - 1) {
                viewPager.currentItem += 1
            } else {
                finishOnboarding()
            }
        }

        // 5. Logika Tombol Skip
        tvSkip.setOnClickListener {
            finishOnboarding()
        }
    }

    // --- LOGIC FUNGSI ---

    // Fungsi Ganti Background
    private fun updateBackground(position: Int) {
        // Ambil gambar dari list sesuai nomor halaman, terus pasang ke ImageView
        ivFullBackground.setImageResource(backgroundImages[position])

        // Tips Pro: Kalau mau ada efek fade in/out biar halus, butuh animasi tambahan.
        // Tapi untuk sekarang, logic ganti langsung ini yang paling stabil & cepat.
    }

    // Fungsi Update Indikator (Kapsul Biru)
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

    private fun finishOnboarding() {
        Toast.makeText(this, "Sip! Pindah ke Login.", Toast.LENGTH_SHORT).show()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    // --- ADAPTER ---
    inner class OnboardingAdapter(private val data: List<Pair<String, String>>) :
        RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

        inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tvTitle)
            val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_onboarding_text, parent, false)
            return OnboardingViewHolder(view)
        }

        override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
            holder.tvTitle.text = data[position].first
            holder.tvDesc.text = data[position].second
        }

        override fun getItemCount(): Int = data.size
    }
}