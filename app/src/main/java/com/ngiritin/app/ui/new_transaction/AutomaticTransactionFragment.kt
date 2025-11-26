package com.ngiritin.app.ui.new_transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.ngiritin.app.R
// import com.ngiritin.app.ui.home.HomeFragment // Jika ingin navigasi balik ke home

class AutomaticTransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_automatic_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack: LinearLayout = view.findViewById(R.id.header) // Klik area "Back"
        val btnMic: View = view.findViewById(R.id.btnMic)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSaveTransaction)
        val etInput: EditText = view.findViewById(R.id.etAiInput)

        // 1. Tombol Back
        btnBack.setOnClickListener {
            // Kembali ke fragment sebelumnya atau ke Home
            parentFragmentManager.popBackStack()
        }

        // 2. Tombol Mic (Placeholder untuk fitur Voice)
        btnMic.setOnClickListener {
            Toast.makeText(context, "Listening... (Voice Feature Coming Soon)", Toast.LENGTH_SHORT).show()
            // Nanti di sini panggil Logic Speech-to-Text
        }

        // 3. Tombol Save (Placeholder untuk kirim ke AI)
        btnSave.setOnClickListener {
            val text = etInput.text.toString()
            if (text.isNotEmpty()) {
                Toast.makeText(context, "Sending to AI: $text", Toast.LENGTH_SHORT).show()
                // Nanti di sini panggil ViewModel.analyzeText(text)
            } else {
                Toast.makeText(context, "Please write something first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}