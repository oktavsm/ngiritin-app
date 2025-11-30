package com.ngiritin.app.ui.new_transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ngiritin.app.R

class AddOptionBottomSheet(
    private val onOptionSelected: (String) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.btnAutomatic).setOnClickListener {
            onOptionSelected("automatic")
            dismiss()
        }

        view.findViewById<LinearLayout>(R.id.btnManual).setOnClickListener {
            onOptionSelected("manual")
            dismiss()
        }
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog
}