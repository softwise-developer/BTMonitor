package com.softwise.trumonitor.models;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.softwise.trumonitor.database.DatabaseClient;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.database.dbListeners.ISensorDataCallback;

import java.util.List;


public class SensorTempViewModel extends AndroidViewModel {
    private Context mContext;
    /* access modifiers changed from: private */
    public final MutableLiveData<List<EntitySensor>> sensorLiveData = new MutableLiveData<>();
    private boolean viewModelSetup = false;

    public SensorTempViewModel(Application application) {
        super(application);
    }

    public boolean setupViewModel(Context context) {
        if (!this.viewModelSetup) {
            this.mContext = context;
            this.viewModelSetup = true;
        }
        getSensorDataFromLocalDB();
        return true;
    }

    public LiveData<List<EntitySensor>> fetchSensorData() {
        return this.sensorLiveData;
    }

    private void getSensorDataFromLocalDB() {
        try {
            new DatabaseClient(this.mContext).getAllSensor(new ISensorDataCallback() {
                public void onLoadAllSensor(List<EntitySensor> list) {
                    Log.e("Size temp value", String.valueOf(list.size()));
                    SensorTempViewModel.this.sensorLiveData.postValue(list);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        Log.e("Model disconnect ", NotificationCompat.CATEGORY_CALL);
        super.onCleared();
    }
}
