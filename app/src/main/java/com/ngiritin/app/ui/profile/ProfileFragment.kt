package com.ngiritin.app.ui.main.profile // <-- Pastiin ini sesuai nama package lu sendiri

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
        setupLogoutButton(view)
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

        // 1. Tombol Change diklik -> Buka Form
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
}