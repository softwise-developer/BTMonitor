package com.softwise.trumonitor.utils;

import android.content.Context;

import com.softwise.trumonitor.helper.HelperPreference;


public class SPTrueTemp {
    public static void saveFirstTimeLaunch(Context context, Boolean bool) {
        HelperPreference.saveBoolean(context, "first_launch", bool);
    }

    public static boolean getIsFirstTimeLaunch(Context context) {
        return HelperPreference.getBoolean(context, "first_launch").booleanValue();
    }

    public static void saveSensorId(Context context, String str) {
        HelperPreference.saveString(context, BluetoothConstants.SENSOR_ID, str);
    }

    public static String getSensorId(Context context) {
        return HelperPreference.getString(context, BluetoothConstants.SENSOR_ID);
    }
    public static void saveAssetsId(Context context, String str) {
        HelperPreference.saveString(context, BluetoothConstants.ASSETS_ID, str);
    }

    public static String getAssetsId(Context context) {
        return HelperPreference.getString(context, BluetoothConstants.ASSETS_ID);
    }

    public static void saveLoginStatus(Context context, Boolean bool) {
        HelperPreference.saveBoolean(context, "Login_status", bool);
    }

    public static boolean getLoginStatus(Context context) {
        return HelperPreference.getBoolean(context, "Login_status").booleanValue();
    }

    public static void saveConnectedMacAddress(Context context, String str) {
        HelperPreference.saveString(context, "MAC", str);
    }

    public static String getConnectedMacAddress(Context context) {
        return HelperPreference.getString(context, "MAC");
    }

    public static void saveConnectedBluetoothName(Context context, String str) {
        HelperPreference.saveString(context, "b_name", str);
    }

    public static String getConnectedBluetooth(Context context) {
        return HelperPreference.getString(context, "b_name");
    }

    public static void saveUserId(Context context, String str) {
        HelperPreference.saveString(context, "user_id", str);
    }

    public static String getUserId(Context context) {
        return HelperPreference.getString(context, "user_id");
    }

    public static void saveUserEmail(Context context, String str) {
        HelperPreference.saveString(context, "user_email", str);
    }

    public static String getUserEmail(Context context) {
        return HelperPreference.getString(context, "user_email");
    }

    public static void saveUserMobile(Context context, String str) {
        HelperPreference.saveString(context, "user_mobile", str);
    }

    public static String getUserMobile(Context context) {
        return HelperPreference.getString(context, "user_mobile");
    }

    public static void saveUserLevel(Context context, String str) {
        HelperPreference.saveString(context, "user_level", str);
    }

    public static String getUserLevel(Context context) {
        return HelperPreference.getString(context, "user_level");
    }

    public static void saveUserOrg(Context context, int i) {
        HelperPreference.saveInt(context, "org", i);
    }

    public static Integer getUserOrg(Context context) {
        return HelperPreference.getInteger(context, "org");
    }

    public static void saveToken(Context context, String str) {
        HelperPreference.saveString(context, "token", str);
    }

    public static String getToken(Context context) {
        return HelperPreference.getString(context, "token");
    }

    public static void saveLatitude(Context context, String str) {
        HelperPreference.saveString(context, "lat", str);
    }

    public static String getLatitude(Context context) {
        return HelperPreference.getString(context, "lat");
    }

    public static void saveLongitude(Context context, String str) {
        HelperPreference.saveString(context, "lng", str);
    }

    public static String getLongitude(Context context) {
        return HelperPreference.getString(context, "lng");
    }

    public static void saveBatteryLevel(Context context, String str) {
        HelperPreference.saveString(context, "battery", str);
    }

    public static String getBatteryLevel(Context context) {
        return HelperPreference.getString(context, "battery");
    }

    public static void saveCallingType(Context context, String str) {
        HelperPreference.saveString(context, "callFrom", str);
    }

    public static String getCallingType(Context context) {
        return HelperPreference.getString(context, "callFrom");
    }

    public static void clearSharedPref(Context context) {
        saveLoginStatus(context, false);
        saveUserId(context, (String) null);
        saveUserMobile(context, (String) null);
        saveUserLevel(context, (String) null);
        saveUserEmail(context, (String) null);
        saveUserOrg(context, 0);
        saveConnectedMacAddress(context, (String) null);
        saveConnectedBluetoothName(context, (String) null);
        saveToken(context, (String) null);
        saveLatitude(context, (String) null);
        saveLongitude(context, (String) null);
        saveBatteryLevel(context, (String) null);
    }

    public static void clearConnectedAddress(Context context) {
        saveCallingType(context, (String) null);
        saveSensorId(context, (String) null);
        saveBatteryLevel(context, (String) null);
        saveLatitude(context, (String) null);
        saveLongitude(context, (String) null);
    }
}
