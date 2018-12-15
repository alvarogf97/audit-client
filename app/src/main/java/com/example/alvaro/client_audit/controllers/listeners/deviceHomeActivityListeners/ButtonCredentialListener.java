package com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.DeviceHomeActivity;

public class ButtonCredentialListener implements View.OnClickListener {

    private DeviceHomeActivity activity;

    public ButtonCredentialListener(DeviceHomeActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        activity.showDialog();
    }

}
