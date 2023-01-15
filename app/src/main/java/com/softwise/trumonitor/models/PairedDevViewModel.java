package com.softwise.trumonitor.models;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Collection;

public class PairedDevViewModel extends AndroidViewModel {
    private BluetoothAdapter mBluetoothAdapter;
    private MutableLiveData<Collection<BluetoothDevice>> pairedDeviceList = new MutableLiveData<>();
    private boolean viewModelSetup = false;

    public PairedDevViewModel(Application application) {
        super(application);
    }

    public boolean setupViewModel() {
        if (!this.viewModelSetup) {
            this.viewModelSetup = true;
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return true;
    }

    public void refreshPairedDevices() {
        this.pairedDeviceList.postValue(BluetoothAdapter.getDefaultAdapter().getBondedDevices());
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    public LiveData<Collection<BluetoothDevice>> getPairedDeviceList() {
        ArrayList arrayList = new ArrayList();
        if (this.mBluetoothAdapter != null) {
            for (BluetoothDevice device : mBluetoothAdapter.getBondedDevices()) {
                if (device.getType() !=2 && "ESP32test1".equalsIgnoreCase(device.getName())) {
                    arrayList.add(device);
                }
            }
            this.pairedDeviceList.setValue(arrayList);
        }
        return this.pairedDeviceList;
    }
    // password - mh12dk8802$
}
