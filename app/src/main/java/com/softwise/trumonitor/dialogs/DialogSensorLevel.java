package com.softwise.trumonitor.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.helper.MethodHelper;
import com.softwise.trumonitor.implementer.SensorPresenter;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.models.Sensor;


public class DialogSensorLevel extends DialogFragment {
    int alarmHigh;
    int alarmLow;
    private Button btnAlarmSet;
    private Button btnWarningSet;
    private EditText edtAlarmHigh;
    private EditText edtAlarmLow;
    private EditText edtHour1;
    private EditText edtHour2;
    private EditText edtMin1;
    private EditText edtMin2;
    private EditText edtSec1;
    private EditText edtSec2;
    private EditText edtWarningHigh;
    private EditText edtWarningLow;
    private Context mContext;
    /* access modifiers changed from: private */
    public Sensor mEntitySensor;
    /* access modifiers changed from: private */
    public OnAddSensorLevelListeners mOnAddSensorLevelListeners;
    private SeekBar seekbarAlarm;
    private SeekBar seekbarWarning;
    private TextView txtAlarmHigh;
    private TextView txtAlarmLow;
    /* access modifiers changed from: private */
    public TextView txtAlarmProgress;
    private TextView txtCancel;
    private TextView txtSave;
    private TextView txtSensorId;
    private TextView txtWarningHigh;
    private TextView txtWarningLow;
    /* access modifiers changed from: private */
    public TextView txtWarningProgress;
    int warningHigh;
    int warningLow;

    public interface OnAddSensorLevelListeners {
        void onAddSensorLevel(Sensor sensor);
    }

    public DialogSensorLevel(Context context, Sensor sensor, OnAddSensorLevelListeners onAddSensorLevelListeners) {
        this.mContext = context;
        this.mEntitySensor = sensor;
        this.mOnAddSensorLevelListeners = onAddSensorLevelListeners;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(STYLE_NORMAL, R.style.MY_DIALOG);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.layout_sensor_level_unit, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((FragmentActivity) requireActivity()).setTitle("Add Sensor Data");
        initView(view);
        clickListeners();
    }

    private void initView(View view) {
        this.txtSensorId = (TextView) view.findViewById(R.id.txt_sensor_id);
        this.edtAlarmLow = (EditText) view.findViewById(R.id.edt_alarm_low);
        this.edtAlarmHigh = (EditText) view.findViewById(R.id.edt_alarm_high);
        this.edtWarningLow = (EditText) view.findViewById(R.id.edt_warning_low);
        this.edtWarningHigh = (EditText) view.findViewById(R.id.edt_warning_high);
        this.txtAlarmLow = (TextView) view.findViewById(R.id.txt_alarm_low);
        this.txtAlarmHigh = (TextView) view.findViewById(R.id.txt_alarm_high);
        this.txtWarningLow = (TextView) view.findViewById(R.id.txt_warning_low);
        this.txtWarningHigh = (TextView) view.findViewById(R.id.txt_warning_high);
        this.edtHour1 = (EditText) view.findViewById(R.id.edt_hour1);
        this.edtHour2 = (EditText) view.findViewById(R.id.edt_hour2);
        this.edtMin1 = (EditText) view.findViewById(R.id.edt_min1);
        this.edtMin2 = (EditText) view.findViewById(R.id.edt_min2);
        this.edtSec1 = (EditText) view.findViewById(R.id.edt_sec1);
        this.edtSec2 = (EditText) view.findViewById(R.id.edt_sec2);
        this.txtSave = (TextView) view.findViewById(R.id.txt_save);
        this.txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        this.seekbarAlarm = (SeekBar) view.findViewById(R.id.seekBar_alarm);
        this.seekbarWarning = (SeekBar) view.findViewById(R.id.seekBar_warning);
        this.btnAlarmSet = (Button) view.findViewById(R.id.btn_alarm_set);
        this.btnWarningSet = (Button) view.findViewById(R.id.btn_warning_set);
        this.txtAlarmProgress = (TextView) view.findViewById(R.id.txt_alarm_progress);
        this.txtWarningProgress = (TextView) view.findViewById(R.id.txt_warning_progress);
        this.txtSensorId.setText(this.mEntitySensor.getSensor_name());
    }

    private void clickListeners() {
        this.seekbarAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i > 50) {
                    DialogSensorLevel.this.txtAlarmProgress.setText(String.valueOf(i - 50));
                } else {
                    DialogSensorLevel.this.txtAlarmProgress.setText(String.valueOf(50 - i));
                }
            }
        });
        this.seekbarWarning.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i > 50) {
                    DialogSensorLevel.this.txtWarningProgress.setText(String.valueOf(i - 50));
                } else {
                    DialogSensorLevel.this.txtWarningProgress.setText(String.valueOf(50 - i));
                }
            }
        });
        this.btnAlarmSet.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DialogSensorLevel.this.lambda$clickListeners$0$DialogSensorLevel(view);
            }
        });
        this.btnWarningSet.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DialogSensorLevel.this.lambda$clickListeners$1$DialogSensorLevel(view);
            }
        });
        this.txtSave.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DialogSensorLevel.this.lambda$clickListeners$2$DialogSensorLevel(view);
            }
        });
        this.txtCancel.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DialogSensorLevel.this.lambda$clickListeners$3$DialogSensorLevel(view);
            }
        });
    }

    public /* synthetic */ void lambda$clickListeners$0$DialogSensorLevel(View view) {
        int parseInt = Integer.parseInt(this.txtAlarmProgress.getText().toString());
        if (this.seekbarAlarm.getProgress() > 50) {
            this.alarmHigh = parseInt;
            TextView textView = this.txtAlarmHigh;
            textView.setText("High : " + parseInt);
        } else {
            this.alarmLow = parseInt;
            TextView textView2 = this.txtAlarmLow;
            textView2.setText("Low : " + parseInt);
        }
        this.txtAlarmProgress.setText("0");
        this.seekbarAlarm.setProgress(50);
    }

    public /* synthetic */ void lambda$clickListeners$1$DialogSensorLevel(View view) {
        int parseInt = Integer.parseInt(this.txtWarningProgress.getText().toString());
        if (this.seekbarWarning.getProgress() > 50) {
            this.warningHigh = parseInt;
            TextView textView = this.txtWarningHigh;
            textView.setText("High : " + parseInt);
        } else {
            this.warningLow = parseInt;
            TextView textView2 = this.txtWarningLow;
            textView2.setText("Low : " + parseInt);
        }
        this.txtWarningProgress.setText("0");
        this.seekbarWarning.setProgress(50);
    }

    public /* synthetic */ void lambda$clickListeners$2$DialogSensorLevel(View view) {
        int i;
        int i2;
        int i3 = this.alarmLow;
        int i4 = this.warningLow;
        if (i3 >= i4 || i3 >= (i = this.warningHigh) || i3 >= (i2 = this.alarmHigh)) {
            MethodHelper.showToast(getContext(), "Alarm/Warning condition does not match");
        } else if (i4 >= i || i4 >= i2) {
            MethodHelper.showToast(getContext(), "Warning levels should be less than Alarm levels");
        } else {
            saveData();
        }
    }

    public /* synthetic */ void lambda$clickListeners$3$DialogSensorLevel(View view) {
        dismiss();
    }

    private void saveData() {
        this.mEntitySensor.setAlarmLow(Integer.valueOf(this.alarmLow));
        this.mEntitySensor.setAlarmHigh(Integer.valueOf(this.alarmHigh));
        this.mEntitySensor.setWarningLow(Integer.valueOf(this.warningLow));
        this.mEntitySensor.setWarningHigh(Integer.valueOf(this.warningHigh));
        String trim = this.edtHour1.getText().toString().trim();
        String trim2 = this.edtHour2.getText().toString().trim();
        String trim3 = this.edtMin1.getText().toString().trim();
        String trim4 = this.edtMin2.getText().toString().trim();
        String trim5 = this.edtSec1.getText().toString().trim();
        String trim6 = this.edtSec2.getText().toString().trim();
        this.mEntitySensor.setUpdateFrequency(trim + trim2 + ":" + trim3 + trim4 + ":" + trim5 + trim6);
        new SensorPresenter(this.mContext).saveUpdateSensorLevelToServer(this.mContext, this.mEntitySensor, new IBooleanListener() {
            public void callBack(boolean z) {
                DialogSensorLevel.this.mOnAddSensorLevelListeners.onAddSensorLevel(DialogSensorLevel.this.mEntitySensor);
                DialogSensorLevel.this.dismiss();
            }
        });
    }
}
