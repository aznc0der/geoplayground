package com.azncoder.geoplayground.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.azncoder.geoplayground.R;

/**
 * Created by aznc0der on 2/1/2018.
 */

public class LoadingDialog extends Dialog {

    private static LoadingDialog requestDialog;

    public LoadingDialog(Context context) {
        super(context, R.style.requestDialog);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading_dialog);
        setCanceledOnTouchOutside(false);
    }

    public static void show(Context context) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        if (requestDialog == null) {
            requestDialog = new LoadingDialog(context);
        }
        requestDialog.show();
    }

    public static void dismiss(Context context) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        if (requestDialog != null) {
            if (requestDialog.isShowing()) {
                try {
                    requestDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            requestDialog = null;
        }
    }
}