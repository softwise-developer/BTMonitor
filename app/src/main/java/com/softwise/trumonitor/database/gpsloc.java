package com.softwise.trumonitor.database;

public class gpsloc {
    private int gpsloc_id;
    private String latitude;
    private String longitude;
    private int user_id;

    public int getGpsloc_id() {
        return this.gpsloc_id;
    }

    public void setGpsloc_id(int i) {
        this.gpsloc_id = i;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int i) {
        this.user_id = i;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }
}
