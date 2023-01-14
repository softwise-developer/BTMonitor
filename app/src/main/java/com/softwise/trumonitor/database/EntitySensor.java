package com.softwise.trumonitor.database;

import android.os.Parcel;
import android.os.Parcelable;

public class EntitySensor implements Parcelable {
    public static final Creator<EntitySensor> CREATOR = new Creator<EntitySensor>() {
        public EntitySensor createFromParcel(Parcel parcel) {
            return new EntitySensor(parcel);
        }

        public EntitySensor[] newArray(int i) {
            return new EntitySensor[i];
        }
    };
    private int alarm_high;
    private int alarm_low;
    private int asset_id;
    private String assets_name;
    private int ble_sensor_id;
    private boolean flag;
    private int id;
    private String lat;
    private String lng;
    private String sensor_name;
    private String status;
    private String temp_value;
    private String time;
    private String unit;
    private String update_frequency;
    private int warning_high;
    private int warning_low;

    public int describeContents() {
        return 0;
    }

    protected EntitySensor(Parcel parcel) {
        this.id = parcel.readInt();
        this.ble_sensor_id = parcel.readInt();
        this.asset_id = parcel.readInt();
        this.sensor_name = parcel.readString();
        this.assets_name = parcel.readString();
        this.temp_value = parcel.readString();
        this.unit = parcel.readString();
        this.time = parcel.readString();
        this.status = parcel.readString();
        this.update_frequency = parcel.readString();
        this.lat = parcel.readString();
        this.lng = parcel.readString();
        this.flag = parcel.readByte() != 0;
        this.alarm_low = parcel.readInt();
        this.alarm_high = parcel.readInt();
        this.warning_low = parcel.readInt();
        this.warning_high = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.ble_sensor_id);
        parcel.writeInt(this.asset_id);
        parcel.writeString(this.sensor_name);
        parcel.writeString(this.assets_name);
        parcel.writeString(this.temp_value);
        parcel.writeString(this.unit);
        parcel.writeString(this.time);
        parcel.writeString(this.status);
        parcel.writeString(this.update_frequency);
        parcel.writeString(this.lat);
        parcel.writeString(this.lng);
        parcel.writeByte(this.flag ? (byte) 1 : 0);
        parcel.writeInt(this.alarm_low);
        parcel.writeInt(this.alarm_high);
        parcel.writeInt(this.warning_low);
        parcel.writeInt(this.warning_high);
    }

    public int getBle_sensor_id() {
        return this.ble_sensor_id;
    }

    public void setBle_sensor_id(int i) {
        this.ble_sensor_id = i;
    }

    public String getSensor_name() {
        return this.sensor_name;
    }

    public void setSensor_name(String str) {
        this.sensor_name = str;
    }

    public String getTemp_value() {
        return this.temp_value;
    }

    public void setTemp_value(String str) {
        this.temp_value = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public EntitySensor() {
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public String getUpdate_frequency() {
        return this.update_frequency;
    }

    public void setUpdate_frequency(String str) {
        this.update_frequency = str;
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

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean z) {
        this.flag = z;
    }

    public int getAlarm_low() {
        return this.alarm_low;
    }

    public void setAlarm_low(int i) {
        this.alarm_low = i;
    }

    public int getAlarm_high() {
        return this.alarm_high;
    }

    public void setAlarm_high(int i) {
        this.alarm_high = i;
    }

    public int getWarning_low() {
        return this.warning_low;
    }

    public void setWarning_low(int i) {
        this.warning_low = i;
    }

    public int getWarning_high() {
        return this.warning_high;
    }

    public void setWarning_high(int i) {
        this.warning_high = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getAssets_name() {
        return this.assets_name;
    }

    public void setAssets_name(String str) {
        this.assets_name = str;
    }

    public int getAsset_id() {
        return this.asset_id;
    }

    public String toString() {
        return "EntitySensor{id=" + this.id + ", ble_sensor_id=" + this.ble_sensor_id + ", asset_id=" + this.asset_id + ", sensor_name='" + this.sensor_name + '\'' + ", assets_name='" + this.assets_name + '\'' + ", temp_value='" + this.temp_value + '\'' + ", unit='" + this.unit + '\'' + ", time='" + this.time + '\'' + ", status='" + this.status + '\'' + ", update_frequency='" + this.update_frequency + '\'' + ", lat='" + this.lat + '\'' + ", lng='" + this.lng + '\'' + ", flag=" + this.flag + ", alarm_low=" + this.alarm_low + ", alarm_high=" + this.alarm_high + ", warning_low=" + this.warning_low + ", warning_high=" + this.warning_high + '}';
    }

    public void setAsset_id(int i) {
        this.asset_id = i;
    }
}
