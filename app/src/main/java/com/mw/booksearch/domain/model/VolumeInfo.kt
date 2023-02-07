package com.mw.booksearch.domain.model

/**
 * Contains the "volumeInfo" object for a queried book.
 */
data class VolumeInfo(
    val authors: List<String>?,
    val averageRating: String?,
    val canonicalVolumeLink: String?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val maturityRating: String?,
    val pageCount: Int?,
    val publishedDate: String?,
    val publisher: String?,
    val ratingsCount: Int?,
    val title: String?
)
