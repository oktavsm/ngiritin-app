package com.ngiritin.app.ui.new_transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.ngiritin.app.R
import java.util.Calendar

class ManualTransactionFragment : Fragment() {

    private var isIncomeTab = true // Default Income

    // Views
    private lateinit var btnTabIncome: TextView
    private lateinit var btnTabExpense: TextView
    private lateinit var tvDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manual_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init Views
        val btnBack: LinearLayout = view.findViewById(R.id.header)
        btnTabIncome = view.findViewById(R.id.btnTabIncome)
        btnTabExpense = view.findViewById(R.id.btnTabExpense)

        tvDate = view.findViewById(R.id.tvDate)
        val inputCategory: LinearLayout = view.findViewById(R.id.inputCategory)
        val inputDate: LinearLayout = view.findViewById(R.id.inputDate)
        val inputSource: LinearLayout = view.findViewById(R.id.inputSource)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSave)

        // 1. Back Button
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 2. Tab Switcher Logic
        btnTabIncome.setOnClickListener {
            if (!isIncomeTab) updateTabState(true)
        }
        btnTabExpense.setOnClickListener {
            if (isIncomeTab) updateTabState(false)
        }

        // 3. Date Picker
        inputDate.setOnClickListener {
            showDatePicker()
        }

        // 4. Placeholder Click Listeners (Category & Source)
        inputCategory.setOnClickListener {
            Toast.makeText(context, "Open Category Picker", Toast.LENGTH_SHORT).show()
        }
        inputSource.setOnClickListener {
            Toast.makeText(context, "Open Source/Account Picker", Toast.LENGTH_SHORT).show()
        }

        // 5. Save Button
        btnSave.setOnClickListener {
            val type = if (isIncomeTab) "Income" else "Expense"
            Toast.makeText(context, "Saving $type Transaction...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTabState(isIncome: Boolean) {
        isIncomeTab = isIncome
        val activeColor = ContextCompat.getColor(requireContext(), R.color.white)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.blue_primary) // #006FFD

        if (isIncome) {
            // Income Active
            btnTabIncome.setBackgroundResource(R.drawable.bg_tab_active)
            btnTabIncome.setTextColor(activeColor)

            // Expense Inactive
            btnTabExpense.setBackgroundResource(0) // Transparent
            btnTabExpense.setTextColor(inactiveColor)
        } else {
            // Expense Active
            btnTabExpense.setBackgroundResource(R.drawable.bg_tab_active)
            btnTabExpense.setTextColor(activeColor)

            // Income Inactive
            btnTabIncome.setBackgroundResource(0)
            btnTabIncome.setTextColor(inactiveColor)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format: DD/MM/YYYY
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvDate.text = formattedDate
                tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_text))
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}