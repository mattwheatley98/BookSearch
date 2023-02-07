package com.mw.booksearch.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mw.booksearch.domain.model.GetBooks
import com.mw.booksearch.domain.model.ImageLinks
import com.mw.booksearch.domain.model.VolumeInfo
import com.mw.booksearch.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * UI state for [HomeScreen].
 */
sealed interface HomeScreenUiState {
    data class Success(val bookData: GetBooks) : HomeScreenUiState
    object Error : HomeScreenUiState
    object Loading : HomeScreenUiState
    object Start : HomeScreenUiState
}

/**
 * View Model for [HomeScreen], [BookInformationScreen], and "BookSearchBottomBar".
 */
@HiltViewModel
class BookSearchViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    /**
     * Holds [HomeScreenUiState].
     */
    var homeScreenUiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.Start)
        private set

    /**
     * Uses [bookRepository] to query the Google Books API with [searchTextUiState].
     */
    fun getBooks() {
        viewModelScope.launch {
            homeScreenUiState = HomeScreenUiState.Loading
            homeScreenUiState = try {
                HomeScreenUiState.Success(bookRepository.getBooks(searchTextUiState.searchText))
            } catch (e: IOException) {
                HomeScreenUiState.Error
            } catch (e: HttpException) {
                HomeScreenUiState.Error
            }
        }
    }

    /**
     * Holds [SearchTextUiState] for the text field from "BookSearchBottomBar".
     */
    var searchTextUiState by mutableStateOf(SearchTextUiState())
        private set

    /**
     * Updates [searchTextUiState] with values provided by the text field from "BookSearchBottomBar".
     */
    fun updateSearchTextUiState(searchText: String) {
        searchTextUiState = SearchTextUiState(searchText = searchText)
    }

    /**
     * Holds [VolumeInfoUiState] for the book users click on.
     */
    var volumeInfoUiState by mutableStateOf(VolumeInfoUiState())
        private set

    /**
     * Updates [volumeInfoUiState] with the values associated with the book users clicked on (response from HTTP request).
     */
    fun updateVolumeInfoUiState(volumeInfo: VolumeInfo){
        volumeInfoUiState = VolumeInfoUiState(volumeInfo = volumeInfo)
    }
}

/**
 * UI state for the text field from "BookSearchBottomBar".
 */
data class SearchTextUiState(
    val searchText: String = ""
)

/**
 * UI state for the book users click on.
 */
data class VolumeInfoUiState(
    var volumeInfo: VolumeInfo = VolumeInfo(
        authors = listOf(),
        averageRating = "",
        canonicalVolumeLink = "",
        description = "",
        imageLinks = ImageLinks(smallThumbnail = "", thumbnail = ""),
        maturityRating = "",
        pageCount = 0,
        publishedDate = "",
        publisher = "",
        ratingsCount = 0,
        title = "",
    )
)

