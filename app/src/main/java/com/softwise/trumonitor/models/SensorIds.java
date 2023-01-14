package com.softwise.trumonitor.models;

public class SensorIds {
    private int ble_asset_id;
    private int[] sensor_ids;

    public SensorIds() {
    }

    public int[] getSensor_ids() {
        return this.sensor_ids;
    }

    public void setSensor_ids(int[] iArr) {
        this.sensor_ids = iArr;
    }

    public SensorIds(int i, int[] iArr) {
        this.ble_asset_id = i;
        this.sensor_ids = iArr;
    }

    public int getBle_asset_id() {
        return this.ble_asset_id;
    }

    public void setBle_asset_id(int i) {
        this.ble_asset_id = i;
    }
}
