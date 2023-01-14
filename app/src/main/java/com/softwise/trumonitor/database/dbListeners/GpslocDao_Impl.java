package com.softwise.trumonitor.database.dbListeners;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;

import com.softwise.trumonitor.database.gpsloc;

import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;


public final class GpslocDao_Impl implements GpslocDao {
    /* access modifiers changed from: private */
    public final RoomDatabase __db;
    private final EntityInsertionAdapter<gpsloc> __insertionAdapterOfgpsloc;

    @SuppressLint("RestrictedApi")
    public GpslocDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfgpsloc = new EntityInsertionAdapter<gpsloc>(roomDatabase) {
          @Override
            public String createQuery() {
                return "INSERT OR ABORT INTO `gpsloc` (`gpsloc_id`,`user_id`,`latitude`,`longitude`) VALUES (nullif(?, 0),?,?,?)";
            }

            @Override
            public void bind(SupportSQLiteStatement supportSQLiteStatement, gpsloc gpsloc) {
                supportSQLiteStatement.bindLong(1, (long) gpsloc.getGpsloc_id());
                supportSQLiteStatement.bindLong(2, (long) gpsloc.getUser_id());
                if (gpsloc.getLatitude() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, gpsloc.getLatitude());
                }
                if (gpsloc.getLongitude() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, gpsloc.getLongitude());
                }
            }
        };
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void insert(gpsloc gpsloc) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfgpsloc.insert(gpsloc);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void update(gpsloc gpsloc) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfgpsloc.insert(gpsloc);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public List<gpsloc> getAllGpsLoc() {
        String str;
        String str2;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM gpsloc", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false, (CancellationSignal) null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "gpsloc_id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "user_id");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "latitude");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "longitude");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                gpsloc gpsloc = new gpsloc();
                gpsloc.setGpsloc_id(query.getInt(columnIndexOrThrow));
                gpsloc.setUser_id(query.getInt(columnIndexOrThrow2));
                if (query.isNull(columnIndexOrThrow3)) {
                    str = null;
                } else {
                    str = query.getString(columnIndexOrThrow3);
                }
                gpsloc.setLatitude(str);
                if (query.isNull(columnIndexOrThrow4)) {
                    str2 = null;
                } else {
                    str2 = query.getString(columnIndexOrThrow4);
                }
                gpsloc.setLongitude(str2);
                arrayList.add(gpsloc);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @SuppressLint("RestrictedApi")
    public Maybe<gpsloc> getGpsLocByUserId(int i) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM gpsloc where user_id =?", 1);
        acquire.bindLong(1, (long) i);
        return Maybe.fromCallable(new Callable<gpsloc>() {
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: softwise.mechatronics.truBlueMonitor.database.gpsloc} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.String} */
            /* JADX WARNING: type inference failed for: r3v0 */
            /* JADX WARNING: type inference failed for: r3v3 */
            /* JADX WARNING: type inference failed for: r3v5 */
            /* JADX WARNING: Multi-variable type inference failed */
            /* Code decompiled incorrectly, please refer to instructions dump. */
           @Override
            public gpsloc call() throws Exception {
                /*
                    r7 = this;
                    softwise.mechatronics.truBlueMonitor.database.dbListeners.GpslocDao_Impl r0 = softwise.mechatronics.truBlueMonitor.database.dbListeners.GpslocDao_Impl.this
                    androidx.room.RoomDatabase r0 = r0.__db
                    androidx.room.RoomSQLiteQuery r1 = r0
                    r2 = 0
                    r3 = 0
                    android.database.Cursor r0 = androidx.room.util.DBUtil.query(r0, r1, r2, r3)
                    java.lang.String r1 = "gpsloc_id"
                    int r1 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r1)     // Catch:{ all -> 0x0061 }
                    java.lang.String r2 = "user_id"
                    int r2 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r2)     // Catch:{ all -> 0x0061 }
                    java.lang.String r4 = "latitude"
                    int r4 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r4)     // Catch:{ all -> 0x0061 }
                    java.lang.String r5 = "longitude"
                    int r5 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r0, r5)     // Catch:{ all -> 0x0061 }
                    boolean r6 = r0.moveToFirst()     // Catch:{ all -> 0x0061 }
                    if (r6 == 0) goto L_0x005d
                    softwise.mechatronics.truBlueMonitor.database.gpsloc r6 = new softwise.mechatronics.truBlueMonitor.database.gpsloc     // Catch:{ all -> 0x0061 }
                    r6.<init>()     // Catch:{ all -> 0x0061 }
                    int r1 = r0.getInt(r1)     // Catch:{ all -> 0x0061 }
                    r6.setGpsloc_id(r1)     // Catch:{ all -> 0x0061 }
                    int r1 = r0.getInt(r2)     // Catch:{ all -> 0x0061 }
                    r6.setUser_id(r1)     // Catch:{ all -> 0x0061 }
                    boolean r1 = r0.isNull(r4)     // Catch:{ all -> 0x0061 }
                    if (r1 == 0) goto L_0x0047
                    r1 = r3
                    goto L_0x004b
                L_0x0047:
                    java.lang.String r1 = r0.getString(r4)     // Catch:{ all -> 0x0061 }
                L_0x004b:
                    r6.setLatitude(r1)     // Catch:{ all -> 0x0061 }
                    boolean r1 = r0.isNull(r5)     // Catch:{ all -> 0x0061 }
                    if (r1 == 0) goto L_0x0055
                    goto L_0x0059
                L_0x0055:
                    java.lang.String r3 = r0.getString(r5)     // Catch:{ all -> 0x0061 }
                L_0x0059:
                    r6.setLongitude(r3)     // Catch:{ all -> 0x0061 }
                    r3 = r6
                L_0x005d:
                    r0.close()
                    return r3
                L_0x0061:
                    r1 = move-exception
                    r0.close()
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: softwise.mechatronics.truBlueMonitor.database.dbListeners.GpslocDao_Impl.AnonymousClass2.call():softwise.mechatronics.truBlueMonitor.database.gpsloc");
            }

            /* access modifiers changed from: protected */
            @SuppressLint("RestrictedApi")
            @Override
            public void finalize() {
                acquire.release();
            }
        });
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
