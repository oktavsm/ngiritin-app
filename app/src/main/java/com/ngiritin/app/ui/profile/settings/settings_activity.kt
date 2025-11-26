package com.ngiritin.app.ui.profile.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngiritin.app.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        setupManageCategories()
        setupOtherButtons()
    }

    private fun setupManageCategories() {
        // Kenalan sama View-nya
        val btnHeader = findViewById<LinearLayout>(R.id.btnManageCategories)
        val layoutExpanded = findViewById<LinearLayout>(R.id.layoutCategoriesExpanded)
        val ivArrow = findViewById<ImageView>(R.id.ivArrowCategories)

        val btnCancel = findViewById<Button>(R.id.btnCancelCategory)
        val btnSave = findViewById<Button>(R.id.btnSaveCategory)

        // 1. Logika Buka Tutup (Toggle)
        btnHeader.setOnClickListener {
            if (layoutExpanded.visibility == View.VISIBLE) {
                // Kalau lagi kebuka -> Tutup
                layoutExpanded.visibility = View.GONE
                ivArrow.rotation = 0f // Balikin panah ke kanan
            } else {
                // Kalau lagi ketutup -> Buka
                layoutExpanded.visibility = View.VISIBLE
                ivArrow.rotation = 90f // Puter panah ke bawah
            }
        }

        // 2. Tombol Cancel -> Tutup aja
        btnCancel.setOnClickListener {
            layoutExpanded.visibility = View.GONE
            ivArrow.rotation = 0f
        }

        // 3. Tombol Save -> Pura-pura save & Tutup
        btnSave.setOnClickListener {
            Toast.makeText(this, "Categories Updated!", Toast.LENGTH_SHORT).show()
            layoutExpanded.visibility = View.GONE
            ivArrow.rotation = 0f
        }
    }

    private fun setupOtherButtons() {
        findViewById<Button>(R.id.btnSaveReminder)?.setOnClickListener {
            Toast.makeText(this, "Reminder Saved!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnSaveWarning)?.setOnClickListener {
            Toast.makeText(this, "Warning Limit Saved!", Toast.LENGTH_SHORT).show()
        }
    }
}