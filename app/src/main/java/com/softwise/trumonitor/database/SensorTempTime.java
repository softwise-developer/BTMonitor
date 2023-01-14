package com.softwise.trumonitor.database;

public class SensorTempTime {
    private int assets_id;
    private String assets_name;
    private boolean flag;
    private int id;
    private boolean isUploadPending;
    private String lat;
    private String lng;
    private int sensor_id;
    private String status;
    private boolean tempFromMemory;
    private float temp_value;
    private String time;
    private String unit;

    public SensorTempTime() {
    }

    public SensorTempTime(String str, float f) {
        this.time = str;
        this.temp_value = f;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getSensor_id() {
        return this.sensor_id;
    }

    public void setSensor_id(int i) {
        this.sensor_id = i;
    }

    public float getTemp_value() {
        return this.temp_value;
    }

    public void setTemp_value(float f) {
        this.temp_value = f;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public boolean isTempFromMemory() {
        return this.tempFromMemory;
    }

    public void setTempFromMemory(boolean z) {
        this.tempFromMemory = z;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean z) {
        this.flag = z;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String str) {
        this.lat = str;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String str) {
        this.lng = str;
    }

    public String getAssets_name() {
        return this.assets_name;
    }

    public void setAssets_name(String str) {
        this.assets_name = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public int getAssets_id() {
        return this.assets_id;
    }

    public void setAssets_id(int i) {
        this.assets_id = i;
    }

    public boolean isUploadPending() {
        return this.isUploadPending;
    }

    public void setUploadPending(boolean z) {
        this.isUploadPending = z;
    }
}
