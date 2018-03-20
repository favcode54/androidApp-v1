package org.favcode54.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.favcode54.R;

import java.lang.ref.WeakReference;

/**
 * Created by Wilberforce on 1/25/18 at 11:10 PM.
 */
public final class MyVolleyError {

    public static String errorMessage(VolleyError error, Context activity) {
        WeakReference weakContext = new WeakReference<>(activity);
        Context context = (Context) weakContext.get();
        String errorString = null;
        if (context != null && error != null) {
            errorString = error instanceof TimeoutError ? context.getString(R.string.connect_time_o) :
                    (error instanceof NoConnectionError ? context.getString(R.string.no_connect_err) :
                            (error instanceof AuthFailureError ? context.getString(R.string.auth_error) :
                                    (error instanceof ServerError ? context.getString(R.string.server_error) :
                                            (error instanceof NetworkError ? context.getString(R.string.network_error) :
                                                    (error instanceof  ParseError ? context.getString
                                                            (R.string.parse_error) : context.getString(R.string.sth_wrong_try_again))))));
        }

        return errorString;
    }

}