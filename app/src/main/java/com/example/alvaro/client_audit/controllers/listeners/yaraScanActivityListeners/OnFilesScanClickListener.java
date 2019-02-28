package com.example.alvaro.client_audit.controllers.listeners.yaraScanActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.yaraActivities.YaraScanActivity;

public class OnFilesScanClickListener implements View.OnClickListener {

    private YaraScanActivity activity;

    public OnFilesScanClickListener(YaraScanActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.start_file_search();
    }
}
