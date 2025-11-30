package com.ngiritin.app.ui.new_transaction

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.ngiritin.app.R
import com.ngiritin.app.ui.new_transaction.TransactionViewModel
import java.util.Calendar

class ManualTransactionFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()

    private var isIncomeTab = true
    private var selectedCategory: String = ""
    private var selectedAccount: String = ""
    private var selectedDate: String = ""

    private lateinit var btnTabIncome: TextView
    private lateinit var btnTabExpense: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvSource: TextView
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manual_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val header: LinearLayout = view.findViewById(R.id.header)
        btnTabIncome = view.findViewById(R.id.btnTabIncome)
        btnTabExpense = view.findViewById(R.id.btnTabExpense)

        etAmount = view.findViewById(R.id.etAmount)
        etNote = view.findViewById(R.id.etNote)

        tvCategory = view.findViewById(R.id.tvCategory)
        tvDate = view.findViewById(R.id.tvDate)
        tvSource = view.findViewById(R.id.tvSource)

        val inputCategory: LinearLayout = view.findViewById(R.id.inputCategory)
        val inputDate: LinearLayout = view.findViewById(R.id.inputDate)
        val inputSource: LinearLayout = view.findViewById(R.id.inputSource)
        val btnSave: MaterialButton = view.findViewById(R.id.btnSave)

        setDefaultDate()

        header.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnTabIncome.setOnClickListener {
            if (!isIncomeTab) updateTabState(true)
        }
        btnTabExpense.setOnClickListener {
            if (isIncomeTab) updateTabState(false)
        }

        inputDate.setOnClickListener { showDatePicker() }
        inputCategory.setOnClickListener { showCategoryDialog() }
        inputSource.setOnClickListener { showAccountDialog() }

        btnSave.setOnClickListener { saveTransaction() }
    }

    private fun updateTabState(isIncome: Boolean) {
        isIncomeTab = isIncome
        val activeColor = ContextCompat.getColor(requireContext(), R.color.white)
        val inactiveColor =
            ContextCompat.getColor(requireContext(), R.color.blue_primary)

        if (isIncome) {
            btnTabIncome.setBackgroundResource(R.drawable.bg_tab_active)
            btnTabIncome.setTextColor(activeColor)
            btnTabExpense.setBackgroundResource(0)
            btnTabExpense.setTextColor(inactiveColor)
        } else {
            btnTabExpense.setBackgroundResource(R.drawable.bg_tab_active)
            btnTabExpense.setTextColor(activeColor)
            btnTabIncome.setBackgroundResource(0)
            btnTabIncome.setTextColor(inactiveColor)
        }
    }

    private fun setDefaultDate() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        selectedDate = "$day/$month/$year"
        tvDate.text = selectedDate
        tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_text))
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvDate.text = selectedDate
                tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_text))
            },
            year, month, day
        ).show()
    }

    private fun showCategoryDialog() {
        val categories = if (isIncomeTab) {
            arrayOf("Salary", "Allowance", "Bonus", "Investment", "Others")
        } else {
            arrayOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Health", "Others")
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Choose Category")
            .setItems(categories) { _, which ->
                selectedCategory = categories[which]
                tvCategory.text = selectedCategory
                tvCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_text))
            }
            .show()
    }

    private fun showAccountDialog() {
        val accounts = arrayOf("Cash", "BCA", "Mandiri", "Gopay", "OVO", "Dana")

        AlertDialog.Builder(requireContext())
            .setTitle("Choose Account")
            .setItems(accounts) { _, which ->
                selectedAccount = accounts[which]
                tvSource.text = selectedAccount
                tvSource.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_text))
            }
            .show()
    }

    private fun saveTransaction() {
        val amountStr = etAmount.text.toString()
        val note = etNote.text.toString()
        val type = if (isIncomeTab) "Income" else "Expense"

        if (amountStr.isEmpty()) {
            Toast.makeText(context, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedCategory.isEmpty()) {
            Toast.makeText(context, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedAccount.isEmpty()) {
            Toast.makeText(context, "Please select an account", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(context, "$type transaction saved successfully!", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }
}
