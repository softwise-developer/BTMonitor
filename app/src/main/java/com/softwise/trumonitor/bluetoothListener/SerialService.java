package com.softwise.trumonitor.bluetoothListener;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.listeners.SerialListener;
import com.softwise.trumonitor.utils.BluetoothConstants;

import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class SerialService extends Service implements SerialListener {
    private final IBinder binder = new SerialBinder();
    public boolean connected;
    private SerialListener listener;
    private final Handler mainLooper = new Handler(Looper.getMainLooper());
    private final Queue<QueueItem> queue1 = new LinkedList();
    private final Queue<QueueItem> queue2 = new LinkedList();
    private SerialSocket socket;

    private enum QueueType {
        Connect,
        ConnectError,
        Read,
        IoError
    }

    @Override
    public void onSerialReadString(String str) {
    }

    public class SerialBinder extends Binder {
        public SerialBinder() {
        }

        public SerialService getService() {
            return SerialService.this;
        }
    }

    private class QueueItem {
        byte[] data;
        Exception e;
        QueueType type;

        QueueItem(QueueType queueType, byte[] bArr, Exception exc) {
            this.type = queueType;
            this.data = bArr;
            this.e = exc;
        }
    }

    public void onDestroy() {
        Log.e("Service Destroy", "Called");
        cancelNotification();
        disconnect();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void connect(SerialSocket serialSocket) throws IOException {
        serialSocket.connect(this);
        this.socket = serialSocket;
        this.connected = true;
    }

    public void disconnect() {
        this.connected = false;
        cancelNotification();
        SerialSocket serialSocket = this.socket;
        if (serialSocket != null) {
            serialSocket.disconnect();
            this.socket = null;
        }
    }

    public void write(byte[] bArr) throws IOException {
        if (this.connected) {
            this.socket.write(bArr);
            return;
        }
        throw new IOException(getApplication().getString(R.string.msg_not_connected));
    }

    public void attach(SerialListener serialListener) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            cancelNotification();
            synchronized (this) {
                this.listener = serialListener;
            }
            for (QueueItem queueItem : this.queue1) {
                int i = queueItem.type.ordinal();
                if (i == 1) {
                    serialListener.onSerialConnect();
                } else if (i == 2) {
                    serialListener.onSerialConnectError(queueItem.e);
                } else if (i == 3) {
                    serialListener.onSerialRead(queueItem.data);
                } else if (i == 4) {
                    serialListener.onSerialIoError(queueItem.e);
                }
            }
            for (QueueItem queueItem2 : this.queue2) {
                int i2 = queueItem2.type.ordinal();
                if (i2 == 1) {
                    serialListener.onSerialConnect();
                } else if (i2 == 2) {
                    serialListener.onSerialConnectError(queueItem2.e);
                } else if (i2 == 3) {
                    serialListener.onSerialRead(queueItem2.data);
                } else if (i2 == 4) {
                    serialListener.onSerialIoError(queueItem2.e);
                }
            }
            this.queue1.clear();
            this.queue2.clear();
            return;
        }
        throw new IllegalArgumentException("not in main thread");
    }

    public void detach() {
        if (this.connected) {
            createNotification();
        }
        this.listener = null;
    }

    @SuppressLint("WrongConstant")
    private void createNotification() {
        String str;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(BluetoothConstants.NOTIFICATION_CHANNEL, "Background service", 2);
            notificationChannel.setShowBadge(false);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
        Intent action = new Intent().setAction(BluetoothConstants.INTENT_ACTION_DISCONNECT);
        Intent addCategory = new Intent().setClassName(this, BluetoothConstants.INTENT_CLASS_MAIN_ACTIVITY).setAction("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
        PendingIntent.getBroadcast(this, 1, action, 134217728);
        PendingIntent activity = PendingIntent.getActivity(this, 1, addCategory, 134217728);
        NotificationCompat.Builder contentTitle = new NotificationCompat.Builder(this, BluetoothConstants.NOTIFICATION_CHANNEL).setColor(getResources().getColor(R.color.colorPrimary)).setContentTitle(getResources().getString(R.string.app_name));
        if (this.socket != null) {
            str = "Connected to " + this.socket.getName();
        } else {
            str = "Background Service";
        }
        startForeground(1001, contentTitle.setContentText(str).setContentIntent(activity).setOngoing(true).build());
    }

    private void cancelNotification() {
        stopForeground(true);
    }

    @Override
    public void onSerialConnect() {
        if (this.connected) {
            synchronized (this) {
                if (this.listener != null) {
                    this.mainLooper.post(new Runnable() {
                        @Override
                        public final void run() {
                            SerialListener serialListener = listener;
                            if (serialListener != null) {
                                serialListener.onSerialConnect();
                            } else {
                                queue1.add(new QueueItem(QueueType.Connect, (byte[]) null, (Exception) null));
                            }
                        }
                    });
                } else {
                    this.queue2.add(new QueueItem(QueueType.Connect, (byte[]) null, (Exception) null));
                }
            }
        }
    }


    public void onSerialConnectError(Exception exc) {
        if (this.connected) {
            synchronized (this) {
                if (this.listener != null) {
                    this.mainLooper.post(new Runnable() {
                        @Override
                        public void run() {
                            SerialListener serialListener = listener;
                            if (serialListener != null) {
                                serialListener.onSerialConnectError(exc);
                                return;
                            }
                            queue1.add(new QueueItem(QueueType.ConnectError, (byte[]) null, exc));
                            cancelNotification();
                            disconnect();
                        }
                    });
                } else {
                    this.queue2.add(new QueueItem(QueueType.ConnectError, (byte[]) null, exc));
                    cancelNotification();
                    disconnect();
                }
            }
        }
    }


    @Override
    public void onSerialRead(byte[] bArr) {
        if (this.connected) {
            synchronized (this) {
                if (this.listener != null) {
                    this.mainLooper.post(() -> {
                        SerialListener serialListener = this.listener;
                        if (serialListener != null) {
                            serialListener.onSerialRead(bArr);
                        } else {
                            this.queue1.add(new QueueItem(QueueType.Read, bArr, (Exception) null));
                        }
                    });
                } else {
                    this.queue2.add(new QueueItem(QueueType.Read, bArr, (Exception) null));
                }
            }
        }
    }


    public void onSerialIoError(Exception exc) {
        if (this.connected) {
            synchronized (this) {
                if (this.listener != null) {
                    this.mainLooper.post(() -> {
                        SerialListener serialListener = this.listener;
                        if (serialListener != null) {
                            serialListener.onSerialIoError(exc);
                            return;
                        }
                        this.queue1.add(new QueueItem(QueueType.IoError, (byte[]) null, exc));
                        cancelNotification();
                        disconnect();
                    });
                } else {
                    this.queue2.add(new QueueItem(QueueType.IoError, (byte[]) null, exc));
                    cancelNotification();
                    disconnect();
                }
            }
        }
    }



    public boolean isBluetoothConnected() {
        return this.connected;
    }
}
