package com.softwise.trumonitor.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.softwise.trumonitor.R;

import androidx.recyclerview.widget.RecyclerView;

public class SensorTesterViewHolder extends RecyclerView.ViewHolder {
    public TextView txtSensorTemp;

    public SensorTesterViewHolder(View view) {
        super(view);
        this.txtSensorTemp = (TextView) view.findViewById(R.id.list_item_text1);
    }

    public void setupView(String str) {
        this.txtSensorTemp.setText(str);
    }
}
