package com.softwise.trumonitor.implementer;

import android.content.Context;
import android.util.Log;

import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.listeners.IObserveDataListener;
import com.softwise.trumonitor.models.AssetAndSensorInfo;
import com.softwise.trumonitor.models.RefreshTokenResponse;
import com.softwise.trumonitor.models.Sensor;
import com.softwise.trumonitor.models.SensorIds;
import com.softwise.trumonitor.serverUtils.ApiClients;
import com.softwise.trumonitor.serverUtils.ServiceListeners.APIService;
import com.softwise.trumonitor.utils.BluetoothConstants;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SensorPresenter {
    APIService apiService;
    /* access modifiers changed from: private */
    public IObserveDataListener mIObserveDataListener;

    public SensorPresenter(Context context, IObserveDataListener iObserveDataListener) {
        this.mIObserveDataListener = iObserveDataListener;
        intPresenters(context);
    }

    public SensorPresenter(Context context) {
        intPresenters(context);
    }

    public void saveUpdateSensorLevelToServer(final Context context, Sensor sensor, final IBooleanListener iBooleanListener) {
        String valueOf = String.valueOf(SPTrueTemp.getUserOrg(context));
        String valueOf2 = String.valueOf(SPTrueTemp.getUserId(context));
        String userLevel = SPTrueTemp.getUserLevel(context);
        String token = SPTrueTemp.getToken(context);
        APIService aPIService = this.apiService;
        aPIService.setUpAssetsData("Bearer " + token, BluetoothConstants.CONTENT_TYPE, valueOf, valueOf2, userLevel, sensor)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable th) {
                if ("HTTP 401 Unauthorized".equals(th.getMessage())) {
                    SensorPresenter.this.callRefreshToken(context, (SensorIds) null);
                } else {
                    iBooleanListener.callBack(false);
                }
            }

            @Override
            public void onNext(JsonObject jsonObject) {
                if (jsonObject != null) {
                    iBooleanListener.callBack(true);
                }
            }
        });
    }

    private void intPresenters(Context context) {
        this.apiService = (APIService) ApiClients.getRetrofitInstance(false).create(APIService.class);
    }

    public void sendAssetAndSensorToServer(final Context context, final SensorIds sensorIds) {
        String valueOf = String.valueOf(SPTrueTemp.getToken(context));
        String valueOf2 = String.valueOf(SPTrueTemp.getUserOrg(context));
        String valueOf3 = String.valueOf(SPTrueTemp.getUserId(context));
        String userLevel = SPTrueTemp.getUserLevel(context);
        APIService aPIService = this.apiService;
        aPIService.getAssetsData("Bearer " + valueOf, BluetoothConstants.CONTENT_TYPE, valueOf2, valueOf3, userLevel, sensorIds).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AssetAndSensorInfo>() {
            @Override
            public void onCompleted() {
                SensorPresenter.this.mIObserveDataListener.dataOnComplete();
            }

            @Override
            public void onError(Throwable th) {
                th.printStackTrace();
                SensorPresenter.this.mIObserveDataListener.errorException(th);
                if ("HTTP 401 Unauthorized".equals(th.getMessage())) {
                    SensorPresenter.this.callRefreshToken(context, sensorIds);
                }
            }

            @Override
            public void onNext(AssetAndSensorInfo assetAndSensorInfo) {
                SensorPresenter.this.mIObserveDataListener.nextDataLoad(assetAndSensorInfo);
            }
        });
    }

    public void callRefreshToken(final Context context, final SensorIds sensorIds) {
        this.apiService.refreshToken(Integer.parseInt(SPTrueTemp.getUserId(context))).enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable th) {
            }

            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response != null) {
                    SPTrueTemp.saveToken(context, response.body().getMessage());
                    if (sensorIds != null) {
                        SensorPresenter.this.sendAssetAndSensorToServer(context, sensorIds);
                    }
                }
            }
        });
    }

    public void uploadData(String str, JsonArray jsonArray, final IBooleanListener iBooleanListener) {
        Log.e("Upload data ", " ");
        ((APIService) ApiClients.getRetrofitInstanceForUpload(true).create(APIService.class))
                .uploadTempData(BluetoothConstants.CONTENT_TYPE, jsonArray).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable th) {
                th.printStackTrace();
                iBooleanListener.callBack(false);
            }

            @Override
            public void onNext(JsonObject jsonObject) {
                Log.e("UploadMemoryData", jsonObject.get("message").toString());
                iBooleanListener.callBack(true);
            }
        });
    }

    public void logout(final Context context, int i, final IBooleanListener iBooleanListener) {
        int parseInt = Integer.parseInt(SPTrueTemp.getUserId(context));
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ble_asset_id", (Number) Integer.valueOf(i));
        jsonObject.addProperty("user_id", (Number) Integer.valueOf(parseInt));
        try {
            this.apiService.refreshToken(parseInt).enqueue(new Callback<RefreshTokenResponse>() {
                @Override
                public void onFailure(Call<RefreshTokenResponse> call, Throwable th) {
                }

                @Override
                public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                    if (response != null) {
                        String message = response.body().getMessage();
                        APIService aPIService = SensorPresenter.this.apiService;
                        aPIService.logoutFromServer("Bearer " + message, BluetoothConstants.CONTENT_TYPE, jsonObject).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable th) {
                                th.printStackTrace();
                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                if ("true".equals(jsonObject.get(FirebaseAnalytics.Param.SUCCESS).toString())) {
                                    SPTrueTemp.saveToken(context, (String) null);
                                    iBooleanListener.callBack(true);
                                    return;
                                }
                                iBooleanListener.callBack(false);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deallocateSensorFromAsset(Context context, int i, final IBooleanListener iBooleanListener) {
        String valueOf = String.valueOf(SPTrueTemp.getUserOrg(context));
        String valueOf2 = String.valueOf(SPTrueTemp.getUserId(context));
        String userLevel = SPTrueTemp.getUserLevel(context);
        String token = SPTrueTemp.getToken(context);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ble_asset_id", (Number) Integer.valueOf(i));
        ((APIService) ApiClients.getRetrofitInstance(false).create(APIService.class)).deallocateSensor("Bearer " + token, BluetoothConstants.CONTENT_TYPE, valueOf, valueOf2, userLevel, jsonObject).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JsonObject>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable th) {
                Log.e("Deallocate error ", th.getMessage());
            }

            @Override
            public void onNext(JsonObject jsonObject) {
                boolean asBoolean = jsonObject.get(FirebaseAnalytics.Param.SUCCESS).getAsBoolean();
                Log.e("Deallocate response ", String.valueOf(jsonObject.get("message")).replaceAll("^\"|\"$", ""));
                if (asBoolean) {
                    iBooleanListener.callBack(true);
                } else {
                    iBooleanListener.callBack(false);
                }
            }
        });
    }
}
