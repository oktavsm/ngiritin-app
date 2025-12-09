package com.ngiritin.app.ui.profile

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
import com.ngiritin.app.ui.profile.onclick.SettingsFragment

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPasswordExpandable(view)
        setupNavigation(view)
    }

    private fun setupPasswordExpandable(view: View) {
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

        btnCancel.setOnClickListener {
            etOldPass.text.clear()
            etNewPass.text.clear()
            etConfirmPass.text.clear()

            layoutExpanded.visibility = View.GONE
            layoutCollapsed.visibility = View.VISIBLE
        }

        btnSave.setOnClickListener {
            val oldPass = etOldPass.text.toString()
            val newPass = etNewPass.text.toString()
            val confirmPass = etConfirmPass.text.toString()

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(requireContext(), "Isi semua kolom dulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPass != confirmPass) {
                Toast.makeText(requireContext(), "Password baru gak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "Password berhasil diganti", Toast.LENGTH_SHORT).show()
            layoutExpanded.visibility = View.GONE
            layoutCollapsed.visibility = View.VISIBLE
        }
    }

    private fun setupLogoutButton(view: View) {
        val btnLogout = view.findViewById<LinearLayout>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logout diklik!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupNavigation(view: View) {
        val btnSettings = view.findViewById<LinearLayout>(R.id.btnSettings)
        val btnLogout = view.findViewById<LinearLayout>(R.id.btnLogout)

        btnSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}