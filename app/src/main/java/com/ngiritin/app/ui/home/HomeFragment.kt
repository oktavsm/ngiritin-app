package com.ngiritin.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ngiritin.app.R
import com.ngiritin.app.utils.CurrencyHelper

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val tvGreeting: TextView = view.findViewById(R.id.tvGreeting)
        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvTotalBalance: TextView = view.findViewById(R.id.tvTotalBalance)
        val tvIncome: TextView = view.findViewById(R.id.tvIncomeAmount)
        val tvExpense: TextView = view.findViewById(R.id.tvExpenseAmount)
        val btnToggleBalance: ImageView = view.findViewById(R.id.btnToggleBalance)

        val tvInsightSubtitle: TextView = view.findViewById(R.id.tvInsightSubtitle)
        val tvInsightAmount: TextView = view.findViewById(R.id.tvInsightAmount)

        val llTransactionList: LinearLayout = view.findViewById(R.id.llTransactionList)

        viewModel.headerData.observe(viewLifecycleOwner) { data ->
            tvGreeting.text = data.greeting
            tvUsername.text = "Welcome Back, ${data.username}!"

            tvTotalBalance.tag = data.balance

            tvIncome.text = "IDR ${CurrencyHelper.formatRupiah(data.income)}"
            tvExpense.text = "IDR ${CurrencyHelper.formatRupiah(data.expense)}"

            tvInsightSubtitle.text = "Average spending in ${data.insightCity} area"
            tvInsightAmount.text =
                "IDR ${CurrencyHelper.formatCompact(data.insightMin)} - IDR ${CurrencyHelper.formatCompact(data.insightMax)}"

            updateBalanceDisplay(tvTotalBalance, btnToggleBalance, viewModel.isBalanceVisible.value ?: false)
        }

        viewModel.isBalanceVisible.observe(viewLifecycleOwner) { isVisible ->
            updateBalanceDisplay(tvTotalBalance, btnToggleBalance, isVisible)
        }

        viewModel.transactionList.observe(viewLifecycleOwner) { list ->
            llTransactionList.removeAllViews()
            for (item in list) {
                addTransactionItem(llTransactionList, item)
            }
        }

        btnToggleBalance.setOnClickListener {
            viewModel.toggleBalanceVisibility()
        }
    }

    private fun updateBalanceDisplay(tvBalance: TextView, btnToggle: ImageView, isVisible: Boolean) {
        val currentBalance = tvBalance.tag as? Double ?: 0.0
        if (isVisible) {
            tvBalance.text = "IDR ${CurrencyHelper.formatRupiah(currentBalance)}"
            btnToggle.setImageResource(R.drawable.ic_visibility)
        } else {
            tvBalance.text = "IDR • • • • • • •"
            btnToggle.setImageResource(R.drawable.ic_visibility_off)
        }
    }

    private fun addTransactionItem(container: LinearLayout, item: HomeViewModel.DummyTransaction) {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_transaction_home, container, false)

        itemView.findViewById<TextView>(R.id.tvTransTitle).text = item.title
        itemView.findViewById<TextView>(R.id.tvTransCategory).text = "Category : ${item.category}"
        itemView.findViewById<TextView>(R.id.tvTransAmount).text =
            "Amount : IDR ${CurrencyHelper.formatRupiah(item.amount)}"
        itemView.findViewById<TextView>(R.id.tvTransNote).text = "Note : ${item.note}"
        itemView.findViewById<TextView>(R.id.tvTransTime).text = item.time

        container.addView(itemView)
    }
}
