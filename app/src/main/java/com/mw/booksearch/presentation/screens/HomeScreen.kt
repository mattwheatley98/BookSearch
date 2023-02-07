package com.mw.booksearch.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mw.booksearch.R
import com.mw.booksearch.domain.model.GetBooks
import com.mw.booksearch.domain.model.VolumeInfo

/**
 * Home screen that allows users to query books using the Google Books API.
 * [HomeScreenUiState] determines which screen to display, based on the status of the query.
 */
@Composable
fun HomeScreen(
    viewModel: BookSearchViewModel,
    navigateToBookDataScreen: () -> Unit,
) {
    when (viewModel.homeScreenUiState) {
        is HomeScreenUiState.Loading -> LoadingScreen()
        is HomeScreenUiState.Start -> StartScreen()
        is HomeScreenUiState.Success -> HomeBody(
            bookData = (viewModel.homeScreenUiState as HomeScreenUiState.Success).bookData,
            onBookClick = { viewModel.updateVolumeInfoUiState(it); navigateToBookDataScreen() },
        )
        else -> ErrorScreen(retryAction = { viewModel.getBooks() })
    }
}

/**
 * Body for [HomeScreen].
 */
@Composable
private fun HomeBody(
    bookData: GetBooks,
    onBookClick: (VolumeInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(6.dp)
    ) {
        BookList(bookData = bookData, onBookClick = onBookClick)
    }


}

/**
 * Displays a lazy vertical grid of queried book thumbnails if [bookData] is not null, or [InvalidSearch] if null.
 */
@Composable
private fun BookList(bookData: GetBooks, onBookClick: (VolumeInfo) -> Unit) {
    if (bookData.bookItems != null) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.Center,
            columns = GridCells.Adaptive(minSize = 150.dp),
        ) {
            items(items = bookData.bookItems) { bookItem ->
                BookListEntry(volumeInfo = bookItem.volumeInfo, onBookClick = onBookClick)
            }
        }
    } else {
        InvalidSearch()
    }
}

/**
 * Composable that displays in [BookList] if a search returns books.
 */
@Composable
private fun BookListEntry(
    volumeInfo: VolumeInfo,
    onBookClick: (VolumeInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = modifier
                .padding(6.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(volumeInfo.imageLinks?.thumbnail) //Must use a safe call in case the thumbnail is null
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                fallback = painterResource(id = R.drawable.ic_broken_image),
                modifier = modifier
                    .height(200.dp)
                    .width(150.dp)
                    .clickable { onBookClick(volumeInfo) }
            )
        }
    }
}

/**
 * Composable that displays in [BookList] if a search returns no books.
 */
@Composable
private fun InvalidSearch(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_books_found),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Home screen for displaying the start message.
 */
@Composable
private fun StartScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.search_for_a_book_to),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Home screen for displaying the loading image.
 */
@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * Home screen for displaying an error message with re-attempt button.
 */
@Composable
private fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.loading_failed),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}



