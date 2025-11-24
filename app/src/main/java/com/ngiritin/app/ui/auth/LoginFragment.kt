package com.ngiritin.app.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngiritin.app.R


import android.content.Intent
import android.widget.Toast
import com.ngiritin.app.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        // 1. Logic Tombol Login
        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString().trim()
            val password = binding.tilPassword.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hardcode Demo Login
            if (email == "admin" && password == "admin") {
                val intent = Intent(requireActivity(), HomeDummyActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // Tutup AuthActivity agar tidak bisa di-back
            } else {
                Toast.makeText(requireContext(), "Email atau Password salah! (Coba: admin)", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Logic Pindah ke Register (dengan Animasi Fade)
        binding.tvRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                // Animasi halus agar background terlihat diam
                .setCustomAnimations(
                    android.R.anim.fade_in,  // Enter
                    android.R.anim.fade_out, // Exit
                    android.R.anim.fade_in,  // Pop Enter (saat back)
                    android.R.anim.fade_out  // Pop Exit
                )
                .replace(R.id.fragmentContainer, RegisterFragment())
                .addToBackStack(null) // Agar bisa ditekan tombol Back HP
                .commit()
        }

        // 3. Fitur Belum Tersedia
        val notAvailableAction = {
            Toast.makeText(requireContext(), "Fitur ini belum tersedia di versi Demo", Toast.LENGTH_SHORT).show()
        }

        binding.tvForgotPassword.setOnClickListener { notAvailableAction() }

        // Loop listener untuk icon sosmed di dalam layoutSocial
        // Pastikan view di dalam layoutSocial bisa diklik atau set click listener manual
        binding.ivGoogle.setOnClickListener { notAvailableAction() }
        binding.ivFb.setOnClickListener { notAvailableAction() }
        binding.ivApple.setOnClickListener { notAvailableAction() }
    }
}