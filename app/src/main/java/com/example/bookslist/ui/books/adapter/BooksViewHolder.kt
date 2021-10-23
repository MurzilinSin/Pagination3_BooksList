package com.example.bookslist.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookslist.data.BookModel
import com.example.bookslist.databinding.RecyclerItemBinding

class BooksVH(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
    lateinit var book: BookModel
    fun bind(book: BookModel)  {
        this.book = book
        binding.title.text = book.title
        binding.author.text = book.author
        binding.description.text = book.description
        binding.publicationDate.text = book.publicationDate
    }

    companion object {
        fun create(view: ViewGroup): BooksVH {
            val inflater = LayoutInflater.from(view.context)
            val binding = RecyclerItemBinding.inflate(inflater, view, false)
            return BooksVH(binding)
        }
    }
}