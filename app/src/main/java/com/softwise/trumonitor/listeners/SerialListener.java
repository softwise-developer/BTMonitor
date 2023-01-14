package com.softwise.trumonitor.listeners;

public interface SerialListener {
    void onSerialConnect();

    void onSerialConnectError(Exception exc);

    void onSerialIoError(Exception exc);

    void onSerialRead(byte[] bArr);

    void onSerialReadString(String str);
}
