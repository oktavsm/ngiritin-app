package com.ngiritin.app.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.ngiritin.app.R
import java.util.Calendar

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Expandable Category
        setupExpandableCard(
            header = view.findViewById(R.id.headerCategories),
            content = view.findViewById(R.id.contentCategories),
            arrow = view.findViewById(R.id.arrowCategories)
        )

        // Setup Expandable Wallet
        setupExpandableCard(
            header = view.findViewById(R.id.headerWallets),
            content = view.findViewById(R.id.contentWallets),
            arrow = view.findViewById(R.id.arrowWallets)
        )

        // Setup Time Picker
        setupTimePicker(view)
    }

    // Fungsi sakti buat bikin card bisa expand/collapse + animasi panah
    private fun setupExpandableCard(header: LinearLayout, content: LinearLayout, arrow: ImageView) {
        header.setOnClickListener {
            if (content.visibility == View.VISIBLE) {
                // COLLAPSE
                content.visibility = View.GONE
                arrow.animate().rotation(0f).setDuration(200).start() // Balik ke kanan
            } else {
                // EXPAND
                content.visibility = View.VISIBLE
                arrow.animate().rotation(90f).setDuration(200).start() // Putar ke bawah
            }
        }
    }

    private fun setupTimePicker(view: View) {
        val tvTime = view.findViewById<TextView>(R.id.tvTimePicker)

        tvTime.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Reminder Time")
                .build()

            picker.show(parentFragmentManager, "ngiritin_time_picker")

            picker.addOnPositiveButtonClickListener {
                // Format jadi HH:mm (contoh: 08:05)
                val hour = String.format("%02d", picker.hour)
                val minute = String.format("%02d", picker.minute)
                tvTime.text = "$hour : $minute"
            }
        }
    }
}