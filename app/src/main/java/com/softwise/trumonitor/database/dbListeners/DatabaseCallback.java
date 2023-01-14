package com.softwise.trumonitor.database.dbListeners;

public interface DatabaseCallback {
    void onDataNotAvailable();

    void onSensorAdded();

    void onSensorDeleted();

    void onSensorUpdated();
}
