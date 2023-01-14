package com.softwise.trumonitor.viewHolders;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softwise.trumonitor.R;

import androidx.recyclerview.widget.RecyclerView;


public class DeviceViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout layout;
    public TextView text1;
    public TextView text2;

    public DeviceViewHolder(View view) {
        super(view);
        this.layout = (RelativeLayout) view.findViewById(R.id.list_item);
        this.text1 = (TextView) view.findViewById(R.id.list_item_text1);
        this.text2 = (TextView) view.findViewById(R.id.list_item_text2);
    }

    public void setupView(BluetoothDevice bluetoothDevice) {
        this.text1.setText(bluetoothDevice.getName());
        this.text2.setText(bluetoothDevice.getAddress());
    }
}
