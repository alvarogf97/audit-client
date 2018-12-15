package com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners;

import android.view.View;
import com.example.alvaro.client_audit.activities.DeviceHomeActivity;
import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;

public class ButtonCancelDialogListener implements View.OnClickListener {

    private DeviceHomeActivity activity;

    public ButtonCancelDialogListener(DeviceHomeActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.hide_dialog();
    }
}
