package com.mw.booksearch.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mw.booksearch.R
import com.mw.booksearch.presentation.screens.SearchTextUiState
import com.mw.booksearch.presentation.util.BookSearchScreenNames

/**
 * TopAppBar composable that displays on all screens.
 * A navigation arrow for backstack navigation will appear if not on "HomeScreen".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchTopBar(
    currentScreenTitle: Int,
    canNavigateBack: Boolean, navigateUp: () -> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(text = context.getString(currentScreenTitle)) },
        navigationIcon = {
            if (canNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_arrow),
                    modifier = Modifier
                        .clickable { navigateUp() }
                        .padding(12.dp)
                )
            }
        }
    )
}

/**
 * BottomAppBar composable that displays on all screens.
 * Contains a TextField that allows users to input a book search, and a Button to initiate the search.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchBottomBar(
    searchTextUiState: SearchTextUiState,
    onValueChange: (String) -> Unit,
    getBooks: () -> Unit,
    currentScreenTitle: Int,
    navigateUp: () -> Unit
) {
    BottomAppBar {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val focusManger = LocalFocusManager.current
            OutlinedTextField(
                value = searchTextUiState.searchText,
                onValueChange = { onValueChange(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManger.clearFocus() }),
                singleLine = true,
                modifier = Modifier.width(200.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                getBooks(); focusManger.clearFocus()
                if (currentScreenTitle == BookSearchScreenNames.BookInformationScreen.title) {
                    navigateUp()
                }
            }) {
                Text(text = stringResource(R.string.search))
            }
        }
    }
}