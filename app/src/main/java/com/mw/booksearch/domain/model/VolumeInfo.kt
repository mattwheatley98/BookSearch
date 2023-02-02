package com.mw.booksearch.domain.model

/**
 * Contains the "volumeInfo" object for a queried book.
 * Note that many of these variables are not used, but will be in later versions.
 */
data class VolumeInfo(
    val authors: List<String>,
    val averageRating: String,
    val canonicalVolumeLink: String,
    val categories: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val maturityRating: String,
    val pageCount: Int,
    val printType: String,
    val publishedDate: String,
    val publisher: String,
    val ratingsCount: Int,
    val subtitle: String,
    val title: String
)
