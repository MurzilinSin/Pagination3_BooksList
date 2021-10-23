package com.example.bookslist.di

import androidx.paging.ExperimentalPagingApi
import com.example.bookslist.repo.BooksOfflineRepo
import com.example.bookslist.data.BooksApi
import com.example.bookslist.db.BooksDatabase
import com.example.bookslist.ui.books.BooksVM
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalPagingApi
val viewModelModule: Module = module {
    viewModel {
        BooksVM(
        BooksOfflineRepo(BooksDatabase.getInstance(),provideRetrofit())
        )
    }
}

val retrofitModule : Module = module {
    single { provideHttpClient() }
    single { provideGson() }
    single { provideRetrofit() }
}

fun provideHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    return httpClient.build()
}

fun provideGson(): GsonConverterFactory {
    return GsonConverterFactory.create(GsonBuilder().setLenient().create())
}

fun provideRetrofit(): BooksApi {
    val podRetrofit = Retrofit.Builder()
        .baseUrl("https://demo.api-platform.com/")
        .addConverterFactory(provideGson())
        .client(provideHttpClient())
        .build()
    return podRetrofit.create(BooksApi::class.java)
}