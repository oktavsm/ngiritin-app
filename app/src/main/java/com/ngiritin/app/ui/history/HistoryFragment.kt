package com.ngiritin.app.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngiritin.app.R
class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var rvTransactions: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        rvTransactions = view.findViewById(R.id.rvTransactions)
        rvTransactions.layoutManager = LinearLayoutManager(context)

        viewModel.transactionList.observe(viewLifecycleOwner) { items ->
            val adapter = HistoryAdapter(items)
            rvTransactions.adapter = adapter
        }
    }
}