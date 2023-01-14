package com.softwise.trumonitor.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class HelperPreference {
    public static final String PREFS_NAME = "BluetoothApp";

    public static void saveString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getString(Context context, String str) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(str, (String) null);
    }

    public static void saveInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static Integer getInteger(Context context, String str) {
        return Integer.valueOf(context.getSharedPreferences(PREFS_NAME, 0).getInt(str, 0));
    }

    public static void saveLong(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static Long getLong(Context context, String str) {
        return Long.valueOf(context.getSharedPreferences(PREFS_NAME, 0).getLong(str, 0));
    }

    public static void saveFloat(Context context, String str, Float f) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putFloat(str, f.floatValue());
        edit.apply();
    }

    public static Float getFloat(Context context, String str) {
        return Float.valueOf(context.getSharedPreferences(PREFS_NAME, 0).getFloat(str, 0.0f));
    }

    public static void saveBoolean(Context context, String str, Boolean bool) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.putBoolean(str, bool.booleanValue());
        edit.apply();
    }

    public static Boolean getBoolean(Context context, String str) {
        return Boolean.valueOf(context.getSharedPreferences(PREFS_NAME, 0).getBoolean(str, false));
    }

    public static boolean clearSharedPreference(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.clear();
        return edit.commit();
    }

    public static void removeValue(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        edit.remove(str);
        edit.apply();
    }
}
