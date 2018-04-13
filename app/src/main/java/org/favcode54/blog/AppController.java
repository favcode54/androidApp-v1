package org.favcode54.blog;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import org.favcode54.BuildConfig;
import org.favcode54.R;
import org.favcode54.util.LruBitmapCache;
import timber.log.Timber;
import timber.log.Timber.DebugTree;


public final class AppController extends Application {
    private static SharedPreferences sharedPreferences;
    private static RefWatcher refWatcher;
    public static Typeface customFont;
    private static Typeface customFontBold;
    private static Context appContext;
    /**
     * Log or request TAG
     */
    public static final String TAG = AppController.class.getSimpleName();

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        mInstance = this;
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            if (BuildConfig.DEBUG) {
                Timber.plant(new DebugTree());
            }

            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            refWatcher = LeakCanary.install(this);
            appContext = getApplicationContext();

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);

            String customFontStr = getString(R.string.customFont);
            String customFontBoldStr = getString(R.string.customFontBold);

            customFont = Typeface.createFromAsset(this.getAssets(), customFontStr);
            customFontBold = Typeface.createFromAsset(this.getAssets(), customFontBoldStr);
            enableStrictMode();
        }
    }

    /**
     * @return AppController singleton instance
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
    	// lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        //VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        //VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy((new Builder()).detectAll().penaltyLog().build());
            StrictMode.setVmPolicy((new StrictMode.VmPolicy.Builder()).detectAll().penaltyLog().build());
        }

    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    public static Typeface getCustomFont() {
        return customFont;
    }

    public static Typeface getCustomFontBold() {
        return customFontBold;
    }

    public static Context getAppContext() {
        return appContext;
    }

}
