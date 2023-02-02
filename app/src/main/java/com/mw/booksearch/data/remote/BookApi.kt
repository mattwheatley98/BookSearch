package com.mw.booksearch.data.remote

import com.mw.booksearch.domain.model.GetBooks
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("v1/volumes")
    suspend fun getBooks(
        @Query("q") search: String,
        @Query("maxResults") queryResults: String = "40" //Maximum allowed results from the API.
    ) : GetBooks
}