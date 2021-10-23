package com.example.bookslist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookslist.data.BookModel

@Database(entities = [BookModel::class,RemoteKeys::class],version = 1)
@TypeConverters(TypeConverter::class)
abstract class BooksDatabase: RoomDatabase() {
    abstract val bookDao: BookDao
    abstract val keysDao: RemoteKeysDao

    companion object {
        private const val DB_NAME = "book.db"
        private var instance: BooksDatabase? = null

        fun getInstance() = instance ?: throw IllegalStateException("А база то ненастоящая")

        fun create(context: Context) {
            if(instance == null) {
                instance = Room.databaseBuilder(context, BooksDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}