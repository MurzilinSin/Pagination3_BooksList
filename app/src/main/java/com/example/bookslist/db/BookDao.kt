package com.example.bookslist.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookslist.data.BookModel

@Dao
interface BookDao {
    @Query("SELECT * FROM bookmodel")
    fun getBooks(): List<BookModel>
    @Query("SELECT * FROM bookmodel")
    fun books(): PagingSource<Int,BookModel>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllBooks(books: List<BookModel>)

    @Query("SELECT * FROM bookmodel ")
    fun pagingSource(): PagingSource<Int, BookModel>

    @Query("DELETE FROM bookmodel")
    suspend fun deleteAll()
}