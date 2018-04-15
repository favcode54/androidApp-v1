package org.favcode54.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Intija on 4/15/2018.
 */

public class PersistentStorageUtils {

    private Context context;

    public PersistentStorageUtils(Context context){ this.context = context; }

    //Used to store simple values with keys. e.g user ID
    public void quick_store(String key, String value){
        if(key == null || value == null){   return; }
        SharedPreferences sp = context.getSharedPreferences("general", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    //Used to retrieve simple values stored with the quick_store function above
    public String quick_retrieve(String key){
        if(key == null){    return "";  }
        return context.getSharedPreferences("general", Context.MODE_PRIVATE).getString(key, "");
    }

    public static String getAppVersion(Context context){
        try {

            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName.trim();

        }catch (Exception e){
            return "";
        }
    }

    public boolean isLoggedIn() {
        return !quick_retrieve("user_id").isEmpty();
    }
    public boolean isConnected() {
        ConnectivityManager cd = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cd != null) {
            NetworkInfo[] info = cd.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

        }
        return false;
    }
}
