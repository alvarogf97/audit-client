package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Export;

public class ExportButtonClickListener implements View.OnClickListener {

    private Export activity;

    public ExportButtonClickListener(Export activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        this.activity.execute_action();
    }
}
