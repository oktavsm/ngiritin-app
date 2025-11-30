package com.ngiritin.app.ui.new_transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.ngiritin.app.R
import com.ngiritin.app.ui.new_transaction.TransactionViewModel

class AutomaticTransactionFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_automatic_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val header: LinearLayout = view.findViewById(R.id.header)
        val btnMic: View = view.findViewById(R.id.btnMic)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSaveTransaction)
        val etInput: EditText = view.findViewById(R.id.etAiInput)

        header.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnMic.setOnClickListener {
            Toast.makeText(context, "Listening... (Voice Feature Coming Soon)", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            val text = etInput.text.toString().trim()

            if (text.isNotEmpty()) {
                viewModel.analyzeText(text)
                Toast.makeText(context, "Sending to AI...", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "Please write something first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
