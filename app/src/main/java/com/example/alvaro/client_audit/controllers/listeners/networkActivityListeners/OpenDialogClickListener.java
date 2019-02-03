package com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.NetworkActivity;

public class OpenDialogClickListener implements View.OnClickListener {

    private NetworkActivity activity;

    public OpenDialogClickListener(NetworkActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.showDialog();
    }
}
