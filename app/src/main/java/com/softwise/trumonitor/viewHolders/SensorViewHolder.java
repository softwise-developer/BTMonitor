package com.softwise.trumonitor.viewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softwise.trumonitor.R;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SensorViewHolder extends RecyclerView.ViewHolder {
    public CardView cardParent;
    public LinearLayout linParent;
    public TextView txtAlarmHigh;
    public TextView txtAlarmLow;
    public TextView txtSensor;
    public TextView txtStatus;
    public TextView txtTemperature;

    public SensorViewHolder(View view) {
        super(view);
        this.txtSensor = (TextView) view.findViewById(R.id.txt_sensor_id);
        this.txtAlarmLow = (TextView) view.findViewById(R.id.txt_alarm_low);
        this.txtAlarmHigh = (TextView) view.findViewById(R.id.txt_alarm_high);
        this.txtTemperature = (TextView) view.findViewById(R.id.txt_temperature);
        this.cardParent = (CardView) view.findViewById(R.id.card_parent);
        this.txtStatus = (TextView) view.findViewById(R.id.txt_sensor_status);
        this.linParent = (LinearLayout) view.findViewById(R.id.lin_parent);
    }
}
