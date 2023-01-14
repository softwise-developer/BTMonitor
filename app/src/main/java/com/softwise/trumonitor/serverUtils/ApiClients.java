package com.softwise.trumonitor.serverUtils;

import android.util.Log;

import com.softwise.trumonitor.utils.BluetoothConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClients {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(boolean z) {
        String str;
        if (z) {
            str = BluetoothConstants.BASE_URL_UPLOAD;
        } else {
            str = BluetoothConstants.BASE_URL;
        }
        if (retrofit == null) {
            Log.e("Base url", str);
            retrofit = new Retrofit.Builder().baseUrl(str).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        } else if (!z) {
            retrofit = new Retrofit.Builder().baseUrl(str).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
        Log.e("URL", String.valueOf(retrofit.baseUrl().url()));
        return retrofit;
    }

    public static Retrofit getRetrofitInstanceForUpload(boolean z) {
        retrofit = null;
        String str = BluetoothConstants.BASE_URL_UPLOAD;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(str).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        }
        return retrofit;
    }
}
