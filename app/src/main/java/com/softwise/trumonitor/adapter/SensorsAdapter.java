package com.softwise.trumonitor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.viewHolders.SensorViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class SensorsAdapter extends RecyclerView.Adapter<SensorViewHolder> {
    private final Context mContext;
    private final OnSensorTempSelectListeners mOnSensorSelectListeners;
    private int mSensorId;
    private List<EntitySensor> mSensorList = new ArrayList();
    private String mTemp;
    private String mUnit;

    public interface OnSensorTempSelectListeners {
        void onSensorSelect(EntitySensor entitySensor);
    }

    public SensorsAdapter(Context context, List<EntitySensor> list, OnSensorTempSelectListeners onSensorTempSelectListeners) {
        this.mSensorList = list;
        this.mContext = context;
        this.mOnSensorSelectListeners = onSensorTempSelectListeners;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SensorViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_sensor_unit, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(SensorViewHolder sensorViewHolder, int i) {
        EntitySensor entitySensor = this.mSensorList.get(i);
        sensorViewHolder.txtSensor.setText(entitySensor.getSensor_name());
        sensorViewHolder.txtAlarmLow.setText(String.valueOf(entitySensor.getAlarm_low()));
        sensorViewHolder.txtAlarmHigh.setText(String.valueOf(entitySensor.getAlarm_high()));
        Log.e("mSensorIdAdapter", Integer.toString(this.mSensorId));
        Log.e("sensor.getBle_sensor_id", Integer.toString(entitySensor.getBle_sensor_id()));
        if (entitySensor.getBle_sensor_id() == this.mSensorId) {
            entitySensor.setTemp_value(this.mTemp);
            entitySensor.setUnit(this.mUnit);
        }
        if (entitySensor.getTemp_value() != null && entitySensor.getTemp_value().length() > 0) {
            double parseDouble = Double.parseDouble(entitySensor.getTemp_value());
            sensorViewHolder.txtTemperature.setText(Math.round(parseDouble) + "°" + entitySensor.getUnit());
            if ("alarm_low".equals(entitySensor.getStatus()) || "alarm_high".equals(entitySensor.getStatus())) {
                AnimationUtils.loadAnimation(this.mContext, R.anim.blink);
                sensorViewHolder.txtStatus.setText("Temperature not in range");
                sensorViewHolder.txtStatus.setVisibility(View.VISIBLE);
            } else {
                sensorViewHolder.txtStatus.setVisibility(View.GONE);
            }
        }
        sensorViewHolder.linParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSensorSelectListeners.onSensorSelect(entitySensor);
            }
        });
    }

    public int getItemCount() {
        return this.mSensorList.size();
    }

    public void updateList(List<EntitySensor> list) {
        this.mSensorList.clear();
        this.mSensorList.addAll(list);
        notifyDataSetChanged();
    }

    public void updateEntitySensor(EntitySensor entitySensor) {
        if (this.mSensorList.size() > 0) {
            for (EntitySensor next : this.mSensorList) {
                if (next.getBle_sensor_id() == entitySensor.getBle_sensor_id()) {
                    this.mSensorList.remove(this.mSensorList.indexOf(next));
                    this.mSensorList.add(entitySensor);
                }
            }
        } else {
            this.mSensorList.add(entitySensor);
        }
        notifyDataSetChanged();
    }

    public void updateTemp(int i, String str, String str2) {
        this.mSensorId = i;
        this.mTemp = str;
        this.mUnit = str2;
        notifyDataSetChanged();
    }
}
