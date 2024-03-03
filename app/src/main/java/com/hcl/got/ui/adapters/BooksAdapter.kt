package com.hcl.got.ui.adapters

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hcl.got.databinding.ListItemBookBinding
import com.hcl.got.data.model.BooksData

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.MyViewHolder>() {

    private var listOfBooks: List<BooksData> = emptyList()
    var onItemClick: ((BooksData) -> Unit)? = null
    var selectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemBookBinding.inflate(
                android.view.LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfBooks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindUI(position)
    }

    fun setListItems(listOfBooks: List<BooksData>) {
        this.listOfBooks = listOfBooks
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding: ListItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bookNameTextView: TextView = binding.bookNameTextView

        init {
            bookNameTextView.setOnClickListener {
                onItemClick?.invoke(listOfBooks[adapterPosition])
                val preSelectedPos = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                notifyItemChanged(preSelectedPos)

            }
        }

        fun bindUI(position: Int) {
            binding.apply {
                binding.bookName = listOfBooks[position].name
                bookNameTextView.setBackgroundColor(if (selectedPosition == position) Color.BLACK else Color.WHITE)
                bookNameTextView.setTextColor(if (selectedPosition == position) Color.WHITE else Color.BLACK)

            }
        }
    }

}