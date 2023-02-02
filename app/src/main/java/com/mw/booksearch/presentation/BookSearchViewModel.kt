package com.mw.booksearch.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mw.booksearch.domain.model.GetBooks
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
 * View Model for [HomeScreen].
 */
@HiltViewModel
class BookSearchViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
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
     * Holds [searchTextUiState] for the text field from [HomeScreen].
     */
    var searchTextUiState by mutableStateOf(SearchTextUiState())
        private set

    /**
     * Updates [searchTextUiState] with values provided by the text field from [HomeScreen].
     */
    fun updateSearchTextUiState(searchText: String) {
        searchTextUiState = SearchTextUiState(searchText = searchText)
    }
}

/**
 * Ui state for the text field from [HomeScreen].
 */
data class SearchTextUiState(
    val searchText: String = ""
)