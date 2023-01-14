package com.softwise.trumonitor.database.dbListeners;

import com.softwise.trumonitor.database.SensorTempTime;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.List;


public interface SensorTempTimeDao {
    void deleteSensorTempById(int i);

    Maybe<List<SensorTempTime>> getAllSensorTemp();

    Flowable<List<SensorTempTime>> getSensorTempById(int i);

    Flowable<List<SensorTempTime>> getSingleSensorTempByFlag(boolean z);

    Flowable<SensorTempTime> getSingleSensorTempById(int i);

    void insert(SensorTempTime sensorTempTime);
}
