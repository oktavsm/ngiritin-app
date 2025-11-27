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
        setupManageWallets() // <-- INI FITUR BARU KITA
        setupOtherButtons()
    }

    private fun setupManageCategories() {
        val btnHeader = findViewById<LinearLayout>(R.id.btnManageCategories)
        val layoutExpanded = findViewById<LinearLayout>(R.id.layoutCategoriesExpanded)
        val ivArrow = findViewById<ImageView>(R.id.ivArrowCategories)

        val btnCancel = findViewById<Button>(R.id.btnCancelCategory)
        val btnSave = findViewById<Button>(R.id.btnSaveCategory)

        btnHeader.setOnClickListener {
            toggleVisibility(layoutExpanded, ivArrow)
        }

        btnCancel.setOnClickListener {
            closeSection(layoutExpanded, ivArrow)
        }

        btnSave.setOnClickListener {
            Toast.makeText(this, "Categories Updated!", Toast.LENGTH_SHORT).show()
            closeSection(layoutExpanded, ivArrow)
        }
    }

    private fun setupManageWallets() {
        // Kenalan dulu sama View barunya
        val btnHeader = findViewById<LinearLayout>(R.id.btnManageWallets)
        val layoutExpanded = findViewById<LinearLayout>(R.id.layoutWalletsExpanded)
        val ivArrow = findViewById<ImageView>(R.id.ivArrowWallets)

        val btnCancel = findViewById<Button>(R.id.btnCancelWallet)
        val btnSave = findViewById<Button>(R.id.btnSaveWallet)

        // 1. Logika Buka Tutup
        btnHeader.setOnClickListener {
            toggleVisibility(layoutExpanded, ivArrow)
        }

        // 2. Tombol Cancel
        btnCancel.setOnClickListener {
            closeSection(layoutExpanded, ivArrow)
        }

        // 3. Tombol Save
        btnSave.setOnClickListener {
            Toast.makeText(this, "Wallets Updated!", Toast.LENGTH_SHORT).show()
            closeSection(layoutExpanded, ivArrow)
        }
    }

    // Fungsi helper biar gak nulis kode ulang-ulang (DRY Principle)
    private fun toggleVisibility(layout: View, arrow: ImageView) {
        if (layout.visibility == View.VISIBLE) {
            layout.visibility = View.GONE
            arrow.rotation = 0f
        } else {
            layout.visibility = View.VISIBLE
            arrow.rotation = 90f
        }
    }

    private fun closeSection(layout: View, arrow: ImageView) {
        layout.visibility = View.GONE
        arrow.rotation = 0f
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