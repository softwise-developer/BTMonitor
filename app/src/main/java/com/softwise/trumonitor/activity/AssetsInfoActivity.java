package com.softwise.trumonitor.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.adapter.AssetsAdapter;
import com.softwise.trumonitor.adapter.SensorsLevelAdapter;
import com.softwise.trumonitor.database.DatabaseClient;
import com.softwise.trumonitor.databinding.ActivityAssetsInfoBinding;
import com.softwise.trumonitor.dialogs.DialogSensorLevel;
import com.softwise.trumonitor.helper.DialogHelper;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.helper.ServerDatabaseHelper;
import com.softwise.trumonitor.implementer.SensorPresenter;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.listeners.IBooleanWithDialogListener;
import com.softwise.trumonitor.listeners.IObserveDataListener;
import com.softwise.trumonitor.listeners.SerialListener;
import com.softwise.trumonitor.models.AssetAndSensorInfo;
import com.softwise.trumonitor.models.RefreshTokenResponse;
import com.softwise.trumonitor.models.Sensor;
import com.softwise.trumonitor.models.SensorIds;
import com.softwise.trumonitor.serverUtils.ApiClients;
import com.softwise.trumonitor.serverUtils.ServiceListeners.APIService;
import com.softwise.trumonitor.utils.BluetoothConstants;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.softwise.trumonitor.utils.TextUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.softwise.trumonitor.utils.BluetoothConstants.BEARER;
import static com.softwise.trumonitor.utils.BluetoothConstants.CONTENT_TYPE;


