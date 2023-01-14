package com.softwise.trumonitor.bluetoothListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softwise.trumonitor.R;


public class FloatingWindow extends Service {
    /* access modifiers changed from: private */
    public EditText edt1;
    private Context mContext;
    /* access modifiers changed from: private */
    public View mView;
    /* access modifiers changed from: private */
    public WindowManager mWindowManager;
    WindowManager.LayoutParams mWindowsParams;
    /* access modifiers changed from: private */
    public boolean wasInFocus = true;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        this.mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        allAboutLayout(intent);
        moveView();
        return super.onStartCommand(intent, i, i2);
    }

    @Override
    public void onDestroy() {
        View view = this.mView;
        if (view != null) {
            this.mWindowManager.removeView(view);
        }
        super.onDestroy();
    }

    private void moveView() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams((int) (((float) displayMetrics.widthPixels) * 1.0f),
                (int) (((float) displayMetrics.heightPixels) * 1.0f), Build.VERSION.SDK_INT <= 25 ? 2002 : 2038, 6816160, -3);
        this.mWindowsParams = layoutParams;
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        ;
        this.mWindowsParams.y = 100;
        this.mWindowManager.addView(this.mView, this.mWindowsParams);
        this.mView.setOnTouchListener(new View.OnTouchListener() {
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;
            long startTime = System.currentTimeMillis();

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (System.currentTimeMillis() - this.startTime <= 300) {
                    return false;
                }
                FloatingWindow floatingWindow = FloatingWindow.this;
                if (floatingWindow.isViewInBounds(floatingWindow.mView, (int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    FloatingWindow.this.editTextReceiveFocus();
                } else {
                    FloatingWindow.this.editTextDontReceiveFocus();
                }
                int action = motionEvent.getAction();
                if (action == 0) {
                    this.initialX = FloatingWindow.this.mWindowsParams.x;
                    this.initialY = FloatingWindow.this.mWindowsParams.y;
                    this.initialTouchX = motionEvent.getRawX();
                    this.initialTouchY = motionEvent.getRawY();
                } else if (action == 2) {
                    FloatingWindow.this.mWindowsParams.x = this.initialX + ((int) (motionEvent.getRawX() - this.initialTouchX));
                    FloatingWindow.this.mWindowsParams.y = this.initialY + ((int) (motionEvent.getRawY() - this.initialTouchY));
                    FloatingWindow.this.mWindowManager.updateViewLayout(FloatingWindow.this.mView, FloatingWindow.this.mWindowsParams);
                }
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean isViewInBounds(View view, int i, int i2) {
        Rect rect = new Rect();
        int[] iArr = new int[2];
        view.getDrawingRect(rect);
        view.getLocationOnScreen(iArr);
        rect.offset(iArr[0], iArr[1]);
        return rect.contains(i, i2);
    }

    /* access modifiers changed from: private */
    public void editTextReceiveFocus() {
        if (!this.wasInFocus) {
            this.mWindowsParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            this.mWindowManager.updateViewLayout(this.mView, this.mWindowsParams);
            this.wasInFocus = true;
        }
    }

    /* access modifiers changed from: private */
    public void editTextDontReceiveFocus() {
        if (this.wasInFocus) {
            this.mWindowsParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            this.mWindowManager.updateViewLayout(this.mView, this.mWindowsParams);
            this.wasInFocus = false;
            hideKeyboard(this.mContext, this.edt1);
        }
    }

    private void allAboutLayout(Intent intent) {
        View inflate = ((LayoutInflater) this.mContext.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ovelay_window, (ViewGroup) null);
        this.mView = inflate;
        this.edt1 = (EditText) inflate.findViewById(R.id.edt1);
        final TextView textView = (TextView) this.mView.findViewById(R.id.tvValue);
        this.edt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FloatingWindow.this.mWindowsParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
                ;
                FloatingWindow.this.mWindowsParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
                FloatingWindow.this.mWindowManager.updateViewLayout(FloatingWindow.this.mView, FloatingWindow.this.mWindowsParams);
                boolean unused = FloatingWindow.this.wasInFocus = true;
                FloatingWindow.this.showSoftKeyboard(view);
            }
        });
        this.edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                textView.setText(FloatingWindow.this.edt1.getText());
            }
        });
        ((Button) this.mView.findViewById(R.id.btnClose)).setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                FloatingWindow.this.stopSelf();
            }
        });
    }

    private void hideKeyboard(Context context, View view) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(view, 1);
        }
    }
}
