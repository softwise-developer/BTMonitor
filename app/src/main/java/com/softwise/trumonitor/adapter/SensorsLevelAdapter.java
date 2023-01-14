package com.softwise.trumonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.models.Sensor;
import com.softwise.trumonitor.viewHolders.SensorLevelViewHolder;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SensorsLevelAdapter extends RecyclerView.Adapter<SensorLevelViewHolder> {
    private Context mContext;
    private OnSensorLevelSelectListeners mOnSensorLevelSelectListeners;
    private List<Sensor> mSensorList = new ArrayList();

    public interface OnSensorLevelSelectListeners {
        void onSensorLevelSelect(Sensor sensor);
    }

    public SensorsLevelAdapter(Context context, List<Sensor> list, OnSensorLevelSelectListeners onSensorLevelSelectListeners) {
        this.mSensorList = list;
        this.mContext = context;
        this.mOnSensorLevelSelectListeners = onSensorLevelSelectListeners;
    }

    public SensorLevelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SensorLevelViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sensor_level_unit, viewGroup, false));
    }

    public void onBindViewHolder(SensorLevelViewHolder sensorLevelViewHolder, int i) {
        Sensor sensor = this.mSensorList.get(i);
        sensorLevelViewHolder.txtSensor.setText(sensor.getSensor_name());
        sensorLevelViewHolder.txtAlarmLow.setText(String.valueOf(sensor.getAlarmLow()));
        sensorLevelViewHolder.txtAlarmHigh.setText(String.valueOf(sensor.getAlarmHigh()));
        sensorLevelViewHolder.txtWarningLow.setText(String.valueOf(sensor.getWarningLow()));
        sensorLevelViewHolder.txtWarningHigh.setText(String.valueOf(sensor.getWarningHigh()));
        if (sensor.getUpdateFrequency() != null) {
            TextView textView = sensorLevelViewHolder.txtFrequency;
            textView.setText(String.valueOf(sensor.getUpdateFrequency()) + " Min");
        }
        sensorLevelViewHolder.cardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSensorLevelSelectListeners.onSensorLevelSelect(sensor);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mSensorList.size();
    }
}
