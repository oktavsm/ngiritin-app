package com.ngiritin.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ngiritin.app.R
import java.util.Calendar

class HomeFragment : Fragment() {

    private var isBalanceVisible = false
    private var userBalance = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvGreeting: TextView = view.findViewById(R.id.tvGreeting)
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvTotalBalance: TextView = view.findViewById(R.id.tvTotalBalance)
        val tvIncome: TextView = view.findViewById(R.id.tvIncomeAmount)
        val tvExpense: TextView = view.findViewById(R.id.tvExpenseAmount)
        val btnToggleBalance: ImageView = view.findViewById(R.id.btnToggleBalance)

        val dummyName = "Arlott"
        userBalance = 2540000.0
        val dummyIncome = 5000000.0
        val dummyExpense = 2550000.0

        tvGreeting.text = getGreetingMessage()
        tvUsername.text = "Welcome Back, $dummyName!"
        tvIncome.text = "IDR ${formatRupiah(dummyIncome)}"
        tvExpense.text = "IDR ${formatRupiah(dummyExpense)}"

        updateBalanceDisplay(tvTotalBalance, btnToggleBalance)

        btnToggleBalance.setOnClickListener {
            isBalanceVisible = !isBalanceVisible
            updateBalanceDisplay(tvTotalBalance, btnToggleBalance)
        }

        val tvInsightSubtitle: TextView = view.findViewById(R.id.tvInsightSubtitle)
        val tvInsightAmount: TextView = view.findViewById(R.id.tvInsightAmount)

        val city = "Malang"
        val avgMin = 2600000.0
        val avgMax = 3200000.0

        tvInsightSubtitle.text = "Average spending in $city area"
        tvInsightAmount.text = "IDR ${formatCompact(avgMin)} - IDR ${formatCompact(avgMax)}"

        val llTransactionList: LinearLayout = view.findViewById(R.id.llTransactionList)

        val dummyTransactions = listOf(
            DummyTransaction(
                title = "Coffee at Starbucks",
                category = "Food",
                amount = 120000.0,
                note = "Morning coffee before class",
                time = "13.00 AM"
            ),
            DummyTransaction(
                title = "Indomaret Snacks",
                category = "Food",
                amount = 25000.0,
                note = "Beli chiki dan air mineral",
                time = "10.30 AM"
            ),
            DummyTransaction(
                title = "Gojek to Campus",
                category = "Transport",
                amount = 15000.0,
                note = "Ojek ke kampus UB",
                time = "07.45 AM"
            )
        )

        llTransactionList.removeAllViews()

        for (item in dummyTransactions) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_transaction_home, llTransactionList, false)

            val tvTitle: TextView = itemView.findViewById(R.id.tvTransTitle)
            val tvCategory: TextView = itemView.findViewById(R.id.tvTransCategory)
            val tvAmount: TextView = itemView.findViewById(R.id.tvTransAmount)
            val tvNote: TextView = itemView.findViewById(R.id.tvTransNote)
            val tvTime: TextView = itemView.findViewById(R.id.tvTransTime)

            tvTitle.text = item.title
            tvCategory.text = "Category : ${item.category}"
            tvAmount.text = "Amount : IDR ${formatRupiah(item.amount)}"
            tvNote.text = "Note : ${item.note}"
            tvTime.text = item.time

            llTransactionList.addView(itemView)
        }
    }

    private fun updateBalanceDisplay(tvBalance: TextView, btnToggle: ImageView) {
        if (isBalanceVisible) {
            tvBalance.text = "IDR ${formatRupiah(userBalance)}"
            btnToggle.setImageResource(R.drawable.ic_visibility)
        } else {
            tvBalance.text = "IDR • • • • • • •"
            btnToggle.setImageResource(R.drawable.ic_visibility_off)
        }
    }

    private fun formatRupiah(number: Double): String {
        return String.format("%,.0f", number)
    }

    private fun formatCompact(number: Double): String {
        return if (number >= 1000000) {
            String.format("%.1fM", number / 1000000)
        } else {
            formatRupiah(number)
        }
    }

    private fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            else -> "Good Night"
        }
    }

    data class DummyTransaction(
        val title: String,
        val category: String,
        val amount: Double,
        val note: String,
        val time: String
    )
}