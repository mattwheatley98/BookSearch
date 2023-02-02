package com.mw.booksearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mw.booksearch.presentation.BookSearchApp
import com.mw.booksearch.theme.BookSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookSearchTheme {
                BookSearchApp()
            }
        }
    }
}
