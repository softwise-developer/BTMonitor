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

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.database.ISensorTempCallback;
import com.softwise.trumonitor.database.SensorTempTime;
import com.softwise.trumonitor.databinding.ActivitySensorGraphBinding;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.helper.ServerDatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.components.Legend;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class SensorGraphActivity extends AppCompatActivity {
    public String alarm = "Alarm";
    ArrayList<Entry> alarmTemp;
    private EntitySensor entitySensor = new EntitySensor();
    /* access modifiers changed from: private */
    public LineChart lineChart;
    ActivitySensorGraphBinding mBinding;
    public String normal = "Normal";
    ArrayList<Entry> normalTemp;
    private ProgressBar prLoad;
    private TextView txtEmptyData;
    XAxis xl;

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySensorGraphBinding inflate = ActivitySensorGraphBinding.inflate(getLayoutInflater());
        this.mBinding = inflate;
        ConstraintLayout root = inflate.getRoot();
        if (getIntent().getExtras() != null) {
            this.entitySensor = (EntitySensor) getIntent().getParcelableExtra("sensor");
            this.mBinding.txtSensorName.setText(this.entitySensor.getSensor_name());
            setContentView((View) root);
            this.lineChart = this.mBinding.lineChart;
            this.txtEmptyData = this.mBinding.txtEmptyData;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            setTitle(this.entitySensor.getSensor_name());
            initializeLineGraph();
            getSensorData();
            return;
        }
        finish();
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
        new ServerDatabaseHelper(getApplicationContext()).getSensorTemperature(1, new ISensorTempCallback() {
            @Override
            public void loadTemperature(List<SensorTempTime> list) {
                if (list == null || list.size() <= 0) {
                    SensorGraphActivity.this.lineChart.setVisibility(View.GONE);
                    return;
                }
                strArr[0] = new String[list.size()];
                SensorGraphActivity.this.loadData(list);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
