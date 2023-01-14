package com.softwise.trumonitor.database;



import com.softwise.trumonitor.database.dbListeners.GpslocDao;
import com.softwise.trumonitor.database.dbListeners.SensorDao;
import com.softwise.trumonitor.database.dbListeners.SensorTempTimeDao;

import androidx.room.RoomDatabase;


public abstract class AppDatabase extends RoomDatabase {
    public abstract GpslocDao gpslocDao();

    public abstract SensorDao sensorDao();

    public abstract SensorTempTimeDao sensorTempTimeDao();
}
