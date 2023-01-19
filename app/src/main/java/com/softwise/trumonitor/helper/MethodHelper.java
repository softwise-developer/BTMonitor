package com.softwise.trumonitor.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.exifinterface.media.ExifInterface;
import com.softwise.trumonitor.R;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.database.SensorTempTime;
import com.softwise.trumonitor.models.AssetAndSensorInfo;
import com.softwise.trumonitor.models.LoginResponse;
import com.softwise.trumonitor.models.Sensor;
import com.softwise.trumonitor.models.SensorIds;
import com.softwise.trumonitor.utils.BluetoothConstants;
import com.softwise.trumonitor.utils.ConnectionUtils;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MethodHelper {
    private static AssetFileDescriptor afd;
    public static HashMap<Integer, Integer> cnt = new HashMap<>();
    public static int count = 0;
    public static int frequency = 0;
    /* access modifiers changed from: private */
    public static MediaPlayer mediaPlayer;
    public static boolean sendDataFlag = false;
    public static boolean startTimer = false;

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void jumpActivity(Context context, Class<? extends Activity> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static void saveUserDataInSP(Context context, LoginResponse loginResponse) {
        SPTrueTemp.saveToken(context, loginResponse.getAccessToken());
        SPTrueTemp.saveUserId(context, loginResponse.getUser().getOperatorId());
        SPTrueTemp.saveUserMobile(context, loginResponse.getUser().getMobile());
        SPTrueTemp.saveUserEmail(context, loginResponse.getUser().getEmail());
        SPTrueTemp.saveUserLevel(context, loginResponse.getUser().getLevel());
        SPTrueTemp.saveUserOrg(context, loginResponse.getUser().getOrgId().intValue());
    }

    public static String createSensorDataString(List<Sensor> list, int i) {
        StringBuilder sb = new StringBuilder();
        for (Sensor next : list) {
            Log.e("FreqensorDataString", next.getUpdateFrequency());
            Integer valueOf = Integer.valueOf(convertStringTimeToSec(next.getUpdateFrequency()));
            sb.append(next.getAlarmLow());
            sb.append(",");
            sb.append(next.getAlarmHigh());
            sb.append(",");
            sb.append(next.getWarningLow());
            sb.append(",");
            sb.append(next.getWarningHigh());
            sb.append(",");
            sb.append(valueOf);
            sb.append(",");
            sb.append(i);
            sb.append(",");
        }
        sb.append("alarm/warning level:");
        return sb.toString();
    }

    public static String checkAlarmWaningValue(float f, EntitySensor entitySensor) {
        String str = f <= ((float) entitySensor.getAlarm_low()) ? "alarm_low" : null;
        if (((float) entitySensor.getAlarm_low()) < f && f <= ((float) entitySensor.getWarning_low())) {
            str = "warning_low";
        }
        if (((float) entitySensor.getWarning_low()) < f && f < ((float) entitySensor.getWarning_high())) {
            str = "safe";
        }
        if (((float) entitySensor.getWarning_high()) <= f && f < ((float) entitySensor.getAlarm_high())) {
            str = "warning_high";
        }
        return f >= ((float) entitySensor.getAlarm_high()) ? "alarm_high" : str;
    }

    public static String sensorStatusVale(String str) {
        if (str == null) {
            return null;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 2087) {
            if (hashCode != 2091) {
                if (hashCode != 2643) {
                    if (hashCode != 2769) {
                        if (hashCode == 2773 && str.equals("WL")) {
                            c = 2;
                        }
                    } else if (str.equals("WH")) {
                        c = 3;
                    }
                } else if (str.equals("SF")) {
                    c = 4;
                }
            } else if (str.equals("AL")) {
                c = 0;
            }
        } else if (str.equals("AH")) {
            c = 1;
        }
        if (c == 0) {
            return "alarm_low";
        }
        if (c == 1) {
            return "alarm_high";
        }
        if (c == 2) {
            return "warning_low";
        }
        if (c != 3) {
            return c != 4 ? str : "safe";
        }
        return "warning_high";
    }

    public static JsonArray getJsonArray(Context context, EntitySensor entitySensor, boolean z, boolean z2) {
        JsonArray jsonArray = null;
        try {
            JsonObject jsonObject = new JsonObject();
            //jsonObject.addProperty(AppMeasurement.Param.VALUE, ExifInterface.GPS_DIRECTION_TRUE);
            jsonObject.addProperty(FirebaseAnalytics.Param.VALUE, entitySensor.getTemp_value());
            jsonObject.addProperty("unit", entitySensor.getUnit());
            if (z) {
                jsonObject.addProperty("time", changeDateFormat(entitySensor.getTime()));
            } else if (z2) {
                jsonObject.addProperty("time", entitySensor.getTime());
            } else {
                jsonObject.addProperty("time", changeDateFormat(entitySensor.getTime()));
            }
            jsonObject.addProperty("status", entitySensor.getStatus());
            jsonObject.addProperty("ble_asset_id", (Number) Integer.valueOf(entitySensor.getAsset_id()));
            jsonObject.addProperty("ble_sensor_id", (Number) Integer.valueOf(entitySensor.getBle_sensor_id()));
            jsonObject.addProperty("latitude", entitySensor.getLat());
            jsonObject.addProperty("longitude", entitySensor.getLng());
            jsonObject.addProperty("mobile", SPTrueTemp.getUserMobile(context));
            JsonArray jsonArray2 = new JsonArray();
            try {
                jsonArray2.add((JsonElement) jsonObject);
                return jsonArray2;
            } catch (Exception e) {
                jsonArray = jsonArray2;
                e = e;
                e.printStackTrace();
                return jsonArray;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return jsonArray;
        }
    }

    public static String changeDateFormat(String str) {
        Log.e("Memory save date", str);
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(str)));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getNotedTimeLong(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getHourMin(String str) {
        Log.e("Memory save date", str);
        try {
            return new SimpleDateFormat("HH:mm").format((Date) Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str)));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean getUpdateTime(Context context, String str, EntitySensor entitySensor) {
        Boolean bool;
        if (!cnt.containsKey(Integer.valueOf(entitySensor.getBle_sensor_id()))) {
            Integer put = cnt.put(Integer.valueOf(entitySensor.getBle_sensor_id()), 0);
            Log.e("inside count 0", "initilize counter");
        }
        Boolean.valueOf(false);
        count = ((int) (System.currentTimeMillis() / 1000)) % convertStringTimeToSec(str);
        cnt.put(Integer.valueOf(entitySensor.getBle_sensor_id()), Integer.valueOf(count));
        if (cnt.get(Integer.valueOf(entitySensor.getBle_sensor_id())).intValue() == 1 || cnt.get(Integer.valueOf(entitySensor.getBle_sensor_id())).intValue() == 0) {
            Log.e("inside count complete ", Integer.toString(entitySensor.getBle_sensor_id()));
            bool = true;
        } else {
            bool = false;
            Log.e("inside count not Comp", Integer.toString(entitySensor.getBle_sensor_id()));
            Log.e("inside count not Comp", Integer.toString(cnt.get(Integer.valueOf(entitySensor.getBle_sensor_id())).intValue()));
        }
        return bool.booleanValue();
    }

    public static String getTimeInString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(j));
    }

    public static String getTimeInStringComma(long j) {
        //return new SimpleDateFormat("yyyy/MM/dd,HH:mm:ss").format(Long.valueOf(j));
        return new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss").format(Long.valueOf(j));
    }

    public static String getDate(long j) {
        return new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(j));
    }

    public static int convertStringTimeToSec(String frequency) {
        Log.e("frequencyTimeToSec", frequency);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
           // return (int) ((simpleDateFormat.parse(str).getTime() - simpleDateFormat.parse("00:00:00").getTime()) / 1000);
            Date reference = simpleDateFormat.parse("00:00:00");
            Date date = simpleDateFormat.parse(frequency);
            return  (int) ((date.getTime() - reference.getTime()) / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<EntitySensor> generateEntitySensor(Context context, List<Sensor> list) {
        ArrayList arrayList = new ArrayList();
        for (Sensor next : list) {
            EntitySensor entitySensor = new EntitySensor();
            entitySensor.setBle_sensor_id(next.getSensorId().intValue());
            Log.e("generateEntitySensor", Integer.toString(next.getSensorId().intValue()));
            entitySensor.setSensor_name(next.getSensor_name());
            entitySensor.setAlarm_low(next.getAlarmLow().intValue());
            entitySensor.setAlarm_high(next.getAlarmHigh().intValue());
            entitySensor.setWarning_low(next.getWarningLow().intValue());
            entitySensor.setWarning_high(next.getWarningHigh().intValue());
            entitySensor.setUpdate_frequency(next.getUpdateFrequency());
            entitySensor.setFlag(ConnectionUtils.getConnectivityStatusString(context));
            arrayList.add(entitySensor);
        }
        Log.e("generateEntitySensorobj", arrayList.toString());
        return arrayList;
    }

    public static EntitySensor getSingleEntitySensor(Context context, Sensor sensor) {
        EntitySensor entitySensor = new EntitySensor();
        entitySensor.setBle_sensor_id(sensor.getSensorId().intValue());
        entitySensor.setSensor_name(sensor.getSensor_name());
        entitySensor.setAlarm_low(sensor.getAlarmLow().intValue());
        entitySensor.setAlarm_high(sensor.getAlarmHigh().intValue());
        entitySensor.setWarning_low(sensor.getWarningLow().intValue());
        entitySensor.setWarning_high(sensor.getWarningHigh().intValue());
        entitySensor.setUpdate_frequency(sensor.getUpdateFrequency());
        entitySensor.setFlag(ConnectionUtils.getConnectivityStatusString(context));
        return entitySensor;
    }

    public static EntitySensor createSingleEntitySensor(Context context, int i, String str, String str2, String str3, String str4, int i2) {
        Log.e("Inside Create", Integer.toString(i));
        EntitySensor entitySensor = new EntitySensor();
        entitySensor.setBle_sensor_id(i);
        entitySensor.setSensor_name(BluetoothConstants.SENSOR_NAME + i);
        entitySensor.setTemp_value(str);
        entitySensor.setUnit(str2);
        entitySensor.setTime(str3);
        entitySensor.setStatus(sensorStatusVale(str4));
        entitySensor.setAsset_id(i2);
        entitySensor.setFlag(ConnectionUtils.getConnectivityStatusString(context));
        return entitySensor;
    }

    public static SensorTempTime createSensorTempTime(EntitySensor entitySensor, String str, String str2, boolean z) {
        SensorTempTime sensorTempTime = new SensorTempTime();
        sensorTempTime.setSensor_id(entitySensor.getBle_sensor_id());
        sensorTempTime.setTemp_value(Float.parseFloat(entitySensor.getTemp_value()));
        sensorTempTime.setUnit(entitySensor.getUnit());
        sensorTempTime.setTime(getTimeInString(System.currentTimeMillis()));
        sensorTempTime.setTempFromMemory(z);
        sensorTempTime.setLat(str);
        sensorTempTime.setLng(str2);
        sensorTempTime.setStatus(entitySensor.getStatus());
        return sensorTempTime;
    }

    public static int getTimeDifference(String str) {
        try {
            long time = new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str.replace(",", " ").trim()).getTime();
            long j = time / 3600000;
            long j2 = (time / 60000) % 60;
            return (int) j;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @SuppressLint("MissingPermission")
    public static List<String> getDeviceNumberList(Context context) {
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {
                return null;
            }
            @SuppressLint("MissingPermission") List<SubscriptionInfo> activeSubscriptionInfoList = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
            for (int i = 0; i < activeSubscriptionInfoList.size(); i++) {
                SubscriptionInfo subscriptionInfo = activeSubscriptionInfoList.get(i);
                if (subscriptionInfo.getNumber() != null) {
                    arrayList.add(subscriptionInfo.getNumber());
                }
            }
            if (arrayList.size() == 0) {
                arrayList.add(((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number());
            }
        }
        return arrayList;
    }

    public static void playAssetSound(final Context context, final int i) {
        try {
            AssetFileDescriptor openFd = context.getAssets().openFd("lost.mp3");
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            mediaPlayer = mediaPlayer2;
            mediaPlayer2.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
            openFd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (MethodHelper.mediaPlayer != null) {
                        MethodHelper.playAssetSound(context, i);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopAlarm() {
        MediaPlayer mediaPlayer2 = mediaPlayer;
        if (mediaPlayer2 != null) {
            if (mediaPlayer2.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = null;
        }
    }

    public static String checkRoundFunction(double d) {
        BigDecimal scale = new BigDecimal(d).setScale(2, 6);
        Log.e("Temperature in round", String.valueOf(scale));
        return String.valueOf(scale);
    }

    public static List<AssetAndSensorInfo> getAssetsFromArray(Context context) {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = context.getResources().getStringArray(R.array.assets_array);
        for (int i = 0; i < stringArray.length; i++) {
            AssetAndSensorInfo assetAndSensorInfo = new AssetAndSensorInfo();
            assetAndSensorInfo.setAssetId(Integer.valueOf(i));
            assetAndSensorInfo.setAssetName(stringArray[i]);
            arrayList.add(assetAndSensorInfo);
        }
        return arrayList;
    }

    public static SensorIds setOrCreateEntitySensorList(int i, String str) {
        SensorIds sensorIds = new SensorIds();
        if (str.length() > 0 && str.contains("]")) {
            String trim = str.split("sensor_id:")[1].trim();
            try {
                JSONArray jSONArray = (JSONArray) new JSONObject(new JSONTokener("{data:" + trim + "}")).get("data");
                int[] iArr = new int[jSONArray.length()];
                int i2 = 0;
                while (i2 < jSONArray.length()) {
                    if (jSONArray.get(i2) != null && !jSONArray.get(i2).equals("null")) {
                        iArr[i2] = jSONArray.getInt(i2);
                    }
                    i2++;
                    sensorIds = new SensorIds(i, iArr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sensorIds;
    }

    private boolean isAppOnForeground(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
            if (next.importance == 100 && next.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
