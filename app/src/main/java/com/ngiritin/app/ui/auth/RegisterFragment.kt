package com.ngiritin.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ngiritin.app.R
import com.ngiritin.app.databinding.FragmentRegisterBinding
import com.ngiritin.app.ui.navbar.BottomNavbarActivity
import com.ngiritin.app.utils.Result

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.tvBackToLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.tilName.editText?.text.toString().trim()
            val email = binding.tilEmail.editText?.text.toString().trim()
            val pass = binding.tilPassword.editText?.text.toString().trim()
            val confirmPass = binding.tilConfirmPassword.editText?.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(requireContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6) {
                Toast.makeText(requireContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(name, email, pass)
        }

        binding.btnGoogle.setOnClickListener {
            Toast.makeText(requireContext(), "Google Sign-Up coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.authResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.btnSignUp.isEnabled = false
                    binding.btnSignUp.text = "Loading..."
                }
                is Result.Success -> {
                    binding.btnSignUp.isEnabled = true
                    binding.btnSignUp.text = "Sign Up"
                    Toast.makeText(requireContext(), "Registration Successful! Welcome, ${result.data.displayName}", Toast.LENGTH_LONG).show()
                    navigateToHome()
                }
                is Result.Error -> {
                    binding.btnSignUp.isEnabled = true
                    binding.btnSignUp.text = "Sign Up"
                    Toast.makeText(requireContext(), "Registration Failed: ${result.error}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireActivity(), BottomNavbarActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}