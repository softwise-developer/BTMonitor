package com.softwise.trumonitor.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.adapter.SensorsAdapter;
import com.softwise.trumonitor.bluetoothListener.MyService;
import com.softwise.trumonitor.database.EntitySensor;
import com.softwise.trumonitor.databinding.ActivitySensorTemperatureBinding;
import com.softwise.trumonitor.helper.DialogHelper;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.implementer.SensorPresenter;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.listeners.IObserveEntitySensorListener;
import com.softwise.trumonitor.models.SensorTempViewModel;
import com.softwise.trumonitor.utils.BluetoothConstants;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.softwise.trumonitor.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;


public class SensorTemperatureActivity extends AppCompatActivity implements SensorsAdapter.OnSensorTempSelectListeners, IObserveEntitySensorListener {
    /* access modifiers changed from: private */
    public int assetId;
    private int callType = 0;
    /* access modifiers changed from: private */
    public EntitySensor entitySensor;
    private List<EntitySensor> entitySensorsList;
    private boolean hexEnabled = false;
    ActivitySensorTemperatureBinding mBinding;
    private BluetoothAdapter mBluetoothAdapter;
    private SensorTempViewModel mSensorTempViewModel;
    private SensorsAdapter mSensorsAdapter;
    private String newline = TextUtil.newline_crlf;
    private BroadcastReceiver receiveData;
    /* access modifiers changed from: private */
    public SensorPresenter sensorPresenter;

