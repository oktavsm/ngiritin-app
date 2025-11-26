package com.ngiritin.app.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngiritin.app.R
import com.ngiritin.app.model.HistoryItem
import com.ngiritin.app.model.Transaction

class HistoryAdapter(
    private val items: List<HistoryItem>,
    private val onEditClick: (Transaction) -> Unit // Tambahan Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HistoryItem.Header -> TYPE_HEADER
            is HistoryItem.Content -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_card, parent, false)
            ContentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HistoryItem.Header -> (holder as HeaderViewHolder).bind(item)
            is HistoryItem.Content -> (holder as ContentViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size

    // ViewHolder Classes
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvHeaderDate)
        fun bind(item: HistoryItem.Header) {
            tvDate.text = item.date
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Deklarasi view...
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val tvNote: TextView = itemView.findViewById(R.id.tvNote)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)

        fun bind(item: HistoryItem.Content) {
            val data = item.transaction
            tvTitle.text = data.title
            tvCategory.text = "Category : ${data.category}"
            tvAmount.text = "Amount : ${data.amount}"
            tvNote.text = data.note
            tvTime.text = data.time

            btnEdit.setOnClickListener {
                onEditClick(item.transaction) // Panggil callback, kirim data transaksinya
            }
        }
    }
}