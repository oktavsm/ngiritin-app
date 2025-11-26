package com.ngiritin.app.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngiritin.app.R
import com.ngiritin.app.ui.history.onclick.EditTransactionBottomSheet
import com.ngiritin.app.ui.history.onclick.FilterBottomSheet

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

        // Di dalam observe ViewModel
        viewModel.transactionList.observe(viewLifecycleOwner) { items ->

            // Masukkan lambda function di parameter kedua
            val adapter = HistoryAdapter(items) { transaction ->
                // KODE YANG DIJALANKAN SAAT TOMBOL EDIT DI KLIK:
                val editSheet = EditTransactionBottomSheet()

                // (Opsional) Kirim data transaksi ke sheet biar form-nya keisi
                 val bundle = Bundle()
                 bundle.putString("TITLE", transaction.title)
                 editSheet.arguments = bundle

                editSheet.show(parentFragmentManager, "EditTransaction")
            }

            rvTransactions.adapter = adapter
        }

        // Di dalam onViewCreated HistoryFragment
        val btnFilter = view.findViewById<ImageView>(R.id.ivFilter)

        btnFilter.setOnClickListener {
            val filterBottomSheet = FilterBottomSheet()

            // Tampilkan! "parentFragmentManager" adalah kuncinya
            filterBottomSheet.show(parentFragmentManager, "FilterBottomSheet")
        }
    }
}