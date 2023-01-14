package com.softwise.trumonitor.database;

import java.util.List;

public interface ISensorTempCallback {
    void loadTemperature(List<SensorTempTime> list);
}
