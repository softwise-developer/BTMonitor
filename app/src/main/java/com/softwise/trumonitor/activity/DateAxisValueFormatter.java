package com.softwise.trumonitor.activity;


import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* compiled from: SensorGraphNewActivity */
class DateAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    private ArrayList<String> mValues;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public DateAxisValueFormatter(ArrayList<String> arrayList) {
        this.mValues = arrayList;
    }

    @Override
    public String getFormattedValue(float f) {
        return this.sdf.format(new Date((long) f));
    }
}
