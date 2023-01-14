package com.softwise.trumonitor.bluetoothListener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.softwise.trumonitor.database.DatabaseClient;
import com.softwise.trumonitor.database.ISensorTempCallback;
import com.softwise.trumonitor.database.SensorTempTime;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.implementer.SensorPresenter;
import com.softwise.trumonitor.listeners.IBooleanListener;

import androidx.core.app.JobIntentService;
import java.util.List;

public class DataUploadService extends JobIntentService {
    private static final int JOB_ID = 1;
    private static final String TAG = "MyJobIntentService";
    Context mContext;

    public static void enqueueWork(Context context, Intent intent) {
        Log.e("inside Data", " enque Work");
        enqueueWork(context, (Class<?>) DataUploadService.class, 1, intent);
    }

    /* access modifiers changed from: protected */
    @Override
    public void onHandleWork(Intent intent) {
        this.mContext = this;
        checkDataIsPendingForUpload(intent);
    }

    private void checkDataIsPendingForUpload(final Intent intent) {
        DatabaseClient.getInstance(this.mContext).getAllSensorTemp(new ISensorTempCallback() {
           @Override
            public void loadTemperature(final List<SensorTempTime> list) {
                Log.e("", " Upload started");
                if (list == null || list.size() <= 0) {
                    DataUploadService.this.stopSelf();
                    return;
                }
                final int[] iArr = {0};
                for (final SensorTempTime next : list) {
                    int timeDifference = MethodHelper.getTimeDifference(next.getTime());
                    if (next.isFlag() && timeDifference >= 24) {
                        DatabaseClient.getInstance(DataUploadService.this.mContext).deleteSensorTempId(next.getId());
                    } else if (!next.isFlag() || next.isUploadPending()) {
                        Log.e("Pending upload ", next.getTime() + " " + next.getSensor_id());
                        new SensorPresenter(DataUploadService.this.getApplicationContext())
                                .uploadData(String.valueOf(next.getSensor_id()), MethodHelper.getJsonArray(DataUploadService.this.getApplicationContext(), MethodHelper.createSingleEntitySensor(DataUploadService.this.getApplicationContext(), next.getSensor_id(), String.valueOf(next.getTemp_value()), next.getUnit(), next.getTime(), next.getStatus(), next.getAssets_id()), false, true), new IBooleanListener() {
                           @Override
                            public void callBack(boolean z) {
                                iArr[0] = iArr[0] + 1;
                                DatabaseClient.getInstance(DataUploadService.this.mContext).deleteSensorTempId(next.getId());
                                if (iArr[0] == list.size()) {
                                    DataUploadService.this.stopService(intent);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
