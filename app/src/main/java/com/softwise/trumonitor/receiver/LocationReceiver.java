package com.softwise.trumonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.location.LocationResult;

public class LocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (LocationResult.hasResult(intent)) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("googleLocation").putExtra("result", LocationResult.extractResult(intent)));
        }
    }
}
