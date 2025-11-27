package com.ngiritin.app.ui.profile.settings

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngiritin.app.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Logika Tombol Save di halaman Settings
        findViewById<Button>(R.id.btnSaveReminder).setOnClickListener {
            Toast.makeText(this, "Reminder Saved!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnSaveWarning).setOnClickListener {
            Toast.makeText(this, "Warning Limit Saved!", Toast.LENGTH_SHORT).show()
        }

        // Tambahan: Tombol Back di pojok kiri atas (Kalau ada)
        // findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
    }
}