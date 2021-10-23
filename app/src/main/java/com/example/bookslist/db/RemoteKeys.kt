package com.example.bookslist.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class RemoteKeys (
    @PrimaryKey val bookId: String,
    val prevKey: Int?,
    val nextKey: Int?
        )