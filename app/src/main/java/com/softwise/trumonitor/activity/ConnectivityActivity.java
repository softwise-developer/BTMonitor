package com.softwise.trumonitor.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.softwise.trumonitor.R;
import com.softwise.trumonitor.bluetoothListener.SerialService;
import com.softwise.trumonitor.bluetoothListener.SerialSocket;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.databinding.ActivityConnectivityBinding;
import com.softwise.trumonitor.helper.DialogHelper;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.helper.ServerDatabaseHelper;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.listeners.IObserveEntitySensorListener;
import com.softwise.trumonitor.listeners.IReceiveDataListeners;
import com.softwise.trumonitor.listeners.SerialListener;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.softwise.trumonitor.utils.TextUtil;


public class ConnectivityActivity extends AppCompatActivity implements ServiceConnection, SerialListener, IReceiveDataListeners, IObserveEntitySensorListener {
    public static SerialService service;
    private String TAG = "ConnectivityActivity";
    /* access modifiers changed from: private */
    public CountDownTimer countDownTimer;
    String deviceAddress = null;
    String deviceName = null;
    private boolean hexEnabled = false;
    public boolean initialStart = true;
    ActivityConnectivityBinding mBinding;
    private String memoryCount;
    private String newline = TextUtil.newline_crlf;
    /* access modifiers changed from: private */
    public StringBuilder receivedMessage;
    private int dataPoints;

    @Override
    public void getEntitySensor(EntitySensor entitySensor) {
    }

    @Override
    public void onReceiveData(byte[] bArr) {
    }

    @Override
    public void onSerialReadString(String str) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityConnectivityBinding inflate = ActivityConnectivityBinding.inflate(getLayoutInflater());
        this.mBinding = inflate;
        setContentView((View) inflate.getRoot());
        if (getIntent().getExtras() == null) {
            return;
        }
        if ("ST".equals(SPTrueTemp.getCallingType(getApplicationContext()))) {
            finish();
            return;
        }
        this.deviceName = getIntent().getStringExtra("device_name");
        this.deviceAddress = getIntent().getStringExtra("device_mac");
        loadService();
        this.mBinding.btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                if ("Connect".equals(mBinding.btnConnectDisconnect.getText().toString().trim())) {
                    setGIF();
                    loadService();
                }
            }
        });
        this.mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                service.disconnect();
                finish();
            }
        });
    }


    private void loadService() {
        if (!isMyServiceRunning(SerialService.class)) {
            SPTrueTemp.saveCallingType(getApplicationContext(), (String) null);
            Log.d(this.TAG, "Service active");
            startService();
            return;
        }
        Log.d(this.TAG, "Service Not active");
        if ("".equals(SPTrueTemp.getSensorId(getApplicationContext())) || !service.connected) {
            this.initialStart = true;
            stopService(new Intent(this, SerialService.class));
            setGIF();
            SPTrueTemp.saveCallingType(getApplicationContext(), (String) null);
            startService();
        }
    }

    private void startService() {
        getApplication().bindService(new Intent(getApplication(), SerialService.class), this, Context.BIND_AUTO_CREATE);
    }

    @SuppressLint("WrongConstant")
    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public void connect() {
        try {
            service.connect(new SerialSocket(getApplication(), BluetoothAdapter.getDefaultAdapter().getRemoteDevice(this.deviceAddress)));
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    public void send(String str) {
        byte[] bArr;
        Log.e("Send wala msg", str);
        try {
            clearReceiveData();
            if (this.hexEnabled) {
                StringBuilder sb = new StringBuilder();
                TextUtil.toHexString(sb, TextUtil.fromHexString(str));
                TextUtil.toHexString(sb, this.newline.getBytes());
                bArr = TextUtil.fromHexString(sb.toString());
            } else {
                bArr = (str + this.newline).getBytes();
            }
            service.write(bArr);
            if ("send memory data".equals(str)) {
                final String[] strArr = {null};
                this.countDownTimer = new CountDownTimer(10000, 1000) {
                    public void onTick(long j) {
                        if (ConnectivityActivity.this.receivedMessage.toString().contains(")")) {
                            strArr[0] = "Z";
                            ConnectivityActivity.this.countDownTimer.cancel();
                            ConnectivityActivity.this.countDownTimer.onFinish();
                        }
                    }

                    public void onFinish() {
                        if (strArr[0] == null) {
                            ConnectivityActivity.this.resetButtonDialog();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            onSerialIoError(e);
        }
    }

    public void receive(byte[] bArr) {
        if (service.isBluetoothConnected()) {
            String str = new String(bArr);
            Log.e("Received data start", str);
            if (!"".equals(str) && str.trim().length() > 0) {
                try {
                    this.receivedMessage.append(TextUtil.toCaretString(str.trim(), this.newline.length() != 0));
                    /*if (this.receivedMessage.length() > 0) {
                        String valueOf = String.valueOf(this.receivedMessage.toString().charAt(this.receivedMessage.toString().length() - 1));
                        if (this.receivedMessage.toString().contains("(")) {
                            this.receivedMessage.toString().split("\\(");
                        }
                        char c = 65535;
                        int hashCode = valueOf.hashCode();
                        if (hashCode != 41) {
                            if (hashCode != 93) {
                                if (hashCode == 95) {
                                    if (valueOf.equals("_")) {
                                        c = 2;
                                    }
                                }
                            } else if (valueOf.equals("]")) {
                                c = 1;
                            }
                        } else if (valueOf.equals(")")) {
                            c = 0;
                            stopCountDownTimer();
                            String[] split = this.receivedMessage.toString().split("\\(");
                            String trim = split[1].replace(")", "").trim();
                            Log.e("len", Integer.toString(split.length));
                            Log.e("in memory receive", this.receivedMessage.toString());
                            Log.e("in memory receive", trim);
                            ServerDatabaseHelper.getInstance(getApplicationContext()).saveSensorDataFromMemoryToServer(getApplicationContext(), trim, true, new IObserveEntitySensorListener() {
                                public final void getEntitySensor(EntitySensor entitySensor) {
                                    ConnectivityActivity.this.getEntitySensor(entitySensor);
                                }
                            });
                            send("send sensor id");
                            clearReceiveData();

                        } else if (c == 1) {
                            stopCountDownTimer();
                            DialogHelper.dismissProgressDialog();
                            SPTrueTemp.saveSensorId(getApplicationContext(), this.receivedMessage.toString());
                            startActivity(new Intent(this, AssetsInfoActivity.class));
                            clearReceiveData();
                        } else if (c == 2) {
                            stopCountDownTimer();
                            ServerDatabaseHelper.getInstance(getApplicationContext()).saveSensorDataFromMemoryToServer(getApplicationContext(), this.receivedMessage.toString(), false, new IObserveEntitySensorListener() {
                                @Override
                                public final void getEntitySensor(EntitySensor entitySensor) {
                                    ConnectivityActivity.this.getEntitySensor(entitySensor);
                                }
                            });
                            send("Y");
                            clearReceiveData();
                        }*/
                    if (receivedMessage.length() > 0) {
                        String last = String.valueOf(receivedMessage.toString().charAt(receivedMessage.toString().length() - 1));
                        if (receivedMessage.toString().contains("(")) {
                            String[] dataPointsAndDataArray = receivedMessage.toString().split("\\(");
                            dataPoints = 0;
                            if (!"".equals(dataPointsAndDataArray[0])) {
                                dataPoints = Integer.parseInt(dataPointsAndDataArray[0]);
                            }
                        }
                        switch (last) {
                            case ")":// receive data from memory
                                stopCountDownTimer();
                                // 12(data)
                                Log.e("Inside bracket ", String.valueOf(receivedMessage.toString()));
                                String[] dataPointsAndDataArray = receivedMessage.toString().split("\\(");
                                if (!"".equals(dataPointsAndDataArray[0])) {
                                    dataPoints = Integer.parseInt(dataPointsAndDataArray[0]);
                                }
                                Log.e("Data Points ", String.valueOf(dataPoints));
                                String memoryData = dataPointsAndDataArray[1].replace(")", "").trim();
                                String replacedData = memoryData;
                                //String memoryData = dataPointsAndDataArray[0].replace(")", "").trim();
                                if (memoryData.contains("Reading ID, Temperature")) {
                                    replacedData = memoryData.replaceAll("Reading ID, Temperature", "").trim();

                                }
                                if (memoryData.contains("Reading ID, Temperature ^M")) {
                                    replacedData = memoryData.replace("Reading ID, Temperature ^M", "").trim();
                                }
                                Log.e("Inside replace data ", replacedData);
                                if (memoryData != "") {
                                    ServerDatabaseHelper.getInstance(getApplicationContext()).saveSensorDataFromMemoryToServer(getApplicationContext(), replacedData, true, new IObserveEntitySensorListener() {
                                        @Override
                                        public final void getEntitySensor(EntitySensor entitySensor) {
                                            ConnectivityActivity.this.getEntitySensor(entitySensor);
                                        }
                                    });
                                }
                                send("send sensor id");
                                clearReceiveData();
                                dataPoints = 0;


                                break;
                            case "]":// sensor id
                                stopCountDownTimer();
                                DialogHelper.dismissProgressDialog();
                                SPTrueTemp.saveSensorId(getApplicationContext(), receivedMessage.toString());
                                // open assetInfo activity
                                Intent intent = new Intent(ConnectivityActivity.this, AssetsInfoActivity.class);
                                startActivity(intent);
                                clearReceiveData();
                                dataPoints = 0;
                                break;
                            case "_":
                                stopCountDownTimer();
                                //if (dataPoints == 0) {
                                ServerDatabaseHelper.getInstance(getApplicationContext()).saveSensorDataFromMemoryToServer(getApplicationContext(), this.receivedMessage.toString(), false, new IObserveEntitySensorListener() {
                                    public final void getEntitySensor(EntitySensor entitySensor) {
                                        ConnectivityActivity.this.getEntitySensor(entitySensor);
                                    }
                                });
                                  /*  Intent i = new Intent("sensorTemp");
                                    i.putExtra("msg", receivedMessage.toString()); //EDIT: this passes a parameter to the receiver
                                    sendBroadcast(i);*/
                                send("Y");
                                clearReceiveData();
                                //}
                                break;
                        }
                        if ("alarm warning level receive successfully".equals(this.receivedMessage.toString())) {
                            send(MethodHelper.getTimeInStringComma(System.currentTimeMillis()) + ",DateTime received");
                        }
                        if ("date time receive successfully".equalsIgnoreCase(this.receivedMessage.toString())) {
                            Intent intent = new Intent("sensorData");
                            // Intent intent = new Intent(ConnectivityActivity.this,AssetsInfoActivity.class);
                            intent.putExtra("msg", this.receivedMessage.toString());
                            sendBroadcast(intent);
                            //startActivityForResult(intent,2);
                            clearReceiveData();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.d(this.TAG, "Bluetooth disconnect");
        }
    }

    private void stopCountDownTimer() {
        CountDownTimer countDownTimer2 = this.countDownTimer;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
    }

    public void clearReceiveData() {
        this.receivedMessage = new StringBuilder();
    }

    private void setGIF() {
        this.mBinding.imgConnetcing.setVisibility(View.VISIBLE);
        try {
            Glide.with((FragmentActivity) this).asGif().load(Integer.valueOf(R.drawable.bluetooth_connecting)).into(this.mBinding.imgConnetcing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        SerialService service2 = ((SerialService.SerialBinder) iBinder).getService();
        service = service2;
        service2.attach(this);
        if (this.initialStart) {
            this.mBinding.btnConnectDisconnect.setVisibility(View.GONE);
            this.mBinding.btnCancel.setVisibility(View.VISIBLE);
            this.mBinding.txtConnectionStatus.setText(String.format(getResources().getString(R.string.status_connecting), new Object[]{this.deviceName}));
            this.mBinding.prbConnecting.setVisibility(View.VISIBLE);
            this.initialStart = false;
            setGIF();
            connect();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.e("Bluetooth ", "Disconnect");
        this.mBinding.txtConnectionStatus.setText(String.format(getResources().getString(R.string.status_disconnected), new Object[]{this.deviceName}));
        this.mBinding.imgConnetcing.setVisibility(View.GONE);
        this.mBinding.prbConnecting.setVisibility(View.GONE);
        connectionFailedDialog(String.valueOf(Html.fromHtml(getResources().getString(R.string.msg_bluetooth_disconnected))));
    }


    public void serviceDisConnect() {
        SerialService serialService = service;
        if (serialService != null) {
            serialService.disconnect();
            SPTrueTemp.clearConnectedAddress(getApplicationContext());
        }
    }

    @Override
    public void onSerialConnect() {
        send("send memory data");
        this.mBinding.txtConnectionStatus.setText(String.format(getResources().getString(R.string.status_connected), new Object[]{this.deviceName}));
    }

    @Override
    public void onSerialConnectError(Exception exc) {
        String str;
        Log.e("Bluetooth ", "Connection Error CC " + exc.getMessage());
        this.mBinding.prbConnecting.setVisibility(View.GONE);
        this.mBinding.imgConnetcing.setVisibility(View.GONE);
        if (!"read failed, socket might closed or timeout, read ret: -1".equals(exc.getMessage())) {
            str = String.valueOf(Html.fromHtml(getResources().getString(R.string.msg_reset_bluetooth)));
        } else {
            str = getResources().getString(R.string.status_connection_failed);
        }
        connectionFailedDialog(str);
    }

    @Override
    public void onSerialRead(byte[] bArr) {
        receive(bArr);
    }

    @Override
    public void onSerialIoError(Exception exc) {
        Log.e("Bluetooth ", "IO Error CC" + exc.getMessage());
        this.mBinding.prbConnecting.setVisibility(View.GONE);
        this.mBinding.imgConnetcing.setVisibility(View.GONE);
        connectionFailedDialog(String.valueOf(Html.fromHtml(getResources().getString(R.string.msg_reset_bluetooth))));
    }

    private void connectionFailedDialog(String str) {
        if (!"ST".equals(SPTrueTemp.getCallingType(getApplicationContext()))) {
            DialogHelper.messageDialog(ConnectivityActivity.this, "Warning", str, new IBooleanListener() {
                @Override
                public final void callBack(boolean z) {
                    if (z && service != null) {
                        serviceDisConnect();
                        stopService(new Intent(ConnectivityActivity.this, SerialService.class));
                        SPTrueTemp.clearConnectedAddress(ConnectivityActivity.this);
                        System.exit(0);
                        finish();
                    }
                }
            });
            return;
        }
        Intent intent = new Intent("sensorTemp");
        //intent.putExtra(MediaRouteProviderProtocol.SERVICE_DATA_ERROR, MediaRouteProviderProtocol.SERVICE_DATA_ERROR);
        intent.putExtra("error", "error");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


    /* access modifiers changed from: private */
    public void resetButtonDialog() {
        DialogHelper.messageDialog(ConnectivityActivity.this, "Warning", getString(R.string.msg_reset_button_pressed), new IBooleanListener() {
            @Override
            public final void callBack(boolean z) {
                if (z) {
                    SerialService serialService = service;
                    if (serialService != null) {
                        serialService.disconnect();
                        stopService(new Intent(ConnectivityActivity.this, SerialService.class));
                        SPTrueTemp.clearConnectedAddress(ConnectivityActivity.this);
                        finish();
                        return;
                    }
                    return;
                }
                finish();
            }
        });
    }

    public /* synthetic */ void lambda$resetButtonDialog$3$ConnectivityActivity(boolean z) {

    }

    /* access modifiers changed from: protected */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
