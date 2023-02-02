package com.mw.booksearch.domain.repository

import com.mw.booksearch.domain.model.GetBooks

interface BookRepository {
    suspend fun getBooks(search: String): GetBooks
}