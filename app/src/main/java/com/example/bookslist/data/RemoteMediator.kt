package com.example.bookslist.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.bookslist.db.BooksDatabase
import com.example.bookslist.db.RemoteKeys
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RemoteMediator(
    private val db: BooksDatabase,
    private val api: BooksApi
) : RemoteMediator<Int, BookModel>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BookModel>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_NUMBER
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }
        try {
            val apiResponse = api.getBooks(page)
            val books = apiResponse.books
            val endOfPaginationReached = books.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.keysDao.clearRemoteKeys()
                    db.bookDao.deleteAll()
                }
                val prevKey = if (page == INITIAL_PAGE_NUMBER) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = books.map {
                    RemoteKeys(bookId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.keysDao.insertAll(keys)
                db.bookDao.addAllBooks(books)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Success(endOfPaginationReached = false)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, BookModel>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { book ->
                db.keysDao.remoteKeysBookId(book.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, BookModel>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { book ->
                db.keysDao.remoteKeysBookId(book.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, BookModel>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { bookId ->
                db.keysDao.remoteKeysBookId(bookId)
            }
        }
    }

    private companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }
}