    @Override
    public void getEntitySensor(EntitySensor entitySensor2) {
    }

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivitySensorTemperatureBinding inflate = ActivitySensorTemperatureBinding.inflate(getLayoutInflater());
        this.mBinding = inflate;
        setContentView((View) inflate.getRoot());
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            setTitle("Temperature");
            SPTrueTemp.saveCallingType(getApplicationContext(), "ST");
            receiveTempDataByReceiver();
            this.entitySensorsList = new ArrayList();
            this.sensorPresenter = new SensorPresenter(this);
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (getIntent().getExtras() != null) {
                this.assetId = Integer.parseInt(String.valueOf(getIntent().getExtras().get("asset_id")));
            }
            initRecyclerViewForSensor();
            initViewModel();
            this.mBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public final void onRefresh() {
                    SensorTemperatureActivity.this.getDataFromLocalDB();
                }
            });
            receiveData = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    try {
                        if (intent.getExtras() != null) {
                            if (intent.getExtras().get("msg" )!= null) {
                                EntitySensor unused = SensorTemperatureActivity.this.entitySensor = (EntitySensor) intent.getParcelableExtra("msg");
                                SensorTemperatureActivity.this.setTempData(SensorTemperatureActivity.this.entitySensor);
                            }
                            if (intent.getExtras().get("error") != null) {
                                SensorTemperatureActivity.this.showErrorDialog();
                                return;
                            }
                            return;
                        }
                        Log.e("Extra", "Intent getExtras null");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveTempDataByReceiver() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.receiveData, new IntentFilter("sensorTemp"));
    }

    private void initViewModel() {
        try {
            SensorTempViewModel sensorTempViewModel = (SensorTempViewModel) new ViewModelProvider(this).get(SensorTempViewModel.class);
            this.mSensorTempViewModel = sensorTempViewModel;
            if (!sensorTempViewModel.setupViewModel(this)) {
                Log.e("View Model", "Issue in view model");
                finish();
            }
            getDataFromLocalDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void getDataFromLocalDB() {
        SensorTempViewModel sensorTempViewModel = this.mSensorTempViewModel;
        if (sensorTempViewModel != null) {
            sensorTempViewModel.fetchSensorData().observe(this, new Observer() {
                @Override
                public final void onChanged(Object obj) {
                    SensorTemperatureActivity.this.loadTemperature((List) obj);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void loadTemperature(List<EntitySensor> list) {
        if (list.size() > 0) {
            this.callType++;
            this.entitySensorsList.clear();
            this.entitySensorsList.addAll(list);
            this.mSensorsAdapter.notifyDataSetChanged();
            this.mBinding.recyclerSensor.setVisibility(View.VISIBLE);
            this.mBinding.txtEmptyData.setVisibility(View.GONE);
            this.mBinding.prbLoad.setVisibility(View.GONE);
            if (this.callType == 1) {
                send("send temp data");
                return;
            }
            return;
        }
        this.mBinding.recyclerSensor.setVisibility(View.GONE);
        this.mBinding.txtEmptyData.setVisibility(View.GONE);
        this.mBinding.prbLoad.setVisibility(View.GONE);
        this.mBinding.incBattery.cardParent.setVisibility(View.GONE);
    }

    private void initRecyclerViewForSensor() {
        try {
            this.mBinding.recyclerSensor.setLayoutManager(new LinearLayoutManager(this));
            this.mBinding.recyclerSensor.setItemAnimator(new DefaultItemAnimator());
            this.mSensorsAdapter = new SensorsAdapter(this, this.entitySensorsList, new SensorsAdapter.OnSensorTempSelectListeners() {
                @Override
                public final void onSensorSelect(EntitySensor entitySensor) {
                    SensorTemperatureActivity.this.onSensorSelect(entitySensor);
                }
            });
            this.mBinding.recyclerSensor.setAdapter(this.mSensorsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnectBluetooth() {
        try {
            DialogHelper.conformationDialog(SensorTemperatureActivity.this, String.valueOf(Html.fromHtml(getResources().getString(R.string.msg_disconnect))), new IBooleanListener() {
                @Override
                public final void callBack(boolean z) {
                    if (z) {
                        disableBluetooth();
                        openYourActivity((String) null);
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void shutdownBluetooth() {
        try {
            if (this.entitySensorsList == null || this.entitySensorsList.size() <= 0) {
                openYourActivity((String) null);
                finish();
                return;
            }
            DialogHelper.conformationDialog(SensorTemperatureActivity.this, String.valueOf(Html.fromHtml(getResources().getString(R.string.msg_stop_reading))), new IBooleanListener() {
                @Override
                public void callBack(boolean z) {
                    if (z) {
                        SensorTemperatureActivity.this.sensorPresenter.deallocateSensorFromAsset(SensorTemperatureActivity.this.getApplicationContext(), SensorTemperatureActivity.this.assetId, new IBooleanListener() {
                           @Override
                            public void callBack(boolean z) {
                                if (z) {
                                    SensorTemperatureActivity.this.send("N");
                                    SensorTemperatureActivity.this.openYourActivity((String) null);
                                    SensorTemperatureActivity.this.finish();
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableBluetooth() {
        if (this.mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
    }

    private void logoutDeallocateSensors() {
        try {
            DialogHelper.conformationDialog(SensorTemperatureActivity.this, getResources().getString(R.string.msg_logout), new IBooleanListener() {
              @Override
                public final void callBack(boolean z) {
                  if (z) {
                      try {
                          SensorPresenter sensorPresenter2 = new SensorPresenter(getApplicationContext());
                          send("N");
                          logoutFromApp(sensorPresenter2);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void logoutFromApp(SensorPresenter sensorPresenter2) {
        try {
            sensorPresenter2.logout(this, this.assetId, new IBooleanListener() {
              @Override
                public void callBack(boolean z) {
                    if (z) {
                        try {
                            SPTrueTemp.clearSharedPref(SensorTemperatureActivity.this.getApplicationContext());
                            SensorTemperatureActivity.this.openYourActivity("L");
                            SensorTemperatureActivity.this.finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        MethodHelper.showToast(SensorTemperatureActivity.this.getApplicationContext(), SensorTemperatureActivity.this.getString(R.string.msg_something_went_wrong));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void openYourActivity(String str) {
        Intent intent;
        try {
            stopService();
            ConnectivityActivity.service.disconnect();
            SPTrueTemp.clearConnectedAddress(getApplicationContext());
            MethodHelper.stopAlarm();
            if ("L".equals(str)) {
                intent = new Intent(this, LauncherActivity.class);
            } else {
                intent = new Intent(this, PairedDeviceActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void showErrorDialog() {
        try {
            MethodHelper.playAssetSound(getApplicationContext(), 0);
            DialogHelper.messageDialog(SensorTemperatureActivity.this, "Warning", getResources().getString(R.string.status_connection_failed), new IBooleanListener() {
              @Override
                public final void callBack(boolean z) {
                  if (z && ConnectivityActivity.service != null) {
                      openYourActivity((String) null);
                      finish();
                  }                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            shutdownBluetooth();
        }
        if (itemId == R.id.mn_ble_disconnect) {
            disconnectBluetooth();
        }
        if (itemId == R.id.mn_logout) {
            logoutDeallocateSensors();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void send(String str) {
        byte[] bArr;
        try {
            if (this.hexEnabled) {
                StringBuilder sb = new StringBuilder();
                TextUtil.toHexString(sb, TextUtil.fromHexString(str));
                TextUtil.toHexString(sb, this.newline.getBytes());
                bArr = TextUtil.fromHexString(sb.toString());
            } else {
                bArr = (str + this.newline).getBytes();
            }
            ConnectivityActivity.service.write(bArr);
            Log.e("Send Data ST", str);
        } catch (Exception e) {
            ConnectivityActivity.service.onSerialIoError(e);
        }
    }

    @Override
    public void onSensorSelect(EntitySensor entitySensor2) {
        Intent intent = new Intent(this, SensorGraphActivity.class);
        intent.putExtra("sensor", entitySensor2);
        startActivity(intent);
    }

    private void displayBatteryValue(String str) {
        try {
            if (!"0.00".equals(str)) {
                if (!"00.00".equals(str)) {
                    this.mBinding.incBattery.cardParent.setVisibility(View.GONE);
                    this.mBinding.incBattery.progressBarFull.setProgress(100);
                    this.mBinding.incBattery.lblBattery.setText(SPTrueTemp.getConnectedBluetooth(getApplicationContext()) + " Battery Level");
                    float parseFloat = Float.parseFloat(str);
                    this.mBinding.incBattery.txtBattery.setText(Math.round(parseFloat) + "%");
                    this.mBinding.incBattery.progressBar.setProgress(Math.round(parseFloat));
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void setTempData(EntitySensor entitySensor2) {
        try {
            displayBatteryValue(SPTrueTemp.getBatteryLevel(getApplicationContext()));
            for (EntitySensor next : this.entitySensorsList) {
                if (entitySensor2.getBle_sensor_id() == next.getBle_sensor_id()) {
                    next.setTemp_value(entitySensor2.getTemp_value());
                    next.setUnit(entitySensor2.getUnit());
                    this.mSensorsAdapter.notifyDataSetChanged();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.receiveData);
            Log.e("Ondestroy ", "destroy ST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        shutdownBluetooth();
    }

    public void stopService() {
        try {
            Intent intent = new Intent(this, MyService.class);
            intent.setAction(BluetoothConstants.ACTION.STOP_ACTION);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
