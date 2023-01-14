package com.softwise.trumonitor.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.softwise.trumonitor.bluetoothListener.MyService;
import com.softwise.trumonitor.database.DatabaseClient;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.database.ISensorTempCallback;
import com.softwise.trumonitor.database.SensorTempTime;
import com.softwise.trumonitor.database.dbListeners.ISingleSensorData;
import com.softwise.trumonitor.database.dbListeners.SensorDatabaseCallback;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.listeners.IObserveEntitySensorListener;
import com.softwise.trumonitor.models.Sensor;
import com.softwise.trumonitor.serverUtils.ApiClients;
import com.softwise.trumonitor.serverUtils.ServiceListeners.APIService;
import com.softwise.trumonitor.utils.BluetoothConstants;
import com.softwise.trumonitor.utils.ConnectionUtils;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import java.util.List;

import rx.Observer;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerDatabaseHelper {
    static int cnt;
    /* access modifiers changed from: private */
    public static Context mCtx;
    private static ServerDatabaseHelper mInstance;
    private static MediaPlayer mediaPlayer;
    private FusedLocationProviderClient fusedLocationClient;
    private IObserveEntitySensorListener mIObserveEntitySensorListener;

    private void uploadDataOnSever(EntitySensor entitySensor, boolean z, int i, SensorTempTime sensorTempTime) {
    }

    public ServerDatabaseHelper(Context context) {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mCtx = context;
    }

    public static synchronized ServerDatabaseHelper getInstance(Context context) {
        ServerDatabaseHelper serverDatabaseHelper;
        synchronized (ServerDatabaseHelper.class) {
            if (mInstance == null) {
                mInstance = new ServerDatabaseHelper(context);
            }
            mCtx = context;
            serverDatabaseHelper = mInstance;
        }
        return serverDatabaseHelper;
    }

    public void saveSensorDataFromMemoryToServer(Context context, String str, boolean z,
                                                 IObserveEntitySensorListener iObserveEntitySensorListener) {
        String str2;
        String str3;
        int i;
        String[] strArr;
        String str4 = str;
        Log.e("in next", "ok");
        Log.e("in next", str4);
        try {
            this.mIObserveEntitySensorListener = iObserveEntitySensorListener;
            if (str4.contains("_")) {
                Log.e("in next", "1");
                String[] split = str4.split("_");
                if (split.length > 0) {
                    Log.e("in next", ExifInterface.GPS_MEASUREMENT_2D);
                    char c = 0;
                    int i2 = 0;
                    while (i2 < split.length) {
                        Log.e("in next", "for");
                        String[] split2 = split[i2].trim().split("!");
                        String[] split3 = split2[c].split("@");
                        char c2 = 1;
                        String[] split4 = split2[1].split(",");
                        int parseInt = Integer.parseInt(split4[c]);
                        SPTrueTemp.saveBatteryLevel(context, split4[1]);
                        String str5 = split4[2];
                        String str6 = split4[3];
                        Log.e("UploadResponse", Integer.toString(split3.length));
                        int i3 = 0;
                        while (i3 < split3.length) {
                            String[] split5 = split3[i3].split(",");
                            Log.e("valueArray", split5[c]);
                            String str7 = split5[c];
                            String substring = split5[c2].substring(split5[c2].length() - 1);
                            String replace = split5[c2].replace(substring, "");
                            Log.e("tempValue", replace);
                            String str8 = split5[1];
                            if (z) {
                                int parseInt2 = Integer.parseInt(str7);
                                EntitySensor createSingleEntitySensor = MethodHelper.createSingleEntitySensor(context, parseInt2, replace, substring, str5 + " " + str6, str8, parseInt);
                                Log.e("isMemory", "ok");
                                getCurrentLocation(createSingleEntitySensor, true, 0);
                                i = i3;
                                str3 = str6;
                                str2 = str5;
                                strArr = split3;
                            } else {
                                Log.e("isNotMemory", "ok");
                                int parseInt3 = Integer.parseInt(str7);
                                i = i3;
                                str3 = str6;
                                str2 = str5;
                                strArr = split3;
                                getSensorDataFromDB(context, parseInt3, parseInt, replace, substring, str8, str5 + " " + str6, false);
                            }
                            i3 = i + 1;
                            Context context2 = context;
                            split3 = strArr;
                            str6 = str3;
                            str5 = str2;
                            c2 = 1;
                            c = 0;
                        }
                        i2++;
                        c = 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSensorDataFromDB(Context context, int i, int i2, String str, String str2, String str3, String str4, boolean z) {
        Log.e("insideggetsenfromDb", Integer.toString(i));
        final int i3 = i;
        final int i4 = i2;
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str4;
        final String str8 = str3;
        final boolean z2 = z;
        int i5 = i;
        DatabaseClient.getInstance(mCtx).getEntitySensorData(i, new ISingleSensorData() {
            @Override
            public void onLoadSensor(EntitySensor entitySensor) {
                if (entitySensor != null) {
                    entitySensor.setBle_sensor_id(i3);
                    entitySensor.setAsset_id(i4);
                    entitySensor.setTemp_value(str5);
                    entitySensor.setUnit(str6);
                    entitySensor.setTime(str7);
                    entitySensor.setStatus(MethodHelper.sensorStatusVale(str8));
                    Log.e("insidegetEntitySensorDa", Integer.toString(i3));
                    if ("ST".equals(SPTrueTemp.getCallingType(ServerDatabaseHelper.mCtx))) {
                        try {
                            Intent intent = new Intent("sensorTemp");
                            intent.putExtra(NotificationCompat.CATEGORY_MESSAGE, entitySensor);
                            LocalBroadcastManager.getInstance(ServerDatabaseHelper.mCtx).sendBroadcast(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    boolean z = z2;

                    if (z) {
                        ServerDatabaseHelper.this.getCurrentLocation(entitySensor, z, 0);
                    } else {
                        ServerDatabaseHelper.this.startTimeForFrequency(entitySensor, z);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void startTimeForFrequency(EntitySensor entitySensor, boolean z) {
        String str;
        if (!entitySensor.isFlag()) {
            return;
        }
        if (!z) {
            if ("alarm_low".equals(entitySensor.getStatus()) || "alarm_high".equals(entitySensor.getStatus())) {
                str = BluetoothConstants.ALARM_UPDATE_FREQUENCY;
            } else {
                str = entitySensor.getUpdate_frequency();
                MethodHelper.stopAlarm();
            }
            checkFrequencyUpdateTime(entitySensor, str);
            return;
        }
        getCurrentLocation(entitySensor, z, 0);
    }

    private void checkFrequencyUpdateTime(final EntitySensor entitySensor, String str) {
        Log.e("FrequencyUpdateTime", Integer.toString(MethodHelper.convertStringTimeToSec(str)));
        boolean updateTime = MethodHelper.getUpdateTime(mCtx, str, entitySensor);
        if (updateTime) {
            int i = cnt;
            if (i <= 4) {
                cnt = i + 1;
                Log.e("Result", String.valueOf(updateTime));
                DatabaseClient.getInstance(mCtx).updateSensorData(entitySensor);
                this.mIObserveEntitySensorListener.getEntitySensor(entitySensor);
                ((APIService) ApiClients.getRetrofitInstanceForUpload(true).create(APIService.class))
                        .uploadTempData(BluetoothConstants.CONTENT_TYPE, MethodHelper.getJsonArray(mCtx, entitySensor, false, false))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable th) {
                        th.printStackTrace();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Log.e("UploadResponse11", jsonObject.get("message").toString());
                        Log.e("of", Integer.toString(entitySensor.getBle_sensor_id()));
                    }
                });
            }
        } else if (((int) (System.currentTimeMillis() / 1000)) % 30 > 25) {
            cnt = 0;
        }
    }

    /* access modifiers changed from: private */
    public void getCurrentLocation(final EntitySensor entitySensor, final boolean z, final int i) {
        Log.e("in Location", "ok");
        if (!(ActivityCompat.checkSelfPermission(mCtx, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(mCtx, "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
            Log.e("in Location", "1");
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
           @Override
            public void onComplete(Task<Location> task) {
                String str;
                String str2;
                try {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        str2 = SPTrueTemp.getLatitude(ServerDatabaseHelper.mCtx);
                        str = SPTrueTemp.getLongitude(ServerDatabaseHelper.mCtx);
                    } else {
                        str = "0.00";
                        if (task.getResult().getLatitude() > 0.0d) {
                            str2 = String.valueOf(task.getResult().getLatitude());
                            SPTrueTemp.saveLatitude(ServerDatabaseHelper.mCtx, str2);
                        } else {
                            str2 = str;
                        }
                        if (task.getResult().getLongitude() > 0.0d) {
                            str = String.valueOf(task.getResult().getLongitude());
                            SPTrueTemp.saveLongitude(ServerDatabaseHelper.mCtx, str);
                        }
                        Log.e("LOCATION ", str2 + "," + str);
                    }
                    ServerDatabaseHelper.this.saveAndUploadDataOnSever(entitySensor, str2, str, z, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveAndUploadDataOnSever(EntitySensor entitySensor, String str, String str2, boolean z, int i) {
        entitySensor.setLat(str);
        entitySensor.setLng(str2);
        SensorTempTime createSensorTempTime = MethodHelper.createSensorTempTime(entitySensor, str, str2, z);
        if (ConnectionUtils.getConnectivityStatusString(mCtx)) {
            createSensorTempTime.setFlag(true);
            createSensorTempTime.setAssets_id(entitySensor.getAsset_id());
            entitySensor.setFlag(true);
            if (!isMyServiceRunning(mCtx, MyService.class)) {
                Log.e("Upload Temp Service ", "Stopped");
                createSensorTempTime.setUploadPending(true);
                onStartJobIntentService(mCtx, String.valueOf(entitySensor.getBle_sensor_id()), entitySensor, z, i);
            } else {
                Log.e("Upload Temp Service ", "Running");
                onStartJobIntentService(mCtx, String.valueOf(entitySensor.getBle_sensor_id()), entitySensor, z, i);
            }
        }
        if (!z) {
            DatabaseClient.getInstance(mCtx).addSensorTemp(createSensorTempTime);
        }
    }

    public void stopService(Context context) {
        try {
            Intent intent = new Intent(context, MyService.class);
            intent.setAction(BluetoothConstants.ACTION.STOP_ACTION);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void onStartJobIntentService(Context context, String str, EntitySensor entitySensor, boolean z, int i) {
        Intent intent = new Intent(context, MyService.class);
        intent.setAction(BluetoothConstants.ACTION.START_ACTION);
        intent.putExtra("inputExtra", "Foreground Service Example in Android");
        intent.putExtra("sensorId", str);
        intent.putExtra("isFromMemory", z);
        intent.putExtra("entitySensor", entitySensor);
        intent.putExtra("frequency", i);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    public void saveSensorListInLocalDB(Context context, List<Sensor> list, final IBooleanListener iBooleanListener) {
        DatabaseClient instance = DatabaseClient.getInstance(context);
        List<EntitySensor> generateEntitySensor = MethodHelper.generateEntitySensor(context, list);
        instance.deleteSensorTable();
        instance.saveEntitySensorList(generateEntitySensor, new SensorDatabaseCallback() {
            @Override
            public void onSensorAdded() {
                iBooleanListener.callBack(true);
            }

            @Override
            public void onSensorAddedFailed() {
                iBooleanListener.callBack(false);
            }
        });
    }

    public void getSensorTemperature(int i, final ISensorTempCallback iSensorTempCallback) {
        DatabaseClient.getInstance(mCtx).getSensorTemp(i, new ISensorTempCallback() {
            @Override
            public void loadTemperature(List<SensorTempTime> list) {
                iSensorTempCallback.loadTemperature(list);
            }
        });
    }
}
