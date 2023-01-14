package com.softwise.trumonitor.database.dbListeners;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.RxRoom;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import com.softwise.trumonitor.database.SensorTempTime;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;


public final class SensorTempTimeDao_Impl implements SensorTempTimeDao {
    /* access modifiers changed from: private */
    public final RoomDatabase __db;
    private final EntityInsertionAdapter<SensorTempTime> __insertionAdapterOfSensorTempTime;
    private final SharedSQLiteStatement __preparedStmtOfDeleteSensorTempById;

    public SensorTempTimeDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfSensorTempTime = new EntityInsertionAdapter<SensorTempTime>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR ABORT INTO `SensorTempTime` (`id`,`ble_sensor_id`,`ble_assets_id`,`assets_name`,`temp_value`,`unit`,`time`,`memory`,`flag`,`lat`,`lng`,`status`,`isPending`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, SensorTempTime sensorTempTime) {
                supportSQLiteStatement.bindLong(1, (long) sensorTempTime.getId());
                supportSQLiteStatement.bindLong(2, (long) sensorTempTime.getSensor_id());
                supportSQLiteStatement.bindLong(3, (long) sensorTempTime.getAssets_id());
                if (sensorTempTime.getAssets_name() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, sensorTempTime.getAssets_name());
                }
                supportSQLiteStatement.bindDouble(5, (double) sensorTempTime.getTemp_value());
                if (sensorTempTime.getUnit() == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, sensorTempTime.getUnit());
                }
                if (sensorTempTime.getTime() == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindString(7, sensorTempTime.getTime());
                }
                supportSQLiteStatement.bindLong(8, sensorTempTime.isTempFromMemory() ? 1 : 0);
                supportSQLiteStatement.bindLong(9, sensorTempTime.isFlag() ? 1 : 0);
                if (sensorTempTime.getLat() == null) {
                    supportSQLiteStatement.bindNull(10);
                } else {
                    supportSQLiteStatement.bindString(10, sensorTempTime.getLat());
                }
                if (sensorTempTime.getLng() == null) {
                    supportSQLiteStatement.bindNull(11);
                } else {
                    supportSQLiteStatement.bindString(11, sensorTempTime.getLng());
                }
                if (sensorTempTime.getStatus() == null) {
                    supportSQLiteStatement.bindNull(12);
                } else {
                    supportSQLiteStatement.bindString(12, sensorTempTime.getStatus());
                }
                supportSQLiteStatement.bindLong(13, sensorTempTime.isUploadPending() ? 1 : 0);
            }
        };
        this.__preparedStmtOfDeleteSensorTempById = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM sensortemptime WHERE id = ?";
            }
        };
    }

    public void insert(SensorTempTime sensorTempTime) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfSensorTempTime.insert(sensorTempTime);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteSensorTempById(int i) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOfDeleteSensorTempById.acquire();
        acquire.bindLong(1, (long) i);
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDeleteSensorTempById.release(acquire);
        }
    }

    public Maybe<List<SensorTempTime>> getAllSensorTemp() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM sensortemptime", 0);
        return Maybe.fromCallable(new Callable<List<SensorTempTime>>() {
            public List<SensorTempTime> call() throws Exception {
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                Cursor query = DBUtil.query(SensorTempTimeDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_assets_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "memory");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "isPending");
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        SensorTempTime sensorTempTime = new SensorTempTime();
                        ArrayList arrayList2 = arrayList;
                        sensorTempTime.setId(query.getInt(columnIndexOrThrow));
                        sensorTempTime.setSensor_id(query.getInt(columnIndexOrThrow2));
                        sensorTempTime.setAssets_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        sensorTempTime.setAssets_name(str);
                        sensorTempTime.setTemp_value(query.getFloat(columnIndexOrThrow5));
                        if (query.isNull(columnIndexOrThrow6)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow6);
                        }
                        sensorTempTime.setUnit(str2);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow7);
                        }
                        sensorTempTime.setTime(str3);
                        sensorTempTime.setTempFromMemory(query.getInt(columnIndexOrThrow8) != 0);
                        sensorTempTime.setFlag(query.getInt(columnIndexOrThrow9) != 0);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow10);
                        }
                        sensorTempTime.setLat(str4);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow11);
                        }
                        sensorTempTime.setLng(str5);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow12);
                        }
                        sensorTempTime.setStatus(str6);
                        sensorTempTime.setUploadPending(query.getInt(columnIndexOrThrow13) != 0);
                        arrayList = arrayList2;
                        arrayList.add(sensorTempTime);
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

    public Flowable<List<SensorTempTime>> getSensorTempById(int i) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM sensortemptime where ble_sensor_id=?", 1);
        acquire.bindLong(1, (long) i);
        return RxRoom.createFlowable(this.__db, false, new String[]{"sensortemptime"}, new Callable<List<SensorTempTime>>() {
            public List<SensorTempTime> call() throws Exception {
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                Cursor query = DBUtil.query(SensorTempTimeDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_assets_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "memory");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "isPending");
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        SensorTempTime sensorTempTime = new SensorTempTime();
                        ArrayList arrayList2 = arrayList;
                        sensorTempTime.setId(query.getInt(columnIndexOrThrow));
                        sensorTempTime.setSensor_id(query.getInt(columnIndexOrThrow2));
                        sensorTempTime.setAssets_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        sensorTempTime.setAssets_name(str);
                        sensorTempTime.setTemp_value(query.getFloat(columnIndexOrThrow5));
                        if (query.isNull(columnIndexOrThrow6)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow6);
                        }
                        sensorTempTime.setUnit(str2);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow7);
                        }
                        sensorTempTime.setTime(str3);
                        sensorTempTime.setTempFromMemory(query.getInt(columnIndexOrThrow8) != 0);
                        sensorTempTime.setFlag(query.getInt(columnIndexOrThrow9) != 0);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow10);
                        }
                        sensorTempTime.setLat(str4);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow11);
                        }
                        sensorTempTime.setLng(str5);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow12);
                        }
                        sensorTempTime.setStatus(str6);
                        sensorTempTime.setUploadPending(query.getInt(columnIndexOrThrow13) != 0);
                        arrayList = arrayList2;
                        arrayList.add(sensorTempTime);
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

    public Flowable<SensorTempTime> getSingleSensorTempById(int i) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM sensortemptime where ble_sensor_id=?", 1);
        acquire.bindLong(1, (long) i);
        return RxRoom.createFlowable(this.__db, false, new String[]{"sensortemptime"}, new Callable<SensorTempTime>() {
            public SensorTempTime call() throws Exception {
                SensorTempTime sensorTempTime;
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                Cursor query = DBUtil.query(SensorTempTimeDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_assets_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "memory");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "isPending");
                    if (query.moveToFirst()) {
                        sensorTempTime = new SensorTempTime();
                        sensorTempTime.setId(query.getInt(columnIndexOrThrow));
                        sensorTempTime.setSensor_id(query.getInt(columnIndexOrThrow2));
                        sensorTempTime.setAssets_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        sensorTempTime.setAssets_name(str);
                        sensorTempTime.setTemp_value(query.getFloat(columnIndexOrThrow5));
                        if (query.isNull(columnIndexOrThrow6)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow6);
                        }
                        sensorTempTime.setUnit(str2);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow7);
                        }
                        sensorTempTime.setTime(str3);
                        sensorTempTime.setTempFromMemory(query.getInt(columnIndexOrThrow8) != 0);
                        sensorTempTime.setFlag(query.getInt(columnIndexOrThrow9) != 0);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow10);
                        }
                        sensorTempTime.setLat(str4);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow11);
                        }
                        sensorTempTime.setLng(str5);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow12);
                        }
                        sensorTempTime.setStatus(str6);
                        sensorTempTime.setUploadPending(query.getInt(columnIndexOrThrow13) != 0);
                    } else {
                        sensorTempTime = null;
                    }
                    return sensorTempTime;
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

    public Flowable<List<SensorTempTime>> getSingleSensorTempByFlag(boolean z) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM sensortemptime where flag=?", 1);
        acquire.bindLong(1, z ? 1 : 0);
        return RxRoom.createFlowable(this.__db, false, new String[]{"sensortemptime"}, new Callable<List<SensorTempTime>>() {
            public List<SensorTempTime> call() throws Exception {
                String str;
                String str2;
                String str3;
                String str4;
                String str5;
                String str6;
                Cursor query = DBUtil.query(SensorTempTimeDao_Impl.this.__db, acquire, false, (CancellationSignal) null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "ble_sensor_id");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "ble_assets_id");
                    int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "assets_name");
                    int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "temp_value");
                    int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "unit");
                    int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "time");
                    int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "memory");
                    int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "flag");
                    int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "lat");
                    int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "lng");
                    int columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(query, "status");
                    int columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(query, "isPending");
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        SensorTempTime sensorTempTime = new SensorTempTime();
                        ArrayList arrayList2 = arrayList;
                        sensorTempTime.setId(query.getInt(columnIndexOrThrow));
                        sensorTempTime.setSensor_id(query.getInt(columnIndexOrThrow2));
                        sensorTempTime.setAssets_id(query.getInt(columnIndexOrThrow3));
                        if (query.isNull(columnIndexOrThrow4)) {
                            str = null;
                        } else {
                            str = query.getString(columnIndexOrThrow4);
                        }
                        sensorTempTime.setAssets_name(str);
                        sensorTempTime.setTemp_value(query.getFloat(columnIndexOrThrow5));
                        if (query.isNull(columnIndexOrThrow6)) {
                            str2 = null;
                        } else {
                            str2 = query.getString(columnIndexOrThrow6);
                        }
                        sensorTempTime.setUnit(str2);
                        if (query.isNull(columnIndexOrThrow7)) {
                            str3 = null;
                        } else {
                            str3 = query.getString(columnIndexOrThrow7);
                        }
                        sensorTempTime.setTime(str3);
                        sensorTempTime.setTempFromMemory(query.getInt(columnIndexOrThrow8) != 0);
                        sensorTempTime.setFlag(query.getInt(columnIndexOrThrow9) != 0);
                        if (query.isNull(columnIndexOrThrow10)) {
                            str4 = null;
                        } else {
                            str4 = query.getString(columnIndexOrThrow10);
                        }
                        sensorTempTime.setLat(str4);
                        if (query.isNull(columnIndexOrThrow11)) {
                            str5 = null;
                        } else {
                            str5 = query.getString(columnIndexOrThrow11);
                        }
                        sensorTempTime.setLng(str5);
                        if (query.isNull(columnIndexOrThrow12)) {
                            str6 = null;
                        } else {
                            str6 = query.getString(columnIndexOrThrow12);
                        }
                        sensorTempTime.setStatus(str6);
                        sensorTempTime.setUploadPending(query.getInt(columnIndexOrThrow13) != 0);
                        arrayList = arrayList2;
                        arrayList.add(sensorTempTime);
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

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
