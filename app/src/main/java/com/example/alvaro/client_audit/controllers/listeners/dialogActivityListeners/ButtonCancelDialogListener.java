package com.example.alvaro.client_audit.controllers.listeners.dialogActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.DialogActivity;
import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;

public class ButtonCancelDialogListener implements View.OnClickListener {

    private DialogActivity activity;

    public ButtonCancelDialogListener(DialogActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.hide_dialog();
    }
}
