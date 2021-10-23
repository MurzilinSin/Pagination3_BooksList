package com.example.bookslist.ui.books.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.bookslist.data.BookModel
import com.example.bookslist.ui.books.BooksVH

class Adapter: PagingDataAdapter<BookModel, BooksVH>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksVH {
        return BooksVH.create(parent)
    }

    override fun onBindViewHolder(holder: BooksVH, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private object COMPARATOR : DiffUtil.ItemCallback<BookModel>() {

        override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return oldItem.title == newItem.title && oldItem.id == newItem.id
        }
    }
}