package com.softwise.trumonitor.activity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.databinding.ActivityLoginBinding;
import com.softwise.trumonitor.helper.DialogHelper;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.models.LoginResponse;
import com.softwise.trumonitor.models.User;
import com.softwise.trumonitor.models.UserCredentials;
import com.softwise.trumonitor.serverUtils.ApiClients;
import com.softwise.trumonitor.serverUtils.ServiceListeners.APIService;
import com.softwise.trumonitor.utils.BluetoothConstants;
import com.softwise.trumonitor.utils.ConnectionUtils;
import com.softwise.trumonitor.utils.SPTrueTemp;


import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    /* access modifiers changed from: protected */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityLoginBinding inflate = ActivityLoginBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView((View) inflate.getRoot());
        clickListeners();
    }

    private void clickListeners() {
        this.binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (isFormValid()) {
                    String email = binding.adtEmail.getText().toString().trim();
                    String password = binding.edtPassword.getText().toString().trim();
                    login(email, password);
                }
            }
        });
    }

    private boolean isFormValid() {
        String trim = this.binding.adtEmail.getText().toString().trim();
        String trim2 = this.binding.edtPassword.getText().toString().trim();
        if (!ConnectionUtils.getConnectivityStatusString(this)) {
            MethodHelper.showToast(this, getString(R.string.msg_no_internet));
            return false;
        } else if (trim.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(trim).matches()) {
            this.binding.ilEmail.setError(getString(R.string.error_invalid_email));
            requestFocus(this.binding.adtEmail);
            return false;
        } else if (!trim2.isEmpty()) {
            return true;
        } else {
            this.binding.ilPassword.setError(getString(R.string.error_invalid_password));
            requestFocus(this.binding.edtPassword);
            return false;
        }
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }

    private void login(String str, String str2) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(str);
        userCredentials.setPassword(str2);
        Observable<LoginResponse> observeOn = ((APIService) ApiClients.getRetrofitInstance(false).create(APIService.class))
                .login(BluetoothConstants.CONTENT_TYPE, userCredentials).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        DialogHelper.showProgressDialog(this, "Please wait....");
        observeOn.subscribe((Observer<? super LoginResponse>) new Observer<LoginResponse>() {
            @Override
            public void onCompleted() {
                DialogHelper.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable th) {
                th.printStackTrace();
                LoginActivity loginActivity = LoginActivity.this;
                MethodHelper.showToast(loginActivity, loginActivity.getString(R.string.msg_check_server_connection));
                DialogHelper.dismissProgressDialog();
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                DialogHelper.dismissProgressDialog();
                User user = loginResponse.getUser();
                Log.e("Login response ", user.toString());
                if (user != null) {
                    SPTrueTemp.saveLoginStatus(LoginActivity.this.getApplicationContext(), true);
                    LoginActivity loginActivity = LoginActivity.this;
                    MethodHelper.showToast(loginActivity, loginActivity.getString(R.string.msg_login_success));
                    MethodHelper.saveUserDataInSP(LoginActivity.this, loginResponse);
                    LoginActivity.this.openActivity();
                    return;
                }
                LoginActivity loginActivity2 = LoginActivity.this;
                MethodHelper.showToast(loginActivity2, loginActivity2.getString(R.string.msg_login_failed));
            }
        });
    }

    /* access modifiers changed from: private */
    public void openActivity() {
        SPTrueTemp.clearConnectedAddress(getApplicationContext());
        MethodHelper.jumpActivity(this, PairedDeviceActivity.class);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
