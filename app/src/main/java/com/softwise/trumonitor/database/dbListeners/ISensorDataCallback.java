package com.softwise.trumonitor.database.dbListeners;

import com.softwise.trumonitor.database.EntitySensor;

import java.util.List;


public interface ISensorDataCallback {
    void onLoadAllSensor(List<EntitySensor> list);
}
