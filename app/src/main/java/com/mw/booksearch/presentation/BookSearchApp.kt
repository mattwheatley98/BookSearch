package com.mw.booksearch.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mw.booksearch.R

/**
 * Main app composable that displays [HomeScreen], as well as bottom and top bars.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchApp(viewModel: BookSearchViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(R.string.book_search)) }) },
        bottomBar = {
            BottomAppBar() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val focusManger = LocalFocusManager.current
                    OutlinedTextField(
                        value = viewModel.searchTextUiState.searchText,
                        onValueChange = { viewModel.updateSearchTextUiState(it) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManger.clearFocus() }),
                        singleLine = true,
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { viewModel.getBooks(); focusManger.clearFocus() }) {
                        Text(text = stringResource(R.string.search))
                    }
                }
            }
        }) { innerPadding ->
        HomeScreen(modifier = Modifier.padding(innerPadding))
    }
}

