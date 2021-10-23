package com.example.bookslist.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.bookslist.data.*
import com.example.bookslist.repo.BooksOfflineRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class BooksVM(private val repo: BooksOfflineRepo): ViewModel() {
    private val dataSource = repo.getBooks()
    val books: Flow<PagingData<BookModel>> by lazy {
        dataSource.cachedIn(viewModelScope)
    }
}