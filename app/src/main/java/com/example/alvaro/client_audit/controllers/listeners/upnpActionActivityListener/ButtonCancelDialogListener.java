package com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;

public class ButtonCancelDialogListener implements View.OnClickListener {

    private UpnpActionActivity activity;

    public ButtonCancelDialogListener(UpnpActionActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.hide_dialog();
    }
}
