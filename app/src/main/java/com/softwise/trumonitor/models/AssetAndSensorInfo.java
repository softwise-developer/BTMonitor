package com.softwise.trumonitor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AssetAndSensorInfo {
    @SerializedName("ble_asset_id")
    @Expose
    private Integer assetId;
    @SerializedName("assetName")
    @Expose
    private String assetName;
    @SerializedName("sensors")
    @Expose
    private List<Sensor> sensors = null;

    public Integer getAssetId() {
        return this.assetId;
    }

    public void setAssetId(Integer num) {
        this.assetId = num;
    }

    public String getAssetName() {
        return this.assetName;
    }

    public void setAssetName(String str) {
        this.assetName = str;
    }

    public List<Sensor> getSensors() {
        return this.sensors;
    }

    public String toString() {
        return "AssetAndSensorInfo{assetId=" + this.assetId + ", assetName='" + this.assetName + '\'' + ", sensors=" + this.sensors + '}';
    }

    public void setSensors(List<Sensor> list) {
        this.sensors = list;
    }
}
