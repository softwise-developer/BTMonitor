package com.softwise.trumonitor.database;

import com.softwise.trumonitor.database.dbListeners.GpslocDao;
import com.softwise.trumonitor.database.dbListeners.GpslocDao_Impl;
import com.softwise.trumonitor.database.dbListeners.SensorDao;
import com.softwise.trumonitor.database.dbListeners.SensorDao_Impl;
import com.softwise.trumonitor.database.dbListeners.SensorTempTimeDao;
import com.softwise.trumonitor.database.dbListeners.SensorTempTimeDao_Impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;


public final class AppDatabase_Impl extends AppDatabase {
    private volatile GpslocDao _gpslocDao;
    private volatile SensorDao _sensorDao;
    private volatile SensorTempTimeDao _sensorTempTimeDao;

    /* access modifiers changed from: protected */
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context)
                .name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(2) {
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `EntitySensor` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ble_sensor_id` INTEGER NOT NULL, `ble_asset_id` INTEGER NOT NULL, `sensor_name` TEXT, `assets_name` TEXT, `temp_value` TEXT, `unit` TEXT, `time` TEXT, `status` TEXT, `fre` TEXT, `lat` TEXT, `lng` TEXT, `flag` INTEGER NOT NULL, `aLow` INTEGER NOT NULL, `aHigh` INTEGER NOT NULL, `wLow` INTEGER NOT NULL, `wHigh` INTEGER NOT NULL)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `SensorTempTime` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ble_sensor_id` INTEGER NOT NULL, `ble_assets_id` INTEGER NOT NULL, `assets_name` TEXT, `temp_value` REAL NOT NULL, `unit` TEXT, `time` TEXT, `memory` INTEGER NOT NULL, `flag` INTEGER NOT NULL, `lat` TEXT, `lng` TEXT, `status` TEXT, `isPending` INTEGER NOT NULL)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `gpsloc` (`gpsloc_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `latitude` TEXT, `longitude` TEXT)");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd30dfb71e125502cc22a121589549d9b')");
            }

            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `EntitySensor`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `SensorTempTime`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `gpsloc`");
                if (AppDatabase_Impl.this.mCallbacks != null) {
                    int size = AppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(supportSQLiteDatabase);
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (AppDatabase_Impl.this.mCallbacks != null) {
                    int size = AppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppDatabase_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                SupportSQLiteDatabase unused = AppDatabase_Impl.this.mDatabase = supportSQLiteDatabase;
                AppDatabase_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (AppDatabase_Impl.this.mCallbacks != null) {
                    int size = AppDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) AppDatabase_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
                DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
            }

            /* access modifiers changed from: protected */
            public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase supportSQLiteDatabase) {
                SupportSQLiteDatabase supportSQLiteDatabase2 = supportSQLiteDatabase;
                HashMap hashMap = new HashMap(17);
                hashMap.put("id", new TableInfo.Column("id", "INTEGER", true, 1, (String) null, 1));
                hashMap.put("ble_sensor_id", new TableInfo.Column("ble_sensor_id", "INTEGER", true, 0, (String) null, 1));
                hashMap.put("ble_asset_id", new TableInfo.Column("ble_asset_id", "INTEGER", true, 0, (String) null, 1));
                hashMap.put("sensor_name", new TableInfo.Column("sensor_name", "TEXT", false, 0, (String) null, 1));
                hashMap.put("assets_name", new TableInfo.Column("assets_name", "TEXT", false, 0, (String) null, 1));
                hashMap.put("temp_value", new TableInfo.Column("temp_value", "TEXT", false, 0, (String) null, 1));
                hashMap.put("unit", new TableInfo.Column("unit", "TEXT", false, 0, (String) null, 1));
                hashMap.put("time", new TableInfo.Column("time", "TEXT", false, 0, (String) null, 1));
                hashMap.put("status", new TableInfo.Column("status", "TEXT", false, 0, (String) null, 1));
                hashMap.put("fre", new TableInfo.Column("fre", "TEXT", false, 0, (String) null, 1));
                hashMap.put("lat", new TableInfo.Column("lat", "TEXT", false, 0, (String) null, 1));
                hashMap.put("lng", new TableInfo.Column("lng", "TEXT", false, 0, (String) null, 1));
                hashMap.put("flag", new TableInfo.Column("flag", "INTEGER", true, 0, (String) null, 1));
                hashMap.put("aLow", new TableInfo.Column("aLow", "INTEGER", true, 0, (String) null, 1));
                hashMap.put("aHigh", new TableInfo.Column("aHigh", "INTEGER", true, 0, (String) null, 1));
                hashMap.put("wLow", new TableInfo.Column("wLow", "INTEGER", true, 0, (String) null, 1));
                hashMap.put("wHigh", new TableInfo.Column("wHigh", "INTEGER", true, 0, (String) null, 1));
                TableInfo tableInfo = new TableInfo("EntitySensor", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(supportSQLiteDatabase2, "EntitySensor");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "EntitySensor(softwise.mechatronics.truBlueMonitor.database.EntitySensor).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(13);
                hashMap2.put("id", new TableInfo.Column("id", "INTEGER", true, 1, (String) null, 1));
                hashMap2.put("ble_sensor_id", new TableInfo.Column("ble_sensor_id", "INTEGER", true, 0, (String) null, 1));
                hashMap2.put("ble_assets_id", new TableInfo.Column("ble_assets_id", "INTEGER", true, 0, (String) null, 1));
                hashMap2.put("assets_name", new TableInfo.Column("assets_name", "TEXT", false, 0, (String) null, 1));
                hashMap2.put("temp_value", new TableInfo.Column("temp_value", "REAL", true, 0, (String) null, 1));
                hashMap2.put("unit", new TableInfo.Column("unit", "TEXT", false, 0, (String) null, 1));
                hashMap2.put("time", new TableInfo.Column("time", "TEXT", false, 0, (String) null, 1));
                hashMap2.put("memory", new TableInfo.Column("memory", "INTEGER", true, 0, (String) null, 1));
                hashMap2.put("flag", new TableInfo.Column("flag", "INTEGER", true, 0, (String) null, 1));
                hashMap2.put("lat", new TableInfo.Column("lat", "TEXT", false, 0, (String) null, 1));
                hashMap2.put("lng", new TableInfo.Column("lng", "TEXT", false, 0, (String) null, 1));
                hashMap2.put("status", new TableInfo.Column("status", "TEXT", false, 0, (String) null, 1));
                hashMap2.put("isPending", new TableInfo.Column("isPending", "INTEGER", true, 0, (String) null, 1));
                TableInfo tableInfo2 = new TableInfo("SensorTempTime", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(supportSQLiteDatabase2, "SensorTempTime");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(false, "SensorTempTime(softwise.mechatronics.truBlueMonitor.database.SensorTempTime).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
                }
                HashMap hashMap3 = new HashMap(4);
                hashMap3.put("gpsloc_id", new TableInfo.Column("gpsloc_id", "INTEGER", true, 1, (String) null, 1));
                hashMap3.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, (String) null, 1));
                hashMap3.put("latitude", new TableInfo.Column("latitude", "TEXT", false, 0, (String) null, 1));
                hashMap3.put("longitude", new TableInfo.Column("longitude", "TEXT", false, 0, (String) null, 1));
                TableInfo tableInfo3 = new TableInfo("gpsloc", hashMap3, new HashSet(0), new HashSet(0));
                TableInfo read3 = TableInfo.read(supportSQLiteDatabase2, "gpsloc");
                if (tableInfo3.equals(read3)) {
                    return new RoomOpenHelper.ValidationResult(true, (String) null);
                }
                return new RoomOpenHelper.ValidationResult(false, "gpsloc(softwise.mechatronics.truBlueMonitor.database.gpsloc).\n Expected:\n" + tableInfo3 + "\n Found:\n" + read3);
            }
        }, "d30dfb71e125502cc22a121589549d9b", "75c38298a4c7d1ada000ac26bb1cc890")).build());
    }

    /* access modifiers changed from: protected */
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "EntitySensor", "SensorTempTime", "gpsloc");
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `EntitySensor`");
            writableDatabase.execSQL("DELETE FROM `SensorTempTime`");
            writableDatabase.execSQL("DELETE FROM `gpsloc`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    /* access modifiers changed from: protected */
    public Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap hashMap = new HashMap();
        hashMap.put(SensorDao.class, SensorDao_Impl.getRequiredConverters());
        hashMap.put(GpslocDao.class, GpslocDao_Impl.getRequiredConverters());
        hashMap.put(SensorTempTimeDao.class, SensorTempTimeDao_Impl.getRequiredConverters());
        return hashMap;
    }

    public SensorDao sensorDao() {
        SensorDao sensorDao;
        if (this._sensorDao != null) {
            return this._sensorDao;
        }
        synchronized (this) {
            if (this._sensorDao == null) {
                this._sensorDao = new SensorDao_Impl(this);
            }
            sensorDao = this._sensorDao;
        }
        return sensorDao;
    }

    public GpslocDao gpslocDao() {
        GpslocDao gpslocDao;
        if (this._gpslocDao != null) {
            return this._gpslocDao;
        }
        synchronized (this) {
            if (this._gpslocDao == null) {
                this._gpslocDao = new GpslocDao_Impl(this);
            }
            gpslocDao = this._gpslocDao;
        }
        return gpslocDao;
    }

    public SensorTempTimeDao sensorTempTimeDao() {
        SensorTempTimeDao sensorTempTimeDao;
        if (this._sensorTempTimeDao != null) {
            return this._sensorTempTimeDao;
        }
        synchronized (this) {
            if (this._sensorTempTimeDao == null) {
                this._sensorTempTimeDao = new SensorTempTimeDao_Impl(this);
            }
            sensorTempTimeDao = this._sensorTempTimeDao;
        }
        return sensorTempTimeDao;
    }
}
