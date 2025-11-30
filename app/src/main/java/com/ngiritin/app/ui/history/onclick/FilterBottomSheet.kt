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

        view.findViewById<TextView>(R.id.btnClose).setOnClickListener { dismiss() }

        setupDatePickers(view)
        setupSlider(view)

        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<FrameLayout>(
            com.google.android.material.R.id.design_bottom_sheet
        )

        bottomSheet?.let { sheet ->
            val displayMetrics = Resources.getSystem().displayMetrics
            val screenHeight = displayMetrics.heightPixels
            val desiredHeight = (screenHeight * 0.85).toInt()

            val layoutParams = sheet.layoutParams
            layoutParams.height = desiredHeight
            sheet.layoutParams = layoutParams

            val behavior = BottomSheetBehavior.from(sheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
            behavior.peekHeight = desiredHeight
        }
    }

    private fun setupSlider(view: View) {
        val slider = view.findViewById<RangeSlider>(R.id.sliderAmount)
        val tvMin = view.findViewById<TextView>(R.id.tvMinAmount)
        val tvMax = view.findViewById<TextView>(R.id.tvMaxAmount)

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

        npDay.minValue = 1
        npDay.maxValue = 31
        npDay.value = 2

        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        npMonth.minValue = 0
        npMonth.maxValue = months.size - 1
        npMonth.displayedValues = months
        npMonth.value = 10

        npYear.minValue = 2020
        npYear.maxValue = 2030
        npYear.value = 2025
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog
}
