package com.softwise.trumonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.softwise.trumonitor.utils.SPTrueTemp;


public class BluetoothConnectionReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            if (intExtra == 11) {
                MediaPlayer mediaPlayer2 = this.mediaPlayer;
                if (mediaPlayer2 != null) {
                    mediaPlayer2.release();
                    this.mediaPlayer.reset();
                }
            } else if (intExtra == 13) {
                raisedAlarm(context);
            }
        }
    }

    private void raisedAlarm(Context context) {
        try {
            if (SPTrueTemp.getLoginStatus(context)) {
                this.mediaPlayer = new MediaPlayer();
                AssetFileDescriptor openFd = context.getAssets().openFd("bluetooth_disconnect.mp3");
                MediaPlayer mediaPlayer2 = new MediaPlayer();
                this.mediaPlayer = mediaPlayer2;
                mediaPlayer2.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                openFd.close();
                this.mediaPlayer.prepare();
                this.mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