public class AssetsInfoActivity extends ConnectivityActivity implements AssetsAdapter.OnAssetsSelectListeners,
        IObserveDataListener, SensorsLevelAdapter.OnSensorLevelSelectListeners, DialogSensorLevel.OnAddSensorLevelListeners, SerialListener {
    private int backCount;
    private IntentFilter filter;
    /* access modifiers changed from: private */
    public AssetAndSensorInfo mAssetAndSensorInfo;
    /* access modifiers changed from: private */
    public List<AssetAndSensorInfo> mAssetAndSensorInfoList = new ArrayList();
    private AssetsAdapter mAssetsAdapter;
    ActivityAssetsInfoBinding mBinding;
    private SensorsLevelAdapter mSensorsAdapter;
    private String newline = TextUtil.newline_crlf;
    private BroadcastReceiver receiveData;
    private StringBuilder receivedMessage;
    private SearchView searchView;
    List<Sensor> sensorList = new ArrayList();

    @Override
    public void dataOnComplete() {
    }

    @Override
    public void errorException(Throwable th) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityAssetsInfoBinding inflate = ActivityAssetsInfoBinding.inflate(getLayoutInflater());
        this.mBinding = inflate;
        setContentView((View) inflate.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Assets info");

        Intent intent = getIntent();
       /* if (intent != null) {
            if (intent.getStringExtra("MESSAGE") != null) {
                onReceiveSensorData(intent.getStringExtra("MESSAGE"));
            }else {*/
                getAssetsInFoFromSever(getApplicationContext());
                clickListeners();
                IntentFilter intentFilter = new IntentFilter();
                this.filter = intentFilter;
                intentFilter.addAction("sensorData");
                receiveData = new BroadcastReceiver() {

                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent.getExtras().get("msg") != null) {
                            onReceiveSensorData(String.valueOf(intent.getExtras().get("msg")));
                        }
                    }
                };
                registerReceiver(receiveData, filter);
           // }
       // }


    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2) {
            onReceiveSensorData(intent.getStringExtra("msg"));
        }
    }

    public void onReceiveSensorData(String str) {
        if ("date time receive successfully".equals(str)) {
            DialogHelper.dismissProgressDialog();
            DialogHelper.conformationDialogCallBack(AssetsInfoActivity.this, getString(R.string.msg_app_ready_to_use), new IBooleanWithDialogListener() {
                @Override
                public void callBack(boolean z, Dialog dialog) {
                    if (z) {
                        try {
                            dialog.dismiss();
                            Intent intent = new Intent(AssetsInfoActivity.this, SensorTemperatureActivity.class);
                            intent.putExtra("asset_id", AssetsInfoActivity.this.mAssetAndSensorInfo.getAssetId());
                           // intent.putExtra("asset_id", SPTrueTemp.getAssetsId(AssetsInfoActivity.this));
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        AssetsInfoActivity.this.deallocateSensorFromAssets();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void deallocateSensorFromAssets() {
        try {
            new SensorPresenter(getApplicationContext()).deallocateSensorFromAsset(getApplicationContext(), this.mAssetAndSensorInfo.getAssetId().intValue(), new IBooleanListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void callBack(boolean z) {
                    if (z) {
                        SPTrueTemp.clearConnectedAddress(AssetsInfoActivity.this.getApplicationContext());
                        Intent intent = new Intent(AssetsInfoActivity.this, LauncherActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        AssetsInfoActivity.this.startActivity(intent);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void getAssetsInFoFromSever(Context context) {
        String org = String.valueOf(SPTrueTemp.getUserOrg(context));
        String userId = String.valueOf(SPTrueTemp.getUserId(context));
        String levels = SPTrueTemp.getUserLevel(context);
        String token = SPTrueTemp.getToken(getApplicationContext());
        Log.e("Token",token);
        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);
        Observable<List<AssetAndSensorInfo>> observable = apiService.getAllAssetsData(BEARER+" "+token,CONTENT_TYPE, org)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<List<AssetAndSensorInfo>>() {
       /* ((APIService) ApiClients.getRetrofitInstance(false).create(APIService.class)).getAllAssetsData("Bearer " + token,
                BluetoothConstants.CONTENT_TYPE, valueOf).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<AssetAndSensorInfo>>() {*/
            @Override
            public void onCompleted() {
                AssetsInfoActivity.this.mBinding.inc.prbLoad.setVisibility(View.GONE);
            }
            @Override
            public void onError(Throwable th) {
                th.printStackTrace();
                Log.e("Error",th.getMessage());
                AssetsInfoActivity.this.mBinding.inc.prbLoad.setVisibility(View.GONE);
                if ("HTTP 401 Unauthorized".equals(th.getMessage())) {
                    AssetsInfoActivity assetsInfoActivity = AssetsInfoActivity.this;
                    assetsInfoActivity.callRefreshToken(assetsInfoActivity.getApplicationContext());
                }
            }

            @Override
            public void onNext(List<AssetAndSensorInfo> list) {
                if (list == null || list.size() <= 0) {
                    AssetsInfoActivity.this.mBinding.inc.recyclerAssets.setVisibility(View.GONE);
                    AssetsInfoActivity.this.mBinding.inc.recyclerSensor.setVisibility(View.GONE);
                    AssetsInfoActivity.this.mBinding.inc.prbLoad.setVisibility(View.GONE);
                    AssetsInfoActivity.this.mBinding.inc.txtNoData.setVisibility(View.VISIBLE);
                    return;
                }
                AssetsInfoActivity.this.mAssetAndSensorInfoList.addAll(list);
                AssetsInfoActivity.this.mBinding.inc.recyclerAssets.setVisibility(View.VISIBLE);
                AssetsInfoActivity.this.mBinding.inc.recyclerSensor.setVisibility(View.GONE);
                AssetsInfoActivity.this.mBinding.inc.txtNoData.setVisibility(View.GONE);
                AssetsInfoActivity.this.initRecyclerView();
            }
        });
    }

    /* access modifiers changed from: private */
    public void initRecyclerView() {
        this.mBinding.inc.recyclerAssets.setLayoutManager(new LinearLayoutManager(this));
        this.mBinding.inc.recyclerAssets.setItemAnimator(new DefaultItemAnimator());
        this.mAssetsAdapter = new AssetsAdapter(this, this.mAssetAndSensorInfoList, new AssetsAdapter.OnAssetsSelectListeners() {
            @Override
            public final void assetsSelect(AssetAndSensorInfo assetAndSensorInfo) {
                AssetsInfoActivity.this.assetsSelect(assetAndSensorInfo);
            }
        });
        this.mBinding.inc.recyclerAssets.setAdapter(this.mAssetsAdapter);
    }

    private void initRecyclerViewForSensor(List<Sensor> list) {
        this.mBinding.inc.recyclerSensor.setLayoutManager(new LinearLayoutManager(this));
        this.mBinding.inc.recyclerSensor.setItemAnimator(new DefaultItemAnimator());
        this.mSensorsAdapter = new SensorsLevelAdapter(this, list, new SensorsLevelAdapter.OnSensorLevelSelectListeners() {
            @Override
            public final void onSensorLevelSelect(Sensor sensor) {
                AssetsInfoActivity.this.onSensorLevelSelect(sensor);
            }
        });
        this.mBinding.inc.recyclerSensor.setAdapter(this.mSensorsAdapter);
    }

    private void clickListeners() {
        this.mBinding.inc.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                if (mAssetAndSensorInfo != null && sensorList.size() > 0) {
                    ServerDatabaseHelper.getInstance(getApplicationContext()).saveSensorListInLocalDB(getApplicationContext(), sensorList, new IBooleanListener() {
                        @Override
                        public void callBack(boolean z) {
                            if (z) {
                                DialogHelper.showProgressDialog(AssetsInfoActivity.this, "Please wait");
                                String createSensorDataString = MethodHelper.createSensorDataString(AssetsInfoActivity.this.sensorList, AssetsInfoActivity.this.mAssetAndSensorInfo.getAssetId().intValue());
                                Log.e("sensorDatafromdb", createSensorDataString);
                                SPTrueTemp.saveAssetsId(AssetsInfoActivity.this, String.valueOf(mAssetAndSensorInfo.getAssetId()));
                                AssetsInfoActivity.this.send(createSensorDataString);
                                return;
                            }
                            MethodHelper.showToast(AssetsInfoActivity.this.getApplicationContext(), AssetsInfoActivity.this.getString(R.string.msg_something_went_wrong));
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void callRefreshToken(final Context context) {
        ((APIService) ApiClients.getRetrofitInstance(false).create(APIService.class)).refreshToken(Integer.parseInt(SPTrueTemp.getUserId(context))).enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable th) {
            }

            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response != null) {
                    SPTrueTemp.saveToken(context, response.body().getMessage());
                    AssetsInfoActivity.this.getAssetsInFoFromSever(context);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView2 = (SearchView) menu.findItem(R.id.mn_search).getActionView();
        this.searchView = searchView2;
        searchView2.setSearchableInfo(((SearchManager) getSystemService(FirebaseAnalytics.Event.SEARCH)).getSearchableInfo(getComponentName()));
        this.searchView.setMaxWidth(Integer.MAX_VALUE);
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String str) {
                AssetsInfoActivity.this.search(str);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String str) {
                AssetsInfoActivity.this.search(str);
                return false;
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public void search(String str) {
        if (this.mAssetAndSensorInfoList.size() <= 0) {
            return;
        }
        if ("".equals(str)) {
            this.mAssetsAdapter.updateList(this.mAssetAndSensorInfoList);
        } else {
            this.mAssetsAdapter.getFilter().filter(str);
        }
    }

    private void exitFromScreen() {
        if (!this.searchView.isIconified()) {
            this.searchView.setIconified(true);
            return;
        }
        Intent intent = new Intent(this, PairedDeviceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        System.exit(0);
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.mn_search) {
            return true;
        }
        if (itemId != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (this.backCount > 0) {
            this.mBinding.inc.recyclerAssets.setVisibility(View.VISIBLE);
            this.mBinding.inc.recyclerSensor.setVisibility(View.GONE);
            this.backCount = 0;
        } else {
            exitFromScreen();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.backCount > 0) {
            this.mBinding.inc.recyclerAssets.setVisibility(View.VISIBLE);
            this.mBinding.inc.recyclerSensor.setVisibility(View.GONE);
            this.backCount = 0;
            return;
        }
        exitFromScreen();
    }

    @Override
    public void assetsSelect(AssetAndSensorInfo assetAndSensorInfo) {
        this.backCount++;
        this.mAssetAndSensorInfo = new AssetAndSensorInfo();
        this.mAssetAndSensorInfo = assetAndSensorInfo;
        this.mBinding.inc.prbLoad.setVisibility(View.VISIBLE);
        this.mBinding.inc.txtAssetName.setVisibility(View.VISIBLE);
        this.mBinding.inc.txtAssetName.setText(assetAndSensorInfo.getAssetName());
        String sensorId = SPTrueTemp.getSensorId(getApplicationContext());
        Log.e("sensor id", sensorId);
        SensorIds orCreateEntitySensorList = MethodHelper.setOrCreateEntitySensorList(assetAndSensorInfo.getAssetId().intValue(), sensorId);
        Log.e("sensor id", orCreateEntitySensorList.toString());
        if (orCreateEntitySensorList != null) {
            new SensorPresenter(getApplicationContext(), this).sendAssetAndSensorToServer(getApplicationContext(), orCreateEntitySensorList);
        }
    }

    @Override
    public void nextDataLoad(AssetAndSensorInfo assetAndSensorInfo) {
        if (assetAndSensorInfo != null) {
            this.sensorList.clear();
            List<Sensor> sensors = assetAndSensorInfo.getSensors();
            this.sensorList = sensors;
            if (sensors != null && sensors.size() > 0) {
                initRecyclerViewForSensor(this.sensorList);
                this.mBinding.inc.prbLoad.setVisibility(View.GONE);
                this.mBinding.inc.btnContinue.setVisibility(View.VISIBLE);
                this.mBinding.inc.recyclerAssets.setVisibility(View.GONE);
                this.mBinding.inc.recyclerSensor.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSensorLevelSelect(Sensor sensor) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.addOnBackStackChangedListener((FragmentManager.OnBackStackChangedListener) null);
        DialogSensorLevel dialogSensorLevel = new DialogSensorLevel(getApplicationContext(), sensor, this);
        dialogSensorLevel.setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
        dialogSensorLevel.show(supportFragmentManager, "Edit Fragment");
    }

    @Override
    public void onAddSensorLevel(Sensor sensor) {
        for (Sensor next : this.sensorList) {
            if (sensor.getSensorId() == next.getSensorId()) {
                int indexOf = this.sensorList.indexOf(next);
                this.sensorList.get(indexOf).setUpdateFrequency(sensor.getUpdateFrequency());
                this.sensorList.get(indexOf).setAlarmLow(sensor.getAlarmLow());
                this.sensorList.get(indexOf).setAlarmHigh(sensor.getAlarmHigh());
                this.sensorList.get(indexOf).setWarningLow(sensor.getWarningLow());
                this.sensorList.get(indexOf).setWarningHigh(sensor.getWarningHigh());
                this.mSensorsAdapter.notifyDataSetChanged();
                DatabaseClient.getInstance(getApplicationContext()).updateSensorData(MethodHelper.getSingleEntitySensor(getApplicationContext(), sensor));
            }
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(this.receiveData);
        super.onDestroy();
    }
}
