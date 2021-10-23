package com.example.bookslist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM keys WHERE bookId = :bookId")
    suspend fun remoteKeysBookId(bookId: String): RemoteKeys?

    @Query("DELETE FROM keys")
    suspend fun clearRemoteKeys()
}