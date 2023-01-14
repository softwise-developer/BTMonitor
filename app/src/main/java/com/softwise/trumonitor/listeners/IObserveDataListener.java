package com.softwise.trumonitor.listeners;


import com.softwise.trumonitor.models.AssetAndSensorInfo;

public interface IObserveDataListener {
    void dataOnComplete();

    void errorException(Throwable th);

    void nextDataLoad(AssetAndSensorInfo assetAndSensorInfo);
}
