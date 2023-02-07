package com.mw.booksearch.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mw.booksearch.presentation.common.BookSearchBottomBar
import com.mw.booksearch.presentation.common.BookSearchTopBar
import com.mw.booksearch.presentation.screens.BookInformationScreen
import com.mw.booksearch.presentation.screens.BookSearchViewModel
import com.mw.booksearch.presentation.screens.HomeScreen
import com.mw.booksearch.presentation.util.BookSearchScreenNames

/**
 * Main app composable that uses a NavHost, [BookSearchTopBar], and [BookSearchBottomBar].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchApp() {
    val navController = rememberNavController()
    val viewModel: BookSearchViewModel = hiltViewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookSearchScreenNames.valueOf(
        value = backStackEntry?.destination?.route ?: BookSearchScreenNames.HomeScreen.name
    )
    Scaffold(topBar = {
        BookSearchTopBar(
            currentScreenTitle = currentScreen.title,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        )
    }, bottomBar = {
        BookSearchBottomBar(
            searchTextUiState = viewModel.searchTextUiState,
            onValueChange = { viewModel.updateSearchTextUiState(it) },
            getBooks = { viewModel.getBooks() },
            currentScreenTitle = currentScreen.title,
            navigateUp = { navController.navigateUp() })
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookSearchScreenNames.HomeScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BookSearchScreenNames.HomeScreen.name) {
                HomeScreen(
                    navigateToBookDataScreen = { navController.navigate(BookSearchScreenNames.BookInformationScreen.name) },
                    viewModel = viewModel
                )
            }
            composable(route = BookSearchScreenNames.BookInformationScreen.name) {
                BookInformationScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

