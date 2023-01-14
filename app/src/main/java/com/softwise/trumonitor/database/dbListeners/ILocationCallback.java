package com.softwise.trumonitor.database.dbListeners;


import com.softwise.trumonitor.database.gpsloc;

public interface ILocationCallback {
    void getLocation(gpsloc gpsloc);
}
