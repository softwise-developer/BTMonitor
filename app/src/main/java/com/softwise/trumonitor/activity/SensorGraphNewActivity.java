package com.softwise.trumonitor.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.internal.view.SupportMenu;

import com.softwise.trumonitor.database.SensorTempTime;
import com.softwise.trumonitor.databinding.ActivitySensorGraphNewBinding;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.helper.ServerDatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class SensorGraphNewActivity extends AppCompatActivity {
    public String alarm = "Alarm";
    ArrayList<Entry> alarmTemp;
    /* access modifiers changed from: private */
    public LineChart lineChart;
    ActivitySensorGraphNewBinding mBinding;
    public String normal = "Normal";
    ArrayList<Entry> normalTemp;
    private ProgressBar prLoad;
    private TextView txtEmptyData;
    XAxis xl;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySensorGraphNewBinding inflate = ActivitySensorGraphNewBinding.inflate(getLayoutInflater());
        this.mBinding = inflate;
        ConstraintLayout root = inflate.getRoot();
        this.mBinding.txtSensorName.setText("Graph");
        setContentView((View) root);
        this.lineChart = this.mBinding.lineChart;
        this.prLoad = this.mBinding.prbLoad;
        this.txtEmptyData = this.mBinding.txtEmptyData;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Graph");
        initializeLineGraph();
        getSensorData();
    }

    private void initializeLineGraph() {
        this.normalTemp = new ArrayList<>();
        this.alarmTemp = new ArrayList<>();
        this.lineChart.setDrawGridBackground(false);
        this.lineChart.setTouchEnabled(true);
        this.lineChart.setDragEnabled(true);
        this.lineChart.setScaleEnabled(true);
        this.lineChart.setPinchZoom(true);
        this.lineChart.getXAxis().setTextSize(15.0f);
        this.lineChart.getAxisLeft().setTextSize(15.0f);
        XAxis xAxis = this.lineChart.getXAxis();
        this.xl = xAxis;
        xAxis.setAvoidFirstLastClipping(true);
        this.lineChart.getAxisLeft().setInverted(true);
        this.lineChart.getAxisRight().setEnabled(false);
        this.lineChart.getLegend().setForm(Legend.LegendForm.LINE);
    }

    private void getSensorData() {
        new ArrayList();
        final String[][] strArr = {null};
        new ServerDatabaseHelper(getApplicationContext()).getSensorTemperature(1, new com.softwise.trumonitor.database.ISensorTempCallback() {
            @Override
            public void loadTemperature(List<SensorTempTime> list) {
                if (list == null || list.size() <= 0) {
                    SensorGraphNewActivity.this.lineChart.setVisibility(View.GONE);
                    return;
                }
                strArr[0] = new String[list.size()];
                SensorGraphNewActivity.this.loadData(list);
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadData(List<SensorTempTime> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SensorTempTime sensorTempTime = list.get(i);
            String status = sensorTempTime.getStatus();
            float parseFloat = Float.parseFloat(new DecimalFormat("##.##").format(MethodHelper.getNotedTimeLong(sensorTempTime.getTime())));
            if (sensorTempTime.getTime() != null) {
                String str = sensorTempTime.getTime().split(" ")[0];
            }
            if ("alarm_low".equals(status) || "alarm_high".equals(status)) {
                this.alarmTemp.add(new Entry(parseFloat, sensorTempTime.getTemp_value()));
                Log.e("Alarm Time and Temp", MethodHelper.getHourMin(sensorTempTime.getTime()) + " " + sensorTempTime.getTemp_value());
                arrayList.add(list.get(i).getTime());
            } else {
                this.normalTemp.add(new Entry(parseFloat, sensorTempTime.getTemp_value()));
                Log.e("Normal Time and Temp", MethodHelper.getHourMin(sensorTempTime.getTime()) + " " + sensorTempTime.getTemp_value());
                arrayList.add(list.get(i).getTime());
            }
        }
        this.xl.setValueFormatter(new DateAxisValueFormatter(arrayList));
        this.xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList arrayList2 = new ArrayList();
        LineDataSet lineDataSet = new LineDataSet(this.normalTemp, this.normal);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleRadius(4.0f);
        lineDataSet.setColor(Color.parseColor("#3949AB"));
        lineDataSet.setCircleColor(Color.parseColor("#536DFE"));
        LineDataSet lineDataSet2 = new LineDataSet(this.alarmTemp, this.alarm);
        lineDataSet2.setLineWidth(1.5f);
        lineDataSet2.setCircleRadius(4.0f);
        lineDataSet2.setColor(Color.parseColor("#F44336"));
        lineDataSet2.setCircleColor(Color.parseColor("#FF8A80"));
        arrayList2.add(lineDataSet);
        arrayList2.add(lineDataSet2);
        this.lineChart.setData(new LineData((List<ILineDataSet>) arrayList2));
        this.lineChart.invalidate();
        this.prLoad.setVisibility(View.GONE);
    }

    private void loadLineGraph(List<SensorTempTime> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        String[] strArr = new String[list.size()];
        int size = list.size();
        String[] strArr2 = new String[size];
        int i = 0;
        for (SensorTempTime next : list) {
            if (next.getTime() != null) {
                String str = next.getTime().split(" ")[0];
            }
            float parseFloat = Float.parseFloat(new DecimalFormat("##.##").format(MethodHelper.getNotedTimeLong(next.getTime())));
            Log.e("Date time ", String.valueOf(parseFloat));
            float round = (float) Math.round(next.getTemp_value());
            if ("alarm_low".equals(next.getStatus()) || "alarm_high".equals(next.getStatus())) {
                arrayList3.add(new Entry(parseFloat, round));
                strArr2[i] = list.get(i).getTime();
            } else {
                arrayList2.add(new Entry(parseFloat, round));
                strArr2[i] = list.get(i).getTime();
            }
            arrayList.add(i, String.valueOf(round));
            strArr[i] = String.valueOf(round);
            i++;
        }
        if (size > 0) {
            this.lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            ArrayList arrayList4 = new ArrayList();
            LineDataSet lineDataSet = new LineDataSet(arrayList3, "Normal");
            lineDataSet.setDrawCircles(true);
            lineDataSet.setColor(-16776961);
            LineDataSet lineDataSet2 = new LineDataSet(arrayList2, "sin");
            lineDataSet2.setDrawCircles(true);
            lineDataSet2.setColor(SupportMenu.CATEGORY_MASK);
            arrayList4.add(lineDataSet);
            arrayList4.add(lineDataSet2);
            this.lineChart.setData(new LineData((List<ILineDataSet>) arrayList4));
            this.lineChart.setVisibility(View.VISIBLE);
            this.prLoad.setVisibility(View.GONE);
            return;
        }
        this.lineChart.setVisibility(View.GONE);
        this.prLoad.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
