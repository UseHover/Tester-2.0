package com.hover.starter.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.hover.starter.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkOps {
    private final static String TAG = "NetworkOps";
    private Context mContext;
    private OkHttpClient okHttpClient;

    public NetworkOps(Context c) {
        mContext = c;
        okHttpClient = new OkHttpClient();
    }

    public String makeRequest(String endpoint) throws IOException {
        if (!isConnected(mContext)) throw new IOException(mContext.getString(R.string.error_network));

        String url = mContext.getString(R.string.url_builder, mContext.getString(R.string.base_url), endpoint);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept","application/json")
                .addHeader("Authorization", "Token token=" + getApiKey(mContext))
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw
                new IOException("Error " + response.code() + " " + response.body().string());

        return response.body().string();
    }

    private static boolean isConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }



    private String getApiKey(Context c) {
        try {
            ApplicationInfo ai = c.getPackageManager().getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA);
            Log.v(TAG, "API Key found: " + ai.metaData.getString("com.hover.ApiKey"));
            return ai.metaData.getString("com.hover.ApiKey");
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return null;
    }


}

