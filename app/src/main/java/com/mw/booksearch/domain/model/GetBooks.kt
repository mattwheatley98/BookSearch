package com.mw.booksearch.domain.model

import com.squareup.moshi.Json

/**
 * Parses the "items" objects from the query and stores them as [bookItems].
 */
data class GetBooks(
    @field:Json(name = "items")
    val bookItems: List<BookItem>?,
)
