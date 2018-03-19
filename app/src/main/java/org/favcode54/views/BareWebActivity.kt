package org.favcode54.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.webkit.WebSettings
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.jadebyte.naijaloaded.network.VolleyCache
import com.jadebyte.naijaloaded.network.VolleySingleton
import kotlinx.android.synthetic.main.activity_bare_web.*
import kotlinx.android.synthetic.main.content_bare_web.*
import org.favcode54.BaseActivity
import org.favcode54.MyWebViewClient
import org.favcode54.R
import org.favcode54.network.MyVolleyError
import timber.log.Timber
import java.util.concurrent.TimeUnit

class BareWebActivity : BaseActivity() {

    companion object {
        const val URL = "PAGE_URL"
        const val TITLE = "PAGE_TITLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bare_web)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(TITLE)
        applyFont(rootView)
        tintProgressBar(progressBar)

        val url = intent.getStringExtra(URL)
        Timber.i("onCreate: %s", url)
        initWebView()
        loadPageHtml(url)
    }


    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun initWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.webViewClient = MyWebViewClient(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        } else {
            webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        }
    }

    private fun loadPageHtml(url: String) {
        progressBar.visibility = View.VISIBLE
        val request = object : StringRequest(url,
                Response.Listener { response ->
                    progressBar.visibility = View.GONE
                    webView.loadDataWithBaseURL(url, response, "text/html", "UTF-8", null)
                },
                Response.ErrorListener { error ->
                    Snackbar.make(rootView, MyVolleyError.errorMessage(error, this)!!, Snackbar.LENGTH_LONG).show()
                    finish()
                })
        {
            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                val resp = super.parseNetworkResponse(response)
                return Response.success(resp.result, VolleyCache.parseIgnoreCacheHeaders(response!!, TimeUnit.DAYS.toMillis(30))) // Cache
                // for 30 days
            }
        }

        request.setShouldCache(true)
        request.retryPolicy = DefaultRetryPolicy(7000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

}
