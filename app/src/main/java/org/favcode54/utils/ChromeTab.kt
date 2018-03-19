package org.favcode54.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.widget.Toast
import org.favcode54.R
import java.lang.ref.WeakReference

/**
 * Created by Wilberforce on 1/27/18 at 11:03 PM.
 */

object ChromeTab {
    fun loadPage(activity: FragmentActivity, link: String) {
        val weakReference = WeakReference(activity)
        val context = weakReference.get()

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        builder.setSecondaryToolbarColor(ContextCompat.getColor(context, android.R.color.white))

        builder.setStartAnimations(context, R.anim.loadfrag_in, R.anim.loadfrag_out)
        builder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        val customTabsIntent = builder.build()

        customTabsIntent.intent.data = Uri.parse(link)

        val PACKAGE_NAME = "com.android.chrome"
        val packageManager = context.getPackageManager()
        val resolveInfoList = packageManager.queryIntentActivities(customTabsIntent.intent, PackageManager.MATCH_DEFAULT_ONLY)

        for (resolveInfo in resolveInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            if (TextUtils.equals(packageName, PACKAGE_NAME))
                customTabsIntent.intent.`package` = PACKAGE_NAME
        }
        customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like " + "Gecko) Chrome/61.0.3163.100 Safari/537.36"))
        try {
            customTabsIntent.launchUrl(context, Uri.parse(link))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Page cannot be viewed", Toast.LENGTH_SHORT).show()
        }
    }
}
