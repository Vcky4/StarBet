package com.starbet.ui.screens.webView

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun MyWebView(
    modifier: Modifier = Modifier,
    url: String,
) {
    val webViewState = rememberWebViewState(url = url)
    WebView(
        modifier = modifier,
        state = webViewState,
        captureBackPresses = true,
        onCreated = { it : WebView ->
            it.settings.javaScriptEnabled = true
        }
    )
}