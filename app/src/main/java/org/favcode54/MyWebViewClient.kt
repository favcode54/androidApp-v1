package org.favcode54

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import org.favcode54.utils.ChromeTab

/**
 * Created by Wilberforce on 1/27/18 at 10:12 PM.
 */

class MyWebViewClient(private val context: FragmentActivity) : WebViewClient() {

    @Suppress("OverridingDeprecatedMember")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return handleUrl(Uri.parse(url))
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return handleUrl(request.url)
    }

    private fun handleUrl(uri: Uri): Boolean {
        ChromeTab.loadPage(context, uri.toString())
        // Returning true the webView should not load the url
        // e.g. open web page in a Browser or handle it locally
        return true
    }
}
