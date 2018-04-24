package org.favcode54.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by Intija on 4/23/2018.
 */

public class QuickNetUtils {
    private static final String MEDIA_TYPE = "application/x-www-form-urlencoded; charset=utf-8";

    public static boolean isConnected(Context context) {
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

    public static Call makePostRequest(String URL, RequestBody body) throws IOException {
        OkHttpClient client = new OkHttpClient();
       // Timber.i(rawBody.toString());

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        return client.newCall(request);

    }

    public static Call makeGetRequest(String URL) throws IOException {
        OkHttpClient client = new OkHttpClient();
        return client.newCall(new Request.Builder()
                                    .url(URL)
                                    .get()
                                    .build());
    }

}
