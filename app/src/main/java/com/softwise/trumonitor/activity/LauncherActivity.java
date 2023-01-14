package com.softwise.trumonitor.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.helper.DialogHelper;
import com.softwise.trumonitor.helper.HelperUtils;
import com.softwise.trumonitor.utils.SPTrueTemp;
import com.google.android.gms.location.places.Place;


public class LauncherActivity extends AppCompatActivity {
    private String[] PERMISSIONS_NEEDED;
    private int PERMISSION_ALL_REQ_CODE = 201;
    int count = 0;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash_screen);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(Place.TYPE_SUBLOCALITY_LEVEL_4);
        }
        setTitle("");
        String[] manifestPermissions = HelperUtils.getManifestPermissions(this);
        handleDirection();
       /* this.PERMISSIONS_NEEDED = manifestPermissions;
        if (HelperUtils.hasPermissions(LauncherActivity.this, manifestPermissions)) {
            handleDirection();
        } else {
            ActivityCompat.requestPermissions(LauncherActivity.this, this.PERMISSIONS_NEEDED, this.PERMISSION_ALL_REQ_CODE);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void handleDirection() {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
           @Override
            public void run() {
                if (SPTrueTemp.getLoginStatus(LauncherActivity.this.getApplicationContext())) {
                    LauncherActivity.this.startActivity(new Intent(LauncherActivity.this, PairedDeviceActivity.class));
                } else {
                    LauncherActivity.this.startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                }
                LauncherActivity.this.finish();
            }
        }, (long) getResources().getInteger(R.integer.splashscreen_duration));
    }

    private void showDialog() {
        DialogHelper.showMessageDialog(LauncherActivity.this, "Warning", getString(R.string.msg_permission_not_granted));
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == this.PERMISSION_ALL_REQ_CODE && iArr.length > 0 && iArr[0] == 0) {
            handleDirection();
            return;
        }
        //showDialog();
        //finishAffinity();
    }
}
