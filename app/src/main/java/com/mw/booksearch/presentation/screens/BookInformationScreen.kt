package com.mw.booksearch.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.mw.booksearch.R

/**
 * Screen for book information.
 */
@Composable
fun BookInformationScreen(viewModel: BookSearchViewModel) {
    BookDataBody(
        volumeInfoUiState = viewModel.volumeInfoUiState,
        modifier = Modifier.padding(12.dp)
    )
}

/**
 * Body for [BookInformationScreen].
 */
@Composable
private fun BookDataBody(volumeInfoUiState: VolumeInfoUiState, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Card() {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                BookTitle(volumeInfoUiState = volumeInfoUiState)
                BookDescription(volumeInfoUiState = volumeInfoUiState)
                BookInformation(volumeInfoUiState = volumeInfoUiState)
            }
        }
    }
}

/**
 * Composable that contains the clicked book's title and author(s).
 */
@Composable
private fun BookTitle(volumeInfoUiState: VolumeInfoUiState, modifier: Modifier = Modifier) {
    Column {
        (if (volumeInfoUiState.volumeInfo.title != null) {
            volumeInfoUiState.volumeInfo.title
        } else {
            stringResource(R.string.no_title_found)
        })?.let {
            Text(
                text = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                color = if (volumeInfoUiState.volumeInfo.title == null) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = modifier.fillMaxWidth()
            )
        }
        (if (volumeInfoUiState.volumeInfo.authors != null) {
            volumeInfoUiState.volumeInfo.authors
        } else {
            stringResource(R.string.no_authors_found)
        })?.let {
            Text(
                text = it.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (volumeInfoUiState.volumeInfo.authors == null) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Composable that displays the clicked book's cover, as well as description.
 * Clicking on the book cover will open the device's default browser and navigate to the book's Google Books page.
 */
@Composable
private fun BookDescription(volumeInfoUiState: VolumeInfoUiState, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val openUrl = Intent(Intent.ACTION_VIEW)
    openUrl.data = Uri.parse(volumeInfoUiState.volumeInfo.canonicalVolumeLink)

    Row(modifier = modifier.height(160.dp)) {
        AsyncImage(
            model = volumeInfoUiState.volumeInfo.imageLinks?.thumbnail,
            contentDescription = volumeInfoUiState.volumeInfo.title,
            contentScale = ContentScale.FillBounds,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            fallback = painterResource(id = R.drawable.ic_broken_image),
            modifier = modifier
                .weight(.30F)
                .width(12.dp)
                .height(160.dp)
                .clickable { ContextCompat.startActivity(context, openUrl, null) }
        )
        Spacer(modifier = modifier.padding(4.dp))
        Box(
            modifier = modifier
                .weight(.7F)
                .border(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            if (volumeInfoUiState.volumeInfo.description != null) {
                Text(
                    text = volumeInfoUiState.volumeInfo.description!!,
                    modifier = modifier
                        .verticalScroll(scrollState)
                        .padding(3.dp)
                )
            } else {
                Text(
                    text = "NO DESCRIPTION FOUND!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = modifier.padding(3.dp)
                )
            }
        }
    }
}

/**
 * Composable that displays a variety of information about the clicked book with [BookInformationEntry].
 * Also indicates to the user that the book cover is clickable.
 */
@Composable
private fun BookInformation(volumeInfoUiState: VolumeInfoUiState, modifier: Modifier = Modifier) {
    Row() {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            BookInformationEntry(
                type = "Average rating",
                isNull = volumeInfoUiState.volumeInfo.averageRating == null,
                nullText = "NO AVERAGE RATING FOUND!",
                nonNullText = "${volumeInfoUiState.volumeInfo.averageRating.toString()} / 5"
            )
            BookInformationEntry(
                type = "Number of ratings",
                isNull = volumeInfoUiState.volumeInfo.ratingsCount == null,
                nullText = "NO RATINGS FOUND!",
                nonNullText = volumeInfoUiState.volumeInfo.ratingsCount.toString()
            )
            BookInformationEntry(
                type = "Maturity rating",
                isNull = volumeInfoUiState.volumeInfo.maturityRating == null,
                nullText = "NO MATURITY RATING FOUND!",
                nonNullText = if (volumeInfoUiState.volumeInfo.maturityRating.toString() == "NOT_MATURE") {
                    "Not Mature"
                } else {
                    "Mature"
                }
            )
            BookInformationEntry(
                type = "Published date",
                isNull = volumeInfoUiState.volumeInfo.publishedDate == null,
                nullText = "NO PUBLISHED DATE FOUND!",
                nonNullText = volumeInfoUiState.volumeInfo.publishedDate.toString()
            )
            BookInformationEntry(
                type = "Publisher",
                isNull = volumeInfoUiState.volumeInfo.publisher == null,
                nullText = "NO PUBLISHER FOUND!",
                nonNullText = volumeInfoUiState.volumeInfo.publisher.toString()
            )
            BookInformationEntry(
                type = "Number of pages",
                isNull = volumeInfoUiState.volumeInfo.pageCount == null,
                nullText = "NO PUBLISHER FOUND!",
                nonNullText = volumeInfoUiState.volumeInfo.pageCount.toString()
            )
            Text(
                text = "Tap the book's cover for more information!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Reusable composable used in [BookInformation] to make book information entries much more efficient.
 */
@Composable
private fun BookInformationEntry(
    type: String,
    isNull: Boolean,
    nullText: String,
    nonNullText: String
) {
    Row {
        Text(
            text = "$type: "
        )
        Text(
            text = if (isNull) {
                nullText
            } else {
                nonNullText
            }, color = if (isNull) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            }
        )
    }
}


