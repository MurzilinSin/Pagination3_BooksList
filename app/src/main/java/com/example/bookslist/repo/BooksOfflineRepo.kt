package com.example.bookslist.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bookslist.data.BookModel
import com.example.bookslist.data.BooksApi
import com.example.bookslist.data.BooksApi.Companion.MAX_PAGE_SIZE
import com.example.bookslist.data.RemoteMediator
import com.example.bookslist.db.BooksDatabase
import kotlinx.coroutines.flow.Flow

class BooksOfflineRepo(
    private val db: BooksDatabase,
    private val booksApi : BooksApi
) {
    @ExperimentalPagingApi
    fun getBooks(): Flow<PagingData<BookModel>> {
        val pagingSourceFactory = { db.bookDao.pagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = MAX_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RemoteMediator(db, booksApi),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}