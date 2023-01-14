package com.softwise.trumonitor.database.dbListeners;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;

import androidx.room.RxRoom;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import com.softwise.trumonitor.database.EntitySensor;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;


public final class SensorDao_Impl implements SensorDao {
    /* access modifiers changed from: private */
    public final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter<EntitySensor> __deletionAdapterOfEntitySensor;
    private final EntityInsertionAdapter<EntitySensor> __insertionAdapterOfEntitySensor;
    /* access modifiers changed from: private */
    public final EntityInsertionAdapter<EntitySensor> __insertionAdapterOfEntitySensor_1;
    private final SharedSQLiteStatement __preparedStmtOfDeleteById;
    private final SharedSQLiteStatement __preparedStmtOfDeleteSensorTable;
    private final SharedSQLiteStatement __preparedStmtOfUpdateNetworkFlag;
    private final SharedSQLiteStatement __preparedStmtOfUpdateTemp;
    private final EntityDeletionOrUpdateAdapter<EntitySensor> __updateAdapterOfEntitySensor;

    public SensorDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfEntitySensor = new EntityInsertionAdapter<EntitySensor>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `EntitySensor` (`id`,`ble_sensor_id`,`ble_asset_id`,`sensor_name`,`assets_name`,`temp_value`,`unit`,`time`,`status`,`fre`,`lat`,`lng`,`flag`,`aLow`,`aHigh`,`wLow`,`wHigh`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, EntitySensor entitySensor) {
                supportSQLiteStatement.bindLong(1, (long) entitySensor.getId());
                supportSQLiteStatement.bindLong(2, (long) entitySensor.getBle_sensor_id());
                supportSQLiteStatement.bindLong(3, (long) entitySensor.getAsset_id());
                if (entitySensor.getSensor_name() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, entitySensor.getSensor_name());
                }
                if (entitySensor.getAssets_name() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, entitySensor.getAssets_name());
                }
                if (entitySensor.getTemp_value() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, entitySensor.getTemp_value());
                }
                if (entitySensor.getUnit() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, entitySensor.getUnit());
                }
                if (entitySensor.getTime() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, entitySensor.getTime());
                }
                if (entitySensor.getStatus() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, entitySensor.getStatus());
                }
                if (entitySensor.getUpdate_frequency() == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindString(10, entitySensor.getUpdate_frequency());
                }
                if (entitySensor.getLat() == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindString(11, entitySensor.getLat());
                }
                if (entitySensor.getLng() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindString(12, entitySensor.getLng());
                }
                supportSQLiteStatement.bindLong(13, entitySensor.isFlag() ? 1 : 0);
                supportSQLiteStatement.bindLong(14, (long) entitySensor.getAlarm_low());
                supportSQLiteStatement.bindLong(15, (long) entitySensor.getAlarm_high());
                supportSQLiteStatement.bindLong(16, (long) entitySensor.getWarning_low());
                supportSQLiteStatement.bindLong(17, (long) entitySensor.getWarning_high());
            }
        };
        this.__insertionAdapterOfEntitySensor_1 = new EntityInsertionAdapter<EntitySensor>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR ABORT INTO `EntitySensor` (`id`,`ble_sensor_id`,`ble_asset_id`,`sensor_name`,`assets_name`,`temp_value`,`unit`,`time`,`status`,`fre`,`lat`,`lng`,`flag`,`aLow`,`aHigh`,`wLow`,`wHigh`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, EntitySensor entitySensor) {
                supportSQLiteStatement.bindLong(1, (long) entitySensor.getId());
                supportSQLiteStatement.bindLong(2, (long) entitySensor.getBle_sensor_id());
                supportSQLiteStatement.bindLong(3, (long) entitySensor.getAsset_id());
                if (entitySensor.getSensor_name() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, entitySensor.getSensor_name());
                }
                if (entitySensor.getAssets_name() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, entitySensor.getAssets_name());
                }
                if (entitySensor.getTemp_value() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, entitySensor.getTemp_value());
                }
                if (entitySensor.getUnit() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, entitySensor.getUnit());
                }
                if (entitySensor.getTime() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, entitySensor.getTime());
                }
                if (entitySensor.getStatus() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, entitySensor.getStatus());
                }
                if (entitySensor.getUpdate_frequency() == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindString(10, entitySensor.getUpdate_frequency());
                }
                if (entitySensor.getLat() == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindString(11, entitySensor.getLat());
                }
                if (entitySensor.getLng() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindString(12, entitySensor.getLng());
                }
                supportSQLiteStatement.bindLong(13, entitySensor.isFlag() ? 1 : 0);
                supportSQLiteStatement.bindLong(14, (long) entitySensor.getAlarm_low());
                supportSQLiteStatement.bindLong(15, (long) entitySensor.getAlarm_high());
                supportSQLiteStatement.bindLong(16, (long) entitySensor.getWarning_low());
                supportSQLiteStatement.bindLong(17, (long) entitySensor.getWarning_high());
            }
        };
        this.__deletionAdapterOfEntitySensor = new EntityDeletionOrUpdateAdapter<EntitySensor>(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM `EntitySensor` WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, EntitySensor entitySensor) {
                supportSQLiteStatement.bindLong(1, (long) entitySensor.getId());
            }
        };
        this.__updateAdapterOfEntitySensor = new EntityDeletionOrUpdateAdapter<EntitySensor>(roomDatabase) {
            public String createQuery() {
                return "UPDATE OR ABORT `EntitySensor` SET `id` = ?,`ble_sensor_id` = ?,`ble_asset_id` = ?,`sensor_name` = ?,`assets_name` = ?,`temp_value` = ?,`unit` = ?,`time` = ?,`status` = ?,`fre` = ?,`lat` = ?,`lng` = ?,`flag` = ?,`aLow` = ?,`aHigh` = ?,`wLow` = ?,`wHigh` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, EntitySensor entitySensor) {
                supportSQLiteStatement.bindLong(1, (long) entitySensor.getId());
                supportSQLiteStatement.bindLong(2, (long) entitySensor.getBle_sensor_id());
                supportSQLiteStatement.bindLong(3, (long) entitySensor.getAsset_id());
                if (entitySensor.getSensor_name() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, entitySensor.getSensor_name());
                }
                if (entitySensor.getAssets_name() == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, entitySensor.getAssets_name());
                }
                if (entitySensor.getTemp_value() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, entitySensor.getTemp_value());
                }
                if (entitySensor.getUnit() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, entitySensor.getUnit());
                }
                if (entitySensor.getTime() == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, entitySensor.getTime());
                }
                if (entitySensor.getStatus() == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, entitySensor.getStatus());
                }
                if (entitySensor.getUpdate_frequency() == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindString(10, entitySensor.getUpdate_frequency());
                }
                if (entitySensor.getLat() == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindString(11, entitySensor.getLat());
                }
                if (entitySensor.getLng() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindString(12, entitySensor.getLng());
                }
                supportSQLiteStatement.bindLong(13, entitySensor.isFlag() ? 1 : 0);
                supportSQLiteStatement.bindLong(14, (long) entitySensor.getAlarm_low());
                supportSQLiteStatement.bindLong(15, (long) entitySensor.getAlarm_high());
                supportSQLiteStatement.bindLong(16, (long) entitySensor.getWarning_low());
                supportSQLiteStatement.bindLong(17, (long) entitySensor.getWarning_high());
                supportSQLiteStatement.bindLong(18, (long) entitySensor.getId());
            }
        };
        this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM entitysensor WHERE id = ?";
            }
        };
        this.__preparedStmtOfUpdateTemp = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE entitysensor SET temp_value=?,unit=? WHERE ble_sensor_id = ?";
            }
        };
        this.__preparedStmtOfUpdateNetworkFlag = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE entitysensor SET flag=? WHERE ble_sensor_id = ?";
            }
        };
        this.__preparedStmtOfDeleteSensorTable = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM entitysensor";
            }
        };
    }

    public void insert(EntitySensor entitySensor) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfEntitySensor.insert(entitySensor);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public Completable insertSensorData(final List<EntitySensor> list) {
        return Completable.fromCallable(new Callable<Void>() {
            public Void call() throws Exception {
                SensorDao_Impl.this.__db.beginTransaction();
                try {
                    SensorDao_Impl.this.__insertionAdapterOfEntitySensor_1.insert(list);
                    SensorDao_Impl.this.__db.setTransactionSuccessful();
                    return null;
                } finally {
                    SensorDao_Impl.this.__db.endTransaction();
                }
            }
        });
    }

    public void delete(EntitySensor entitySensor) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfEntitySensor.handle(entitySensor);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void update(EntitySensor entitySensor) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfEntitySensor.handle(entitySensor);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteById(int i) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfDeleteById.acquire();
        acquire.bindLong(1, (long) i);
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteById.release(acquire);
        }
    }

    public void updateTemp(String str, String str2, int i) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfUpdateTemp.acquire();
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        acquire.bindLong(3, (long) i);
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdateTemp.release(acquire);
        }
    }

    public void updateNetworkFlag(boolean z, int i) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfUpdateNetworkFlag.acquire();
        acquire.bindLong(1, z ? 1 : 0);
        acquire.bindLong(2, (long) i);
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfUpdateNetworkFlag.release(acquire);
        }
    }

    public void deleteSensorTable() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfDeleteSensorTable.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteSensorTable.release(acquire);
        }
    }

    public Flowable<List<EntitySensor>> getAllSensor() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM entitysensor", 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"entitysensor"}, new Callable<List<EntitySensor>>() {
            public List<EntitySensor> call() throws Exception {
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                String str7;
                String str8;
                String str9;
                Cursor query = DBUtil.query(SensorDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_asset_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "sensor_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "fre");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "aLow");
                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "aHigh");
                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "wLow");
                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "wHigh");
                    int i = columnIndexOrThrow14;
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        EntitySensor entitySensor = new EntitySensor();
                        ArrayList arrayList2 = arrayList;
                        entitySensor.setId(query.getInt(columnIndexOrThrow));
                        entitySensor.setBle_sensor_id(query.getInt(columnIndexOrThrow2));
                        entitySensor.setAsset_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        entitySensor.setSensor_name(str);
                        if (query.isNull(columnIndexOrThrow5)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow5);
                        }
                        entitySensor.setAssets_name(str2);
                        if (query.isNull(columnIndexOrThrow6)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow6);
                        }
                        entitySensor.setTemp_value(str3);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow7);
                        }
                        entitySensor.setUnit(str4);
                        if (query.isNull(columnIndexOrThrow8)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow8);
                        }
                        entitySensor.setTime(str5);
                        if (query.isNull(columnIndexOrThrow9)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow9);
                        }
                        entitySensor.setStatus(str6);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str7 = null;
                        } else {
                            str7 = query.getString(columnIndexOrThrow10);
                        }
                        entitySensor.setUpdate_frequency(str7);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str8 = null;
                        } else {
                            str8 = query.getString(columnIndexOrThrow11);
                        }
                        entitySensor.setLat(str8);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str9 = null;
                        } else {
                            str9 = query.getString(columnIndexOrThrow12);
                        }
                        entitySensor.setLng(str9);
                        entitySensor.setFlag(query.getInt(columnIndexOrThrow13) != 0);
                        int i2 = i;
                        int i3 = columnIndexOrThrow;
                        entitySensor.setAlarm_low(query.getInt(i2));
                        int i4 = columnIndexOrThrow15;
                        int i5 = i2;
                        entitySensor.setAlarm_high(query.getInt(i4));
                        int i6 = columnIndexOrThrow16;
                        int i7 = i4;
                        entitySensor.setWarning_low(query.getInt(i6));
                        int i8 = columnIndexOrThrow17;
                        int i9 = i6;
                        entitySensor.setWarning_high(query.getInt(i8));
                        arrayList = arrayList2;
                        arrayList.add(entitySensor);
                        int i10 = i9;
                        columnIndexOrThrow17 = i8;
                        columnIndexOrThrow = i3;
                        i = i5;
                        columnIndexOrThrow15 = i7;
                        columnIndexOrThrow16 = i10;
                    }
                    return arrayList;
                } finally {
                    query.close();
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                acquire.release();
            }
        });
    }

    public Flowable<List<EntitySensor>> getAllSensorData(boolean z) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM entitysensor where flag=?", 1);
        acquire.bindLong(1, z ? 1 : 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"entitysensor"}, new Callable<List<EntitySensor>>() {
            public List<EntitySensor> call() throws Exception {
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                String str7;
                String str8;
                String str9;
                Cursor query = DBUtil.query(SensorDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_asset_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "sensor_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "fre");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "aLow");
                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "aHigh");
                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "wLow");
                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "wHigh");
                    int i = columnIndexOrThrow14;
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        EntitySensor entitySensor = new EntitySensor();
                        ArrayList arrayList2 = arrayList;
                        entitySensor.setId(query.getInt(columnIndexOrThrow));
                        entitySensor.setBle_sensor_id(query.getInt(columnIndexOrThrow2));
                        entitySensor.setAsset_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        entitySensor.setSensor_name(str);
                        if (query.isNull(columnIndexOrThrow5)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow5);
                        }
                        entitySensor.setAssets_name(str2);
                        if (query.isNull(columnIndexOrThrow6)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow6);
                        }
                        entitySensor.setTemp_value(str3);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow7);
                        }
                        entitySensor.setUnit(str4);
                        if (query.isNull(columnIndexOrThrow8)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow8);
                        }
                        entitySensor.setTime(str5);
                        if (query.isNull(columnIndexOrThrow9)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow9);
                        }
                        entitySensor.setStatus(str6);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str7 = null;
                        } else {
                            str7 = query.getString(columnIndexOrThrow10);
                        }
                        entitySensor.setUpdate_frequency(str7);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str8 = null;
                        } else {
                            str8 = query.getString(columnIndexOrThrow11);
                        }
                        entitySensor.setLat(str8);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str9 = null;
                        } else {
                            str9 = query.getString(columnIndexOrThrow12);
                        }
                        entitySensor.setLng(str9);
                        entitySensor.setFlag(query.getInt(columnIndexOrThrow13) != 0);
                        int i2 = i;
                        int i3 = columnIndexOrThrow;
                        entitySensor.setAlarm_low(query.getInt(i2));
                        int i4 = columnIndexOrThrow15;
                        int i5 = i2;
                        entitySensor.setAlarm_high(query.getInt(i4));
                        int i6 = columnIndexOrThrow16;
                        int i7 = i4;
                        entitySensor.setWarning_low(query.getInt(i6));
                        int i8 = columnIndexOrThrow17;
                        int i9 = i6;
                        entitySensor.setWarning_high(query.getInt(i8));
                        arrayList = arrayList2;
                        arrayList.add(entitySensor);
                        int i10 = i9;
                        columnIndexOrThrow17 = i8;
                        columnIndexOrThrow = i3;
                        i = i5;
                        columnIndexOrThrow15 = i7;
                        columnIndexOrThrow16 = i10;
                    }
                    return arrayList;
                } finally {
                    query.close();
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                acquire.release();
            }
        });
    }

    public Flowable<EntitySensor> getSensorById(int i) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM entitysensor where ble_sensor_id=?", 1);
        acquire.bindLong(1, (long) i);
        return RxRoom.createFlowable(this.__db, false, new String[]{"entitysensor"}, new Callable<EntitySensor>() {
            public EntitySensor call() throws Exception {
                EntitySensor entitySensor;
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                String str7;
                String str8;
                String str9;
                Cursor query = DBUtil.query(SensorDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_asset_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "sensor_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "fre");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "aLow");
                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "aHigh");
                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "wLow");
                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "wHigh");
                    if (query.moveToFirst()) {
                        int i = columnIndexOrThrow17;
                        EntitySensor entitySensor2 = new EntitySensor();
                        entitySensor2.setId(query.getInt(columnIndexOrThrow));
                        entitySensor2.setBle_sensor_id(query.getInt(columnIndexOrThrow2));
                        entitySensor2.setAsset_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        entitySensor2.setSensor_name(str);
                        if (query.isNull(columnIndexOrThrow5)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow5);
                        }
                        entitySensor2.setAssets_name(str2);
                        if (query.isNull(columnIndexOrThrow6)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow6);
                        }
                        entitySensor2.setTemp_value(str3);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow7);
                        }
                        entitySensor2.setUnit(str4);
                        if (query.isNull(columnIndexOrThrow8)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow8);
                        }
                        entitySensor2.setTime(str5);
                        if (query.isNull(columnIndexOrThrow9)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow9);
                        }
                        entitySensor2.setStatus(str6);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str7 = null;
                        } else {
                            str7 = query.getString(columnIndexOrThrow10);
                        }
                        entitySensor2.setUpdate_frequency(str7);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str8 = null;
                        } else {
                            str8 = query.getString(columnIndexOrThrow11);
                        }
                        entitySensor2.setLat(str8);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str9 = null;
                        } else {
                            str9 = query.getString(columnIndexOrThrow12);
                        }
                        entitySensor2.setLng(str9);
                        entitySensor2.setFlag(query.getInt(columnIndexOrThrow13) != 0);
                        entitySensor2.setAlarm_low(query.getInt(columnIndexOrThrow14));
                        entitySensor2.setAlarm_high(query.getInt(columnIndexOrThrow15));
                        entitySensor2.setWarning_low(query.getInt(columnIndexOrThrow16));
                        entitySensor2.setWarning_high(query.getInt(i));
                        entitySensor = entitySensor2;
                    } else {
                        entitySensor = null;
                    }
                    return entitySensor;
                } finally {
                    query.close();
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                acquire.release();
            }
        });
    }

    public int isDataExist(int i) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM entitysensor WHERE ble_sensor_id = ?", 1);
        acquire.bindLong(1, (long) i);
        this.__db.assertNotSuspendingTransaction();
        int i2 = 0;
        Cursor query = DBUtil.query(this.__db, acquire, false, (CancellationSignal) null);
        try {
            if (query.moveToFirst()) {
                i2 = query.getInt(0);
            }
            return i2;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public Flowable<EntitySensor> getSensorByFlag(boolean z) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM entitysensor where flag=?", 1);
        acquire.bindLong(1, z ? 1 : 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"entitysensor"}, new Callable<EntitySensor>() {
            public EntitySensor call() throws Exception {
                EntitySensor entitySensor;
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                String str7;
                String str8;
                String str9;
                Cursor query = DBUtil.query(SensorDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_asset_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "sensor_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "fre");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(query, "aLow");
                    int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(query, "aHigh");
                    int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(query, "wLow");
                    int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(query, "wHigh");
                    if (query.moveToFirst()) {
                        int i = columnIndexOrThrow17;
                        EntitySensor entitySensor2 = new EntitySensor();
                        entitySensor2.setId(query.getInt(columnIndexOrThrow));
                        entitySensor2.setBle_sensor_id(query.getInt(columnIndexOrThrow2));
                        entitySensor2.setAsset_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        entitySensor2.setSensor_name(str);
                        if (query.isNull(columnIndexOrThrow5)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow5);
                        }
                        entitySensor2.setAssets_name(str2);
                        if (query.isNull(columnIndexOrThrow6)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow6);
                        }
                        entitySensor2.setTemp_value(str3);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow7);
                        }
                        entitySensor2.setUnit(str4);
                        if (query.isNull(columnIndexOrThrow8)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow8);
                        }
                        entitySensor2.setTime(str5);
                        if (query.isNull(columnIndexOrThrow9)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow9);
                        }
                        entitySensor2.setStatus(str6);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str7 = null;
                        } else {
                            str7 = query.getString(columnIndexOrThrow10);
                        }
                        entitySensor2.setUpdate_frequency(str7);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str8 = null;
                        } else {
                            str8 = query.getString(columnIndexOrThrow11);
                        }
                        entitySensor2.setLat(str8);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str9 = null;
                        } else {
                            str9 = query.getString(columnIndexOrThrow12);
                        }
                        entitySensor2.setLng(str9);
                        entitySensor2.setFlag(query.getInt(columnIndexOrThrow13) != 0);
                        entitySensor2.setAlarm_low(query.getInt(columnIndexOrThrow14));
                        entitySensor2.setAlarm_high(query.getInt(columnIndexOrThrow15));
                        entitySensor2.setWarning_low(query.getInt(columnIndexOrThrow16));
                        entitySensor2.setWarning_high(query.getInt(i));
                        entitySensor = entitySensor2;
                    } else {
                        entitySensor = null;
                    }
                    return entitySensor;
                } finally {
                    query.close();
                }
            }

            /* access modifiers changed from: protected */
            public void finalize() {
                acquire.release();
            }
        });
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
