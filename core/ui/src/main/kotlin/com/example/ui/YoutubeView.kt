package com.example.ui

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YoutubeView(
    videoId: String
) {
    WebView(videoId)
}

@Composable
private fun WebView(videoId: String, modifier: Modifier = Modifier) {
    AndroidView(
        //need a set size or the view has default 0 size, and so become invisible
        modifier = modifier.size(320.dp, 180.dp),
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = object: WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                        return true
                    }
                }
            }
        }, update = {
            it.loadUrl("https://www.youtube.com/embed/${videoId}")
        }
    )
}