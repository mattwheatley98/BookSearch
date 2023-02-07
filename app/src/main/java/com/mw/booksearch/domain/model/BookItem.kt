package com.mw.booksearch.domain.model

/**
 * Element of "bookItems" that contains a link to the book on Google Books, as well as more queried information under [volumeInfo].
 */
data class BookItem(
    val volumeInfo: VolumeInfo
)