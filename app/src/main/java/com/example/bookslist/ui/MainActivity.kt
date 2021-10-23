package com.example.bookslist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.example.bookslist.R
import com.example.bookslist.ui.books.BooksFragment

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BooksFragment.newInstance())
                .commitNow()
        }
    }
}