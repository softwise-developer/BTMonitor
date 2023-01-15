package com.softwise.trumonitor.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.listeners.IBooleanListener;
import com.softwise.trumonitor.listeners.IBooleanWithDialogListener;


public class DialogHelper {
    public static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        mProgressDialog = progressDialog;
        progressDialog.setMax(100);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(str);
        mProgressDialog.setProgressStyle(0);
        mProgressDialog.create();
        mProgressDialog.show();
    }

    public static void dismissProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMessageDialog(final Context context, String str, String str2) {
        try {
            AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
            create.setTitle(str);
            create.setMessage(str2);
            create.setButton(DialogInterface.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    ((Activity) context).finish();
                }
            });
            create.setCancelable(false);
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void conformationDialog(Context context, String str, IBooleanListener iBooleanListener) {
        try {
            AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
            create.setTitle(context.getString(R.string.lbl_confirmation));
            create.setMessage(str);
            create.setButton(DialogInterface.BUTTON_POSITIVE,"Yes",(dialogInterface, i) -> {
                iBooleanListener.callBack(true);
                create.dismiss();
            });
            create.setButton(DialogInterface.BUTTON_NEGATIVE,"No",(dialogInterface, i) -> {
                create.dismiss();
            });
            create.setCancelable(false);
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void conformationDialogCallBack(Context context, String str, IBooleanWithDialogListener iBooleanWithDialogListener) {
        try {
            AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
            create.setTitle(context.getString(
                    R.string.lbl_confirmation));
            create.setMessage(str);
            create.setButton(DialogInterface.BUTTON_POSITIVE,"Yes",(dialogInterface, i) -> {
                iBooleanWithDialogListener.callBack(true,create);
            });

            create.setButton(DialogInterface.BUTTON_NEGATIVE,"No",(dialogInterface, i) -> {
                create.dismiss();
            });
            create.setCancelable(false);
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void messageDialogWithCallBack(Context context, String str, IBooleanListener iBooleanListener) {
        try {
            if (!"SensorTemperatureActivity".equals(String.valueOf(context.getClass().getSimpleName()))) {
                AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
                create.setTitle(context.getString(R.string.lbl_alert_message));
                create.setMessage(str);
                create.setButton(DialogInterface.BUTTON_POSITIVE,"Ok",(dialogInterface, i) -> {
                    iBooleanListener.callBack(true);
                    create.dismiss();
                });
                create.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",(dialogInterface, i) -> {
                    create.dismiss();
                });

                create.setCancelable(false);
                create.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void messageDialogPermission(Context context, String str, IBooleanListener iBooleanListener) {
        try {
                AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
                create.setTitle(context.getString(R.string.lbl_alert_message));
                create.setMessage(str);
                create.setButton(DialogInterface.BUTTON_POSITIVE,"Go To Settings",(dialogInterface, i) -> {
                    iBooleanListener.callBack(true);
                    create.dismiss();
                });
                create.setCancelable(false);
                create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void messageDialog(Context context, String str, String str2, IBooleanListener iBooleanListener) {
        try {
            AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
            create.setTitle(str);
            create.setMessage(str2);
            create.setCancelable(false);
            create.setButton(DialogInterface.BUTTON_POSITIVE,"Okay",(dialogInterface, i) -> {
                iBooleanListener.callBack(true);
                create.dismiss();
            });

            create.setCancelable(false);
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void warningDialog(Context context, String str, String str2, IBooleanListener iBooleanListener) {
        try {
            AlertDialog create = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MY_DIALOG)).create();
            create.setTitle(str);
            create.setMessage(str2);
            create.setCancelable(false);
            create.setButton(DialogInterface.BUTTON_POSITIVE,"Okay",(dialogInterface, i) -> {
                iBooleanListener.callBack(true);
                create.dismiss();
            });
            create.setCancelable(false);
            create.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
