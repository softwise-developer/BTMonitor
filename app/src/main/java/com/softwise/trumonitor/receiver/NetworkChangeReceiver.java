package com.softwise.trumonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private NetworkChangeCallback callback;

    public interface NetworkChangeCallback {
        void onNetworkChanged(boolean z);
    }

    public NetworkChangeReceiver(NetworkChangeCallback networkChangeCallback) {
        this.callback = networkChangeCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            boolean isNetworkAvailable = isNetworkAvailable(context);
            NetworkChangeCallback networkChangeCallback = this.callback;
            if (networkChangeCallback != null) {
                networkChangeCallback.onNetworkChanged(isNetworkAvailable);
            }
        }
    }

    private boolean isNetworkAvailable(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                return false;
            }
            return true;
        } catch (NullPointerException e) {
            showLog(e.getLocalizedMessage());
            return false;
        }
    }

    private void showLog(String str) {
        Log.e("NetworkChangeReceiver", "" + str);
    }
}
