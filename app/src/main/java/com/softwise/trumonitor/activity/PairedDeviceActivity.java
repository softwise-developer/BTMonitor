package com.softwise.trumonitor.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.module.ManifestParser;
import com.softwise.trumonitor.R;
import com.softwise.trumonitor.adapter.DeviceAdapter;
import com.softwise.trumonitor.bluetoothListener.DataUploadService;
import com.softwise.trumonitor.databinding.ActivityPairedDeviceBinding;
import com.softwise.trumonitor.helper.DialogHelper;
import com.softwise.trumonitor.implementer.SensorPresenter;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.models.PairedDevViewModel;
import com.softwise.trumonitor.models.SensorIds;
import com.softwise.trumonitor.receiver.NetworkChangeReceiver;
import com.softwise.trumonitor.utils.SPTrueTemp;

import java.util.Collection;


public class PairedDeviceActivity extends AppCompatActivity implements DeviceAdapter.OnDeviceSelectListeners, NetworkChangeReceiver.NetworkChangeCallback {
    String TAG = "PairedDeviceActivity";
    DeviceAdapter adapter;
    ActivityPairedDeviceBinding binding;
    NetworkChangeReceiver networkChangeReceiver;
    View view;
    private PairedDevViewModel viewModel;
    private int REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1;
    private int REQUEST_CODE_LOCATION_PERMISSIONS = 2;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityPairedDeviceBinding inflate = ActivityPairedDeviceBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        CoordinatorLayout root = inflate.getRoot();
        this.view = root;
        setContentView((View) root);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.networkChangeReceiver = new NetworkChangeReceiver(this);
        setUpViewModels();
        clickListeners();
        refreshToken();
        onStartJobIntentService(this.view);
    }

    public void onStartJobIntentService(View view2) {
        Intent intent = new Intent(this, DataUploadService.class);
        Log.e("called Data", " Upload service");
        intent.putExtra("maxCountValue", 1000);
        intent.putExtra("callingType", "M");
        DataUploadService.enqueueWork(this, intent);
    }

    private void refreshToken() {
        new SensorPresenter(getApplicationContext()).callRefreshToken(this, (SensorIds) null);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        checkBluetoothConnectivity();
        registerReceiver(this.networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        NetworkChangeReceiver networkChangeReceiver2 = this.networkChangeReceiver;
        if (networkChangeReceiver2 != null) {
            unregisterReceiver(networkChangeReceiver2);
        }
    }

    private void setUpViewModels() {
        PairedDevViewModel pairedDevViewModel = (PairedDevViewModel) new ViewModelProvider(this).get(PairedDevViewModel.class);
        this.viewModel = pairedDevViewModel;
        if (!pairedDevViewModel.setupViewModel()) {
            finishAffinity();
        }
    }

    private void clickListeners() {
        this.binding.fab.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                startActivity(new Intent("android.settings.BLUETOOTH_SETTINGS"));
            }
        });
    }


    private void checkBluetoothConnectivity() {
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
            DialogHelper.showMessageDialog(PairedDeviceActivity.this, "Warning", getString(R.string.bluetooth_not_available_message));
        }
        if (checkBluetoothAvailability()) {
            checkBluetoothPermission(getApplicationContext());
        }
    }

    private void loadBluetoothDevices() {
        initRecyclerView();
        initRefreshView();
        this.viewModel.getPairedDeviceList().observe(this, new Observer() {

            @Override
            public final void onChanged(Object obj) {
                Collection collection = (Collection) obj;
                if (collection.size() > 0) {
                    adapter.updateList(collection);
                    binding.txtEmptyData.setVisibility(View.GONE);
                    return;
                }
                binding.txtEmptyData.setVisibility(View.VISIBLE);
                binding.txtEmptyData.setText(getString(R.string.enabling_bluetooth));

            }
        });
       this.viewModel.refreshPairedDevices();
    }


    private void initRefreshView() {
        this.binding.mainSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                viewModel.refreshPairedDevices();
                binding.mainSwiperefresh.setRefreshing(false);
            }
        });
    }

    private void requestLocationPermission(Context context) {
        if (ContextCompat.checkSelfPermission(PairedDeviceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(PairedDeviceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(PairedDeviceActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            return;
            // }
        } else {
            openPermissionSetting(context, getResources().getString(R.string.location_permission));

        }
    }

    private void requestBluetoothPermission(Context context) {
        if (ContextCompat.checkSelfPermission(PairedDeviceActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(PairedDeviceActivity.this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(PairedDeviceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(PairedDeviceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(PairedDeviceActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN
                        , Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_BLUETOOTH_PERMISSIONS);
                return;
            }
        } else {
            openPermissionSetting(context, getResources().getString(R.string.bluetooth_location_permission));
        }
    }

    private void openPermissionSetting(Context context, String message) {
        DialogHelper.messageDialogPermission(PairedDeviceActivity.this, message, new IBooleanListener() {
            @Override
            public void callBack(boolean z) {
                if (z) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkBluetoothPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
                // The app has the accurate location permission.
                loadBluetoothDevices();
            } else {
                requestBluetoothPermission(context);
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //The app has the accurate location permission.
                loadBluetoothDevices();
            } else {
                requestLocationPermission(context);
            }
        }
    }

    private boolean checkBluetoothAvailability() {

        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            return true;
        }
        this.binding.txtEmptyData.setVisibility(View.VISIBLE);
        this.binding.txtEmptyData.setText(getString(R.string.enabling_bluetooth));
        return false;
    }

    private void initRecyclerView() {
        this.binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recycler.setItemAnimator(new DefaultItemAnimator());
        this.adapter = new DeviceAdapter(new DeviceAdapter.OnDeviceSelectListeners() {
            public final void deviceSelect(BluetoothDevice bluetoothDevice) {
                PairedDeviceActivity.this.deviceSelect(bluetoothDevice);
            }
        });
        this.binding.recycler.setAdapter(this.adapter);
    }

    private void jumpToActivity(String str, String str2) {
        Intent intent = new Intent(this, ConnectivityActivity.class);
        intent.putExtra("device_name", str);
        intent.putExtra("device_mac", str2);
        SPTrueTemp.saveConnectedBluetoothName(getApplicationContext(), str);
        SPTrueTemp.saveConnectedMacAddress(getApplicationContext(), str2);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void deviceSelect(BluetoothDevice bluetoothDevice) {
        SPTrueTemp.saveCallingType(getApplicationContext(), (String) null);
        jumpToActivity(bluetoothDevice.getName(), bluetoothDevice.getAddress());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.binding.getRoot().removeView(this.view);
    }

    public void onNetworkChanged(boolean z) {
        Log.e("PairedActivity", "Network status " + z);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CODE_BLUETOOTH_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (requestCode != REQUEST_CODE_LOCATION_PERMISSIONS) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Request permission success");
            return;
        }
        Log.e(TAG, "Request permission failed");
    }
}
