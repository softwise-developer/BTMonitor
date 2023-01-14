package com.softwise.trumonitor.database.dbListeners;

public interface SensorDatabaseCallback {
    void onSensorAdded();

    void onSensorAddedFailed();
}
