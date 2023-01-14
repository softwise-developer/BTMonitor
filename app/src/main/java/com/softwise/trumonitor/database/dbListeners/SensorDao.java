package com.softwise.trumonitor.database.dbListeners;

import com.softwise.trumonitor.database.EntitySensor;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;


public interface SensorDao {
    void delete(EntitySensor entitySensor);

    void deleteById(int i);

    void deleteSensorTable();

    Flowable<List<EntitySensor>> getAllSensor();

    Flowable<List<EntitySensor>> getAllSensorData(boolean z);

    Flowable<EntitySensor> getSensorByFlag(boolean z);

    Flowable<EntitySensor> getSensorById(int i);

    void insert(EntitySensor entitySensor);

    Completable insertSensorData(List<EntitySensor> list);

    int isDataExist(int i);

    void update(EntitySensor entitySensor);

    void updateNetworkFlag(boolean z, int i);

    void updateTemp(String str, String str2, int i);
}
