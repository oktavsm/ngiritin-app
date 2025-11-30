package com.ngiritin.app.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.ngiritin.app.R
import com.ngiritin.app.databinding.FragmentLoginBinding
import com.ngiritin.app.ui.navbar.BottomNavbarActivity
import com.ngiritin.app.utils.Result

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        setupGoogleSignIn()
        setupListeners()
        observeViewModel()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Ambil dari strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.loginWithGoogle(credential)
            } catch (e: ApiException) {
                android.util.Log.e("GoogleLogin", "Google sign in failed code=${e.statusCode}, msg=${e.message}")
                Toast.makeText(requireContext(), "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            android.util.Log.e("GoogleLogin", "Result Code not OK: ${result.resultCode}")
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString().trim()
            val password = binding.tilPassword.editText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(requireContext(), "Feature not available yet", Toast.LENGTH_SHORT).show()
        }

        // 3. Tombol Login Google
        binding.btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun observeViewModel() {
        viewModel.authResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    setLoading(true)
                }
                is Result.Success -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }
                is Result.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), "Login Failed: ${result.error}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.btnLogin.isEnabled = !isLoading
        binding.btnGoogle.isEnabled = !isLoading
        binding.btnLogin.text = if (isLoading) "Loading..." else getString(R.string.login)
    }

    private fun navigateToHome() {
        val intent = Intent(requireActivity(), BottomNavbarActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}