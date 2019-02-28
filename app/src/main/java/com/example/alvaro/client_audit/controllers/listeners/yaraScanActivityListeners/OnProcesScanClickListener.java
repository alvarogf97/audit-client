package com.example.alvaro.client_audit.controllers.listeners.yaraScanActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.yaraActivities.YaraScanActivity;

public class OnProcesScanClickListener implements View.OnClickListener {

    private YaraScanActivity activity;

    public OnProcesScanClickListener(YaraScanActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.start_process_scanner();
    }

}
