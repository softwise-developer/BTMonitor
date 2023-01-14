package com.softwise.trumonitor.database.dbListeners;


import com.softwise.trumonitor.database.SensorTempTime;

public interface ISingleSensorTempCallback {
    void onSensorTempLoad(SensorTempTime sensorTempTime);
}
