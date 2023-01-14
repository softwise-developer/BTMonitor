package com.softwise.trumonitor.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwise.trumonitor.R;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SensorLevelViewHolder extends RecyclerView.ViewHolder {
    public CardView cardParent;
    public ImageView imgEdit;
    public TextView txtAlarmHigh;
    public TextView txtAlarmLow;
    public TextView txtFrequency;
    public TextView txtSensor;
    public TextView txtWarningHigh;
    public TextView txtWarningLow;

    public SensorLevelViewHolder(View view) {
        super(view);
        this.txtSensor = (TextView) view.findViewById(R.id.txt_sensor_id);
        this.txtAlarmLow = (TextView) view.findViewById(R.id.txt_alarm_low);
        this.txtAlarmHigh = (TextView) view.findViewById(R.id.txt_alarm_high);
        this.txtWarningLow = (TextView) view.findViewById(R.id.txt_warning_low);
        this.txtWarningHigh = (TextView) view.findViewById(R.id.txt_warning_high);
        this.txtFrequency = (TextView) view.findViewById(R.id.txt_update_frequency);
        this.cardParent = (CardView) view.findViewById(R.id.card_parent);
        this.imgEdit = (ImageView) view.findViewById(R.id.img_edit_sensor);
    }
}
