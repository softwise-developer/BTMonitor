package com.softwise.trumonitor.serverUtils.ServiceListeners;

import com.softwise.trumonitor.models.AssetAndSensorInfo;
import com.softwise.trumonitor.models.LoginResponse;
import com.softwise.trumonitor.models.RefreshTokenResponse;
import com.softwise.trumonitor.models.Sensor;
import com.softwise.trumonitor.models.SensorIds;
import com.softwise.trumonitor.models.UserCredentials;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


public interface APIService {
    @POST("api/bluetooth/sensor/deallocate")
    Observable<JsonObject> deallocateSensor(@Header("Authorization") String str, @Header("Content-Type") String str2, @Header("org") String str3, @Header("user") String str4, @Header("level") String str5, @Body JsonObject jsonObject);

    @GET("api/bluetooth/asset/list/{org}")
    Observable<List<AssetAndSensorInfo>> getAllAssetsData(@Header("Authorization") String str, @Header("Content-Type") String str2, @Path("org") String str3);

    @POST("api/bluetooth/asset/allocate")
    Observable<AssetAndSensorInfo> getAssetsData(@Header("Authorization") String str, @Header("Content-Type") String str2, @Header("org") String str3, @Header("user") String str4, @Header("level") String str5, @Body SensorIds sensorIds);

    @GET("/sensor/lcdSensorSetup")
    Observable<int[]> getLcdSensorSetup(@Body int i);

    @POST("/api/auth/bluetooth/login")
    Observable<LoginResponse> login(@Header("Content-Type") String str, @Body UserCredentials userCredentials);

    @POST("api/bluetooth/logout")
    Observable<JsonObject> logoutFromServer(@Header("Authorization") String str, @Header("Content-Type") String str2, @Body JsonObject jsonObject);

    @GET("/api/auth/token/{operatorId}/regenerate")
    Call<RefreshTokenResponse> refreshToken(@Path("operatorId") int i);

    @POST("api/bluetooth/saveAsset")
    Observable<String> saveAssetsData(@Header("token") String str, @Body AssetAndSensorInfo assetAndSensorInfo);

    @POST("api/bluetooth/asset/setup")
    Observable<JsonObject> setUpAssetsData(@Header("Authorization") String str, @Header("Content-Type") String str2, @Header("org") String str3, @Header("user") String str4, @Header("level") String str5, @Body Sensor sensor);

    @POST("api/sensor/ble-data-new")
    Observable<JsonObject> uploadTempData(@Header("Content-Type") String str, @Body JsonArray jsonArray);
}
