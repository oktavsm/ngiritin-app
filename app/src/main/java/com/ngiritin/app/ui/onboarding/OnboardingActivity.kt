package com.ngiritin.app.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast // Import ini buat bikin pesan pop-up
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngiritin.app.R

class OnboardingActivity : AppCompatActivity() {

    // Data Teks Onboarding
    private val onboardingData = listOf(
        Pair("Stay in Control of Your Money", "Track your cash flow effortlessly and keep your finances organized without the stress."),
        Pair("Know Where Your Money Goes", "Visualize your spending habits with intuitive charts and detailed reports."),
        Pair("Save More for Your Dreams", "Set smart budgets and achieve your financial goals faster than ever."),
        Pair("Secure and Private", "Your financial data is encrypted and safe. Only you have access to your wallet.")
    )

    private lateinit var indicatorLayout: LinearLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // 1. Kenalan sama View
        viewPager = findViewById(R.id.viewPagerText)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        val btnNext = findViewById<FloatingActionButton>(R.id.btnNext)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)

        // 2. Pasang Adapter (Penghubung Data ke Layout)
        val adapter = OnboardingAdapter(onboardingData)
        viewPager.adapter = adapter

        // 3. Pasang Mata-mata (Listener) Geseran Layar
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Update titik-titik indikator pas digeser
                updateIndicators(position)
            }
        })

        // 4. Logika Tombol Next
        btnNext.setOnClickListener {
            if (viewPager.currentItem < onboardingData.size - 1) {
                // Kalau belum halaman terakhir, geser ke kanan
                viewPager.currentItem += 1
            } else {
                // Kalau udah halaman terakhir, jalankan fungsi 'Selesai'
                finishOnboarding()
            }
        }

        // 5. Logika Tombol Skip
        tvSkip.setOnClickListener {
            // Langsung jalankan fungsi 'Selesai'
            finishOnboarding()
        }
    }

    // --- FUNGSI TEST DRIVE (Cuma buat ngecek tombol jalan/nggak) ---
    private fun finishOnboarding() {
        // Nanti kalau LoginActivity udah siap, ganti baris ini pake Intent.
        // Sekarang kita pake Toast dulu biar aplikasi gak crash.
        Toast.makeText(this, "Logika Berhasil! Harusnya pindah ke Login.", Toast.LENGTH_SHORT).show()
    }

    // Fungsi Update Tampilan Indikator (Kapsul Biru)
    private fun updateIndicators(position: Int) {
        for (i in 0 until indicatorLayout.childCount) {
            val view = indicatorLayout.getChildAt(i)
            val params = view.layoutParams as LinearLayout.LayoutParams

            if (i == position) {
                // Indikator Aktif: Lebar 32dp, Warna Biru
                params.width = dpToPx(32)
                view.background = getDrawable(R.drawable.bg_indicator_active)
            } else {
                // Indikator Pasif: Lebar 10dp, Warna Abu
                params.width = dpToPx(10)
                view.background = getDrawable(R.drawable.bg_indicator_inactive)
            }
            view.layoutParams = params
        }
    }

    // Helper: Ubah DP ke Pixel
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    // --- ADAPTER CLASS ---
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