package com.softwise.trumonitor.bluetoothListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.softwise.trumonitor.utils.TextUtil;


public class ScreenActionReceiver extends BroadcastReceiver {
    private String TAG = "ScreenActionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + TextUtil.newline_lf);
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + TextUtil.newline_lf);
        String sb2 = sb.toString();
        Log.d(this.TAG, sb2);
        Toast.makeText(context, sb2, Toast.LENGTH_LONG).show();
        String action = intent.getAction();
        if ("android.intent.action.SCREEN_ON".equals(action)) {
            Log.d(this.TAG, "screen is on...");
            Toast.makeText(context, "screen ON", Toast.LENGTH_LONG).show();
            context.startService(new Intent(context, FloatingWindow.class));
        } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
            Log.d(this.TAG, "screen is off...");
            Toast.makeText(context, "screen OFF", Toast.LENGTH_LONG).show();
        } else if ("android.intent.action.USER_PRESENT".equals(action)) {
            Log.d(this.TAG, "screen is unlock...");
            Toast.makeText(context, "screen UNLOCK", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(new Intent(context, FloatingWindow.class));
            } else {
                context.startService(new Intent(context, FloatingWindow.class));
            }
        } else if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Log.d(this.TAG, "boot completed...");
            Toast.makeText(context, "BOOTED..", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(new Intent(context, FloatingWindow.class));
            } else {
                context.startService(new Intent(context, FloatingWindow.class));
            }
        }
    }

    public IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        return intentFilter;
    }
}
