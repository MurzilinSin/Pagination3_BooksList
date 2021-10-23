package com.example.bookslist.app

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.bookslist.db.BooksDatabase
import com.example.bookslist.di.retrofitModule
import com.example.bookslist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()
        BooksDatabase.create(this)
        startKoin {
            androidContext(this@Application)
            modules(listOf(
                viewModelModule,
                retrofitModule
            ))
        }
    }
}