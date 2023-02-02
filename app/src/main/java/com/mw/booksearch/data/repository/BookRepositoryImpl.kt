package com.mw.booksearch.data.repository

import com.mw.booksearch.data.remote.BookApi
import com.mw.booksearch.domain.model.GetBooks
import com.mw.booksearch.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookApi: BookApi) : BookRepository {
    override suspend fun getBooks(search: String): GetBooks = bookApi.getBooks(search = search)
}