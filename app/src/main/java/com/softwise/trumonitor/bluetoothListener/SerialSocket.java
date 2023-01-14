package com.softwise.trumonitor.bluetoothListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.softwise.trumonitor.listeners.SerialListener;
import com.softwise.trumonitor.utils.BluetoothConstants;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Executors;

public class SerialSocket implements Runnable {
    private static final UUID BLUETOOTH_SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean connected;
    private Context context;
    private BluetoothDevice device;
    private final BroadcastReceiver disconnectBroadcastReceiver;
    /* access modifiers changed from: private */
    public SerialListener listener;
    private BluetoothSocket socket;

    public SerialSocket(Context context2, BluetoothDevice bluetoothDevice) {
        if (!(context2 instanceof Activity)) {
            this.context = context2;
            this.device = bluetoothDevice;
            this.disconnectBroadcastReceiver = new BroadcastReceiver() {
               @Override
                public void onReceive(Context context, Intent intent) {
                    if (SerialSocket.this.listener != null) {
                        SerialSocket.this.listener.onSerialIoError(new IOException("background disconnect"));
                    }
                    SerialSocket.this.disconnect();
                }
            };
            return;
        }
        throw new InvalidParameterException("expected non UI context");
    }

    /* access modifiers changed from: package-private */
    @SuppressLint("MissingPermission")
    public String getName() {
        return device.getName() != null ? device.getName() : this.device.getAddress();
    }

    /* access modifiers changed from: package-private */
    public void connect(SerialListener serialListener) throws IOException {
        this.listener = serialListener;
        this.context.registerReceiver(this.disconnectBroadcastReceiver, new IntentFilter(BluetoothConstants.INTENT_ACTION_DISCONNECT));
        Executors.newSingleThreadExecutor().submit(this);
    }

    /* access modifiers changed from: package-private */
    public void disconnect() {
        this.listener = null;
        BluetoothSocket bluetoothSocket = this.socket;
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
            } catch (Exception unused) {
            }
            this.socket = null;
        }
        try {
            this.context.unregisterReceiver(this.disconnectBroadcastReceiver);
        } catch (Exception unused2) {
        }
    }

    /* access modifiers changed from: package-private */
    public void write(byte[] bArr) throws IOException {
        if (this.connected) {
            this.socket.getOutputStream().write(bArr);
            return;
        }
        throw new IOException("not connected");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void run() {
        try {
            socket = device.createRfcommSocketToServiceRecord(BLUETOOTH_SPP);
            socket.connect();
            if (this.listener != null) {
                this.listener.onSerialConnect();
            }
            this.connected = true;
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    if (this.socket != null && this.socket.isConnected()) {
                        byte[] copyOf = Arrays.copyOf(bArr, this.socket.getInputStream().read(bArr));
                        if (this.listener != null) {
                            this.listener.onSerialRead(copyOf);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.connected = false;
                SerialListener serialListener = this.listener;
                if (serialListener != null) {
                    serialListener.onSerialIoError(e);
                }
                try {
                    if (this.socket != null) {
                        this.socket.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.socket = null;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            SerialListener serialListener2 = this.listener;
            if (serialListener2 != null) {
                serialListener2.onSerialConnectError(e3);
            }
            try {
                this.socket.close();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            this.socket = null;
        }
    }
}
