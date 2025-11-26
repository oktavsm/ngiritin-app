package com.ngiritin.app.ui.history.onclick

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.NumberPicker
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import com.ngiritin.app.R

class FilterBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup tombol Close
        view.findViewById<TextView>(R.id.btnClose).setOnClickListener {
            dismiss()
        }

        setupDatePickers(view)
        setupSlider(view)

        // Bikin full expanded pas dibuka
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }
    override fun onStart() {
        super.onStart()

        // Ambil referensi ke Dialog dan View BottomSheet-nya
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<FrameLayout>(/* id = */ com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let { sheet ->
            // 1. Hitung Tinggi Layar
            val displayMetrics = Resources.getSystem().displayMetrics
            val screenHeight = displayMetrics.heightPixels

            // 2. Tentukan mau seberapa tinggi (Misal: 85% dari layar)
            // Sisa 15% di atas bakal transparan/gelap memperlihatkan halaman History
            val desiredHeight = (screenHeight * 0.85).toInt()

            // 3. Set tinggi Layout
            val layoutParams = sheet.layoutParams
            layoutParams.height = desiredHeight
            sheet.layoutParams = layoutParams

            // 4. Konfigurasi Behavior
            val behavior = BottomSheetBehavior.from(sheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED // Langsung muncul full (sesuai tinggi yg diatur)
            behavior.skipCollapsed = true // Kalau di-swipe ke bawah, langsung nutup (gak nyangkut setengah)
            behavior.peekHeight = desiredHeight
        }
    }



    private fun setupSlider(view: View) {
        val slider = view.findViewById<RangeSlider>(R.id.sliderAmount)
        val tvMin = view.findViewById<TextView>(R.id.tvMinAmount)
        val tvMax = view.findViewById<TextView>(R.id.tvMaxAmount)

        // Set Values default (kalau xml error)
        slider.setValues(20f, 86f)

        slider.addOnChangeListener { _, _, _ ->
            val values = slider.values
            tvMin.text = values[0].toInt().toString()
            tvMax.text = values[1].toInt().toString()
        }
    }

    private fun setupDatePickers(view: View) {
        val npDay = view.findViewById<NumberPicker>(R.id.npDay)
        val npMonth = view.findViewById<NumberPicker>(R.id.npMonth)
        val npYear = view.findViewById<NumberPicker>(R.id.npYear)

        // Setup Day (1-31)
        npDay.minValue = 1
        npDay.maxValue = 31
        npDay.value = 2 // Contoh hari ini

        // Setup Month (String Array)
        val months = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        npMonth.minValue = 0
        npMonth.maxValue = months.size - 1
        npMonth.displayedValues = months
        npMonth.value = 10 // November

        // Setup Year (2020-2030)
        npYear.minValue = 2020
        npYear.maxValue = 2030
        npYear.value = 2025
    }
    // Pastikan theme ini tetap ada biar rounded corner-nya gak kotak putih
    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

}