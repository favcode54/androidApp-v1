package org.favcode54

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.StrictMode
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatDelegate
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

/**
 * Created by Wilberforce on 3/14/18 at 10:30 PM.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        if (BuildConfig.DEBUG) {
            // Initializing Timber
            Timber.plant(Timber.DebugTree())
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        refWatcher = LeakCanary.install(this)
        appContext = applicationContext

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)

        val customFontStr = getString(R.string.customFont)
        val customFontBoldStr = getString(R.string.customFontBold)
        customFont = Typeface.createFromAsset(assets, customFontStr)
        customFontBold = Typeface.createFromAsset(assets, customFontBoldStr)

        enableStrictMode()
    }

    private fun enableStrictMode() {
        // Enabling strict mode. Strict mode enforces better development practices like closing of closable resources
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
        }
    }
    companion object {

        lateinit var sharedPreferences: SharedPreferences
            private set
        lateinit var appContext: Context
            private set
        lateinit var refWatcher: RefWatcher
            private set
        lateinit var customFont: Typeface
            private set
        lateinit var customFontBold: Typeface
            private set
    }
}