package com.softwise.trumonitor.listeners;

import com.softwise.trumonitor.models.AssetAndSensorInfo;

import java.util.List;


public interface IObserveUploadDataListener {
    void dataOnComplete();

    void errorException(Throwable th);

    void nextDataLoad(List<AssetAndSensorInfo> list);
}
