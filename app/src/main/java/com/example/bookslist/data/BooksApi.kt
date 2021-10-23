package com.example.bookslist.data

import androidx.annotation.IntRange
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("/books")
    suspend fun getBooks(@Query("page")@IntRange(from = 1)page:Int): ServerResponse

    companion object {
        const val MAX_PAGE_SIZE = 20
    }
}

