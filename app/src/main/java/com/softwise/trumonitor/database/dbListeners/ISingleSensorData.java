package com.softwise.trumonitor.database.dbListeners;


import com.softwise.trumonitor.database.EntitySensor;

public interface ISingleSensorData {
    void onLoadSensor(EntitySensor entitySensor);
}
