package org.favcode54;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.favcode54.R.id;
import org.favcode54.network.MyVolleyError;
import org.favcode54.network.VolleyCache;
import org.favcode54.network.VolleySingleton;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Intrinsics;

public final class BareWebActivity extends BaseActivity {
    public static final String URL = "PAGE_URL";
    public static final String TITLE = "PAGE_TITLE";

    private ProgressBar progressBar;
    private WebView webView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_bare_web);
        this.setSupportActionBar(findViewById(id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE));

        applyFont(findViewById(id.rootView));
        progressBar = findViewById(id.progressBar);
        webView = findViewById(id.webView);
        tintProgressBar(progressBar);
        String url = getIntent().getStringExtra(URL);
        initWebView();
        loadPageHtml(url);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private final void initWebView() {
        WebSettings webSettings = webView.getSettings();
        Intrinsics.checkExpressionValueIsNotNull(webSettings, "webSettings");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient(this));
        if (VERSION.SDK_INT >= 19) {
            webSettings.setLayoutAlgorithm(LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        }

    }

    private void loadPageHtml(final String url) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(url, response -> {

            progressBar.setVisibility(View.GONE);
            webView.loadDataWithBaseURL(url, response, "text/html", "UTF-8", null);

        }, error -> {
            String error1 = MyVolleyError.errorMessage(error, BareWebActivity.this);
            Snackbar.make(findViewById(id.rootView), error1, Snackbar.LENGTH_LONG).show();
            BareWebActivity.this.finish();
        }) {
            @NotNull
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Response<String> resp = super.parseNetworkResponse(response);

                return Response.success(resp.result, VolleyCache.parseIgnoreCacheHeaders(response, TimeUnit.DAYS.toMillis(30L)));
            }
        };
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(7000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

}
