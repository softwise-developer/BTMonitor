package com.softwise.trumonitor.database.dbListeners;

import com.softwise.trumonitor.database.gpsloc;

import io.reactivex.Maybe;
import java.util.List;


public interface GpslocDao {
    List<gpsloc> getAllGpsLoc();

    Maybe<gpsloc> getGpsLocByUserId(int i);

    void insert(gpsloc gpsloc);

    void update(gpsloc gpsloc);
}
