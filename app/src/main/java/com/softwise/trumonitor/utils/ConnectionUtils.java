package com.softwise.trumonitor.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtils {
    public static boolean getConnectivityStatusString(Context context) {
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetworkInfo == null || (activeNetworkInfo.getType() != 1 && activeNetworkInfo.getType() != 0)) {
            return false;
        }
        return true;
    }
}
