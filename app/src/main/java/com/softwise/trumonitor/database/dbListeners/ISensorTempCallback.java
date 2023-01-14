package com.softwise.trumonitor.database.dbListeners;

import com.softwise.trumonitor.database.SensorTempTime;

import java.util.List;


public interface ISensorTempCallback {
    void onTempLoad(List<SensorTempTime> list);
}
