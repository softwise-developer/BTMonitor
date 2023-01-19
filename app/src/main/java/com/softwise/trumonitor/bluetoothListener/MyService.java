package com.softwise.trumonitor.bluetoothListener;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.activity.SensorTemperatureActivity;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.implementer.SensorPresenter;
import com.softwise.trumonitor.listeners.IBooleanListener;


public class MyService extends Service {
    int counter = 0;
    private Handler h;
    private Runnable r;


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(" OK Bind call ",String.valueOf(intent.getAction()));
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        Context applicationContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("blue_true_monitor", "BlueTruMonitorChannel",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Bluetooth TruMonitor channel description");
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, "blue_true_monitor");
        }else {
            builder = new NotificationCompat.Builder(this);
        }
        PendingIntent activity = PendingIntent.getActivity(applicationContext, 0, new Intent(applicationContext,
                SensorTemperatureActivity.class), PendingIntent.FLAG_IMMUTABLE| PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(activity).setContentTitle("BluetoothTruMonitor").setTicker("Temperature recording is on")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24).setContentIntent(activity).setOngoing(true).build();
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    /* access modifiers changed from: private */
    public Notification updateNotification() {
        NotificationCompat.Builder builder;
        this.counter++;
        Context applicationContext = getApplicationContext();
        PendingIntent activity = PendingIntent.getActivity(applicationContext, 0, new Intent(applicationContext,
                SensorTemperatureActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("blue_true_monitor", "BlueTruMonitorChannel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Bluetooth TruMonitor channel description");
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, "blue_true_monitor");
        } else {
            builder = new NotificationCompat.Builder(applicationContext);
        }
        return builder.setContentIntent(activity).setContentTitle("BluetoothTruMonitor").setTicker("Temperature recording is on")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24).setContentIntent(activity).setOngoing(true).build();
    }

    @Override
    public int onStartCommand(final Intent intent, int i, int i2) {
        try {
            if (intent != null) {
                Log.i("OK Onstart call ", String.valueOf(intent.getAction()));
                if (intent.getAction() != null) {
                    if (intent.getAction().contains("start")) {
                        this.h = new Handler();
                        Runnable r2 = new Runnable() {
                            @Override
                            public void run() {
                                startTempUpload(intent);
                          /*  MyService myService = MyService.this;
                            myService.startForeground(101, myService.updateNotification());
                            intent.getIntExtra("frequency", 0);
                            MyService.this.startTempUpload(intent);*/
                            }
                        };
                        this.r = r2;
                        this.h.post(r2);
                    } else {
                        removeAllNotification();
                        this.h.removeCallbacks(this.r);
                        stopForeground(true);
                        stopSelf();
                    }

                } else {
                    removeAllNotification();
                    stopForeground(true);
                    stopSelf();
                }
            } else {
                removeAllNotification();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    private void removeAllNotification()
    {
        NotificationManager notifManager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
    }

    public void startTempUpload(Intent intent) {
        int parseInt = Integer.parseInt(intent.getStringExtra("sensorId"));
        Log.e("startTempUpload", Integer.toString(parseInt));
        Log.e("Inside upload",String.valueOf(intent.getBooleanExtra("isFromMemory",false)));
        new SensorPresenter(getApplicationContext()).uploadData(String.valueOf(parseInt),
                MethodHelper.getJsonArray(this, (EntitySensor) intent.getExtras().get("entitySensor"),
                        intent.getBooleanExtra("isFromMemory", false), false), new IBooleanListener() {
                    @Override
                    public void callBack(boolean z) {
                        if (z) {
                            stopSelf();
                        }
                    }
                });
    }

}
