package com.ngiritin.app.ui.profile // <-- Pastiin ini sesuai nama package lu sendiri

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ngiritin.app.R
import com.ngiritin.app.ui.auth.AuthActivity
import com.ngiritin.app.ui.settings.SettingsFragment

class ProfileFragment : Fragment() {

    // Ini pengganti onCreate di Activity
    // Fungsinya buat "nempel" layout XML ke layar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Sambungin ke layout fragment_profile
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    // Ini tempat otak/logika lu bekerja
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Panggil fungsi setup di sini
        setupPasswordExpandable(view)
        setupNavigation(view)
    }

    private fun setupPasswordExpandable(view: View) {
        // Karena ini Fragment, kita harus cari ID lewat 'view.'
        val layoutCollapsed = view.findViewById<ConstraintLayout>(R.id.layoutPasswordCollapsed)
        val layoutExpanded = view.findViewById<LinearLayout>(R.id.layoutPasswordExpanded)
        val tvChange = view.findViewById<TextView>(R.id.tvChangePass)

        val btnCancel = view.findViewById<Button>(R.id.btnCancelPass)
        val btnSave = view.findViewById<Button>(R.id.btnSavePass)

        val etOldPass = view.findViewById<EditText>(R.id.etOldPass)
        val etNewPass = view.findViewById<EditText>(R.id.etNewPass)
        val etConfirmPass = view.findViewById<EditText>(R.id.etConfirmPass)

        tvChange.setOnClickListener {
            layoutCollapsed.visibility = View.GONE
            layoutExpanded.visibility = View.VISIBLE
        }

        // 2. Tombol Cancel diklik -> Tutup Form
        btnCancel.setOnClickListener {
            // Bersihin input
            etOldPass.text.clear()
            etNewPass.text.clear()
            etConfirmPass.text.clear()

            layoutExpanded.visibility = View.GONE
            layoutCollapsed.visibility = View.VISIBLE
        }

        // 3. Tombol Save diklik
        btnSave.setOnClickListener {
            val oldPass = etOldPass.text.toString()
            val newPass = etNewPass.text.toString()
            val confirmPass = etConfirmPass.text.toString()

            // Validasi: Cek kosong atau enggak
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                // Pake requireContext() pengganti 'this'
                Toast.makeText(requireContext(), "Isi semua kolom dulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi: Password baru sama konfirmasi harus sama
            if (newPass != confirmPass) {
                Toast.makeText(requireContext(), "Password baru gak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Sukses
            Toast.makeText(requireContext(), "Password berhasil diganti", Toast.LENGTH_SHORT).show()
            layoutExpanded.visibility = View.GONE
            layoutCollapsed.visibility = View.VISIBLE
        }
    }

    private fun setupLogoutButton(view: View) {
        val btnLogout = view.findViewById<LinearLayout>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logout diklik!", Toast.LENGTH_SHORT).show()
            // Nanti di sini logika logout benerannya

        }
    }

    private fun setupNavigation(view: View) {
        val btnSettings = view.findViewById<LinearLayout>(R.id.btnSettings)
        val btnLogout = view.findViewById<LinearLayout>(R.id.btnLogout)

        // 1. LOGIC KE SETTINGS (Fragment Transaction)
        btnSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,  // Animasi Masuk
                    android.R.anim.fade_out, // Animasi Keluar
                    android.R.anim.fade_in,  // Animasi Back Masuk
                    android.R.anim.fade_out  // Animasi Back Keluar
                )
                // GANTI R.id.fragmentContainer SESUAI ID DI MAIN ACTIVITY KAMU
                // Ini adalah ID dari FrameLayout tempat fragment dimuat
                .replace(R.id.fragmentContainer, SettingsFragment())
                .addToBackStack(null) // PENTING: Biar pas di-Back balik ke Profile
                .commit()
        }

        // 2. LOGIC LOGOUT (Activity Intent)
        btnLogout.setOnClickListener {
            // TODO: Hapus data user/session di SharedPreferences di sini
            // val sharedPref = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
            // sharedPref.edit().clear().apply()

            // Pindah ke Login
            val intent = Intent(requireActivity(), AuthActivity::class.java)

            // Flag Sakti: Hapus semua tumpukan history activity
            // Jadi kalau user tekan back, aplikasi keluar, bukan balik ke profile
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}