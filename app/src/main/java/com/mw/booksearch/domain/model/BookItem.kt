package com.mw.booksearch.domain.model

/**
 * Element of "bookItems" that contains a link to the book on Google Books, as well as more queried information under [volumeInfo].
 * [selfLink] is not used yet, but will be in a future version of this app.
 */
data class BookItem(
    val selfLink: String,
    val volumeInfo: VolumeInfo
)