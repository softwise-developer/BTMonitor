package com.softwise.trumonitor.database;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import androidx.room.Room;

import com.softwise.trumonitor.database.dbListeners.DatabaseCallback;
import com.softwise.trumonitor.database.dbListeners.ILocationCallback;
import com.softwise.trumonitor.database.dbListeners.ISensorDataCallback;
import com.softwise.trumonitor.database.dbListeners.ISingleSensorData;
import com.softwise.trumonitor.database.dbListeners.ISingleSensorTempCallback;
import com.softwise.trumonitor.database.dbListeners.SensorDatabaseCallback;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;



public class DatabaseClient {
    public static final String DB_NAME = "sensor_db.db";
    private static DatabaseClient mInstance;
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    /* access modifiers changed from: private */
    public AppDatabase appDatabase;
    private Context mCtx;

    public DatabaseClient(Context context) {
        this.mCtx = context;
        this.appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        DatabaseClient databaseClient;
        synchronized (DatabaseClient.class) {
            if (mInstance == null) {
                mInstance = new DatabaseClient(context);
            }
            databaseClient = mInstance;
        }
        return databaseClient;
    }

    public AppDatabase getAppDatabase() {
        return this.appDatabase;
    }

    public void getAllSensor(final ISensorDataCallback iSensorDataCallback) {
        this.appDatabase.sensorDao().getAllSensorData(true).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<EntitySensor>>() {
            public void accept(List<EntitySensor> list) throws Exception {
                iSensorDataCallback.onLoadAllSensor(list);
            }
        });
    }

    public List<EntitySensor> getAllSensorData() {
        final List<EntitySensor>[] listArr = null;
        this.appDatabase.sensorDao().getAllSensor().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<EntitySensor>>() {
            public void accept(List<EntitySensor> list) throws Exception {
                listArr[0] = new ArrayList();
                listArr[0].addAll(list);
            }
        });
        Log.e("getAllSensorData", listArr[0].toString());
        return listArr[0];
    }

    public void getAndSaveEntitySensor(int i, final EntitySensor entitySensor, final SensorDatabaseCallback sensorDatabaseCallback) {
        final boolean[] zArr = {false};
        this.appDatabase.sensorDao().getSensorById(i).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EntitySensor>() {
            public void accept(EntitySensor entitySensor) throws Exception {
                if (entitySensor != null) {
                    zArr[0] = false;
                    return;
                }
                Log.e("entitySensorSave", entitySensor.toString());
                DatabaseClient.this.saveEntitySensor(entitySensor, new SensorDatabaseCallback() {
                    public void onSensorAdded() {
                        zArr[0] = true;
                        sensorDatabaseCallback.onSensorAdded();
                        Log.e("insidtitySensorSaveTrue", entitySensor.toString());
                    }

                    public void onSensorAddedFailed() {
                        zArr[0] = false;
                        sensorDatabaseCallback.onSensorAddedFailed();
                        Log.e("insidtitySensorSaveFal", entitySensor.toString());
                    }
                });
            }
        });
    }

    public EntitySensor getEntitySensor(int i) {
        Log.e("getinsidEntitySensor", Integer.toString(i));
        final EntitySensor[] entitySensorArr = {null};
        this.appDatabase.sensorDao().getSensorById(i).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EntitySensor>() {
            public void accept(EntitySensor entitySensor) throws Exception {
                entitySensorArr[0] = entitySensor;
                Log.e("getinsidEntity", entitySensor.toString());
            }
        });
        Log.e("", entitySensorArr[0].toString());
        return entitySensorArr[0];
    }

    public void getEntitySensorData(int i, final ISingleSensorData iSingleSensorData) {
        Log.e("getEntitySensorData", Integer.toString(i));
        this.appDatabase.sensorDao().getSensorById(i).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EntitySensor>() {
            public void accept(EntitySensor entitySensor) throws Exception {
                iSingleSensorData.onLoadSensor(entitySensor);
                Log.e("getEntitySensorEntity", entitySensor.toString());
            }
        });
    }

    public void saveEntitySensor(final EntitySensor entitySensor, final SensorDatabaseCallback sensorDatabaseCallback) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().insert(entitySensor);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onComplete() {
                sensorDatabaseCallback.onSensorAdded();
            }

            public void onError(Throwable th) {
                sensorDatabaseCallback.onSensorAddedFailed();
                th.printStackTrace();
            }
        });
    }

    public void saveEntitySensorList(final List<EntitySensor> list, final SensorDatabaseCallback sensorDatabaseCallback) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                for (EntitySensor entitySensor : list) {
                    Log.e("saveEntitySenInsertDB", entitySensor.toString());
                    DatabaseClient.this.appDatabase.sensorDao().insert(entitySensor);
                }
            }
        }).observeOn(AndroidSchedulers.from(Looper.getMainLooper())).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onComplete() {
                String access$100 = DatabaseClient.this.TAG;
                Log.e(access$100, "Data add in db " + ((EntitySensor) list.get(0)).getBle_sensor_id());
                sensorDatabaseCallback.onSensorAdded();
            }

            public void onError(Throwable th) {
                sensorDatabaseCallback.onSensorAddedFailed();
                th.printStackTrace();
            }
        });
    }

    public void addSensorData(final EntitySensor entitySensor) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().insert(entitySensor);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });
    }

    public void deleteSensoryId(final int i) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().deleteById(i);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
            }
        });
    }

    public void deleteSensor(final EntitySensor entitySensor) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().delete(entitySensor);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
            }
        });
    }

    public void updateSensorData(final EntitySensor entitySensor) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().update(entitySensor);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
                disposable.dispose();
            }

            public void onComplete() {
                String access$100 = DatabaseClient.this.TAG;
                Log.e(access$100, "Data update in db" + entitySensor.getBle_sensor_id() + " Frequency = " + entitySensor.getUpdate_frequency());
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });
    }

    public void updateEntitySensorData(final EntitySensor entitySensor) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().update(entitySensor);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
                disposable.dispose();
            }

            public void onComplete() {
                String access$100 = DatabaseClient.this.TAG;
                Log.e(access$100, "Data update in db" + entitySensor.getBle_sensor_id() + " Flag = " + entitySensor.isFlag());
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });
    }

    public void updateSensor(final EntitySensor entitySensor, final DatabaseCallback databaseCallback) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().update(entitySensor);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
                disposable.dispose();
            }

            public void onComplete() {
                databaseCallback.onSensorUpdated();
            }

            public void onError(Throwable th) {
                databaseCallback.onDataNotAvailable();
            }
        });
    }

    public void updateSensorTempUnit(final String str, final String str2, final int i) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().updateTemp(str, str2, i);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
                disposable.dispose();
            }

            public void onComplete() {
                Log.e("Sensor update", "Successfuly" + str + " id " + i);
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });
    }

    public void addLocation(final gpsloc gpsloc) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.gpslocDao().insert(gpsloc);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });
    }

    public void updateLocation(final gpsloc gpsloc) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.gpslocDao().update(gpsloc);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onError(Throwable th) {
                th.printStackTrace();
            }
        });
    }

    public void getLocation(int i, final ILocationCallback iLocationCallback) {
        this.appDatabase.gpslocDao().getGpsLocByUserId(i).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<gpsloc>() {
            public void accept(gpsloc gpsloc) throws Exception {
                iLocationCallback.getLocation(gpsloc);
            }
        });
    }

    public void addSensorTemp(final SensorTempTime sensorTempTime) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorTempTimeDao().insert(sensorTempTime);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onSubscribe(Disposable disposable) {
                Log.e("SensorTemp data", "add disposable");
            }

            public void onComplete() {
                Log.e("SensorTemp data", "add Complete");
            }

            public void onError(Throwable th) {
                Log.e("SensorTemp data error", "add " + th.getMessage());
            }
        });
    }

    public void getAllSensorTemp(final ISensorTempCallback iSensorTempCallback) {
        this.appDatabase.sensorTempTimeDao().getAllSensorTemp().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SensorTempTime>>() {
            public void accept(List<SensorTempTime> list) throws Exception {
                iSensorTempCallback.loadTemperature(list);
            }
        });
    }

    public void getSensorTemp(int i, final ISensorTempCallback iSensorTempCallback) {
        this.appDatabase.sensorTempTimeDao().getSensorTempById(i).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SensorTempTime>>() {
            public void accept(List<SensorTempTime> list) throws Exception {
                iSensorTempCallback.loadTemperature(list);
            }
        });
    }

    public void getSingleSensorTemp(int i, final ISingleSensorTempCallback iSingleSensorTempCallback) {
        this.appDatabase.sensorTempTimeDao().getSingleSensorTempById(i).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SensorTempTime>() {
            public void accept(SensorTempTime sensorTempTime) throws Exception {
                iSingleSensorTempCallback.onSensorTempLoad(sensorTempTime);
            }
        });
    }

    public void getSensorTempByFlag(boolean z, final ISensorTempCallback iSensorTempCallback) {
        this.appDatabase.sensorTempTimeDao().getSingleSensorTempByFlag(z).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SensorTempTime>>() {
            public void accept(List<SensorTempTime> list) throws Exception {
                iSensorTempCallback.loadTemperature(list);
            }
        });
    }

    public void deleteSensorTempId(final int i) {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorTempTimeDao().deleteSensorTempById(i);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
            }
        });
    }

    public void deleteSensorTable() {
        Completable.fromAction(new Action() {
            public void run() throws Exception {
                DatabaseClient.this.appDatabase.sensorDao().deleteSensorTable();
            }
        }).observeOn(AndroidSchedulers.from(Looper.getMainLooper())).subscribeOn(Schedulers.io()).subscribe((CompletableObserver) new CompletableObserver() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
            }
        });
    }
}
