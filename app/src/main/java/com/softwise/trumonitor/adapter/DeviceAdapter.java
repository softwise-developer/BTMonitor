package com.softwise.trumonitor.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.viewHolders.DeviceViewHolder;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
    private BluetoothDevice[] deviceList = new BluetoothDevice[0];
    private final OnDeviceSelectListeners mOnDeviceSelectListeners;

    public interface OnDeviceSelectListeners {
        void deviceSelect(BluetoothDevice bluetoothDevice);
    }

    public DeviceAdapter(OnDeviceSelectListeners onDeviceSelectListeners) {
        this.mOnDeviceSelectListeners = onDeviceSelectListeners;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder deviceViewHolder, @SuppressLint("RecyclerView") int i) {
        deviceViewHolder.setupView(this.deviceList[i]);
        deviceViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDeviceSelectListeners.deviceSelect(deviceList[i]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.deviceList.length;
    }

    public void updateList(Collection<BluetoothDevice> collection) {
        this.deviceList = (BluetoothDevice[]) collection.toArray(new BluetoothDevice[0]);
        notifyDataSetChanged();
    }
}
