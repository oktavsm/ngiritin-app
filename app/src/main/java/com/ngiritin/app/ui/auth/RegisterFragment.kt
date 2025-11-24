package com.ngiritin.app.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngiritin.app.R



import android.widget.Toast
import com.ngiritin.app.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        // 1. Tombol Back Manual (Teks "Back to login")
        binding.tvBackToLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 2. Tombol Sign Up
        binding.btnSignUp.setOnClickListener {
            val name = binding.tilName.editText?.text.toString()
            val email = binding.tilEmail.editText?.text.toString()
            val pass = binding.tilPassword.editText?.text.toString()
            val confirmPass = binding.tilConfirmPassword.editText?.text.toString()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(requireContext(), "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulasi Sukses
            Toast.makeText(requireContext(), "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_LONG).show()
            parentFragmentManager.popBackStack() // Kembali ke halaman Login
        }

        // 3. Sosmed
        val notAvailableAction = {
            Toast.makeText(requireContext(), "Fitur ini belum tersedia di versi Demo", Toast.LENGTH_SHORT).show()
        }
        binding.ivGoogle.setOnClickListener { notAvailableAction() }
        binding.ivFb.setOnClickListener { notAvailableAction() }
    }
}