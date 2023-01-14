package com.softwise.trumonitor.utils;

public class BluetoothConstants {
    public static final String ALARM_UPDATE_FREQUENCY = "00:01:00";
    public static final String BASE_URL = "https://trumonitor.tech";
    public static final String BASE_URL_UPLOAD = "http://157.245.96.191:8888";
    public static final String BEARER = "Bearer";
    public static final String CONTENT_TYPE = "application/json";
    public static final String INTENT_ACTION_DISCONNECT = "rx.android.Disconnect";
    public static final String INTENT_CLASS_MAIN_ACTIVITY = "rx.android.DeviceConnectionActivity";
    public static final String NOTIFICATION_CHANNEL = "rx.android.Channel";
    public static final int NOTIFICATION_ID_FOREGROUND_SERVICE = 8466503;
    public static final int NOTIFY_MANAGER_START_FOREGROUND_SERVICE = 1001;
    public static final String SENSOR_ID = "sensor_id";
    public static final String ASSETS_ID = "assets_id";
    public static final String SENSOR_NAME = "ble_sensor";
    static String debugUrl = "https://trumonitor.tech";
    static String debugUrlForUploadTemp = "http://157.245.96.191:8888";
    static String releaseUrl = "http://157.245.96.191";

    public static class ACTION {
        public static final String MAIN_ACTION = "test.action.main";
        public static final String START_ACTION = "test.action.start";
        public static final String STOP_ACTION = "test.action.stop";
    }

    public static class STATE_SERVICE {
        public static final int CONNECTED = 10;
        public static final int NOT_CONNECTED = 0;
    }
}
