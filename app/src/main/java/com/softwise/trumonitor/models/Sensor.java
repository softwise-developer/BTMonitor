package com.softwise.trumonitor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sensor {
    @SerializedName("alarm_high")
    @Expose
    private Integer alarmHigh;
    @SerializedName("alarm_low")
    @Expose
    private Integer alarmLow;
    @SerializedName("ble_sensor_id")
    @Expose
    private Integer sensorId;
    @SerializedName("sensor_name")
    @Expose
    private String sensor_name;
    private String temp;
    private String unit;
    @SerializedName("update_frequency")
    @Expose
    private String updateFrequency;
    @SerializedName("warning_high")
    @Expose
    private Integer warningHigh;
    @SerializedName("warning_low")
    @Expose
    private Integer warningLow;

    public Integer getSensorId() {
        return this.sensorId;
    }

    public void setSensorId(Integer num) {
        this.sensorId = num;
    }

    public Integer getAlarmLow() {
        return this.alarmLow;
    }

    public void setAlarmLow(Integer num) {
        this.alarmLow = num;
    }

    public Integer getAlarmHigh() {
        return this.alarmHigh;
    }

    public void setAlarmHigh(Integer num) {
        this.alarmHigh = num;
    }

    public Integer getWarningLow() {
        return this.warningLow;
    }

    public void setWarningLow(Integer num) {
        this.warningLow = num;
    }

    public Integer getWarningHigh() {
        return this.warningHigh;
    }

    public void setWarningHigh(Integer num) {
        this.warningHigh = num;
    }

    public String getUpdateFrequency() {
        return this.updateFrequency;
    }

    public void setUpdateFrequency(String str) {
        this.updateFrequency = str;
    }

    public String getTemp() {
        return this.temp;
    }

    public void setTemp(String str) {
        this.temp = str;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public String toString() {
        return "Sensor{sensorId=" + this.sensorId + ", sensor_name='" + this.sensor_name + '\'' + ", alarmLow=" + this.alarmLow + ", warningLow=" + this.warningLow + ", warningHigh=" + this.warningHigh + ", alarmHigh=" + this.alarmHigh + ", updateFrequency='" + this.updateFrequency + '\'' + ", temp='" + this.temp + '\'' + ", unit='" + this.unit + '\'' + '}';
    }

    public String getSensor_name() {
        return this.sensor_name;
    }

    public void setSensor_name(String str) {
        this.sensor_name = str;
    }
}
