package com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.NetworkActivity;

public class ShowAnomaliesClickListener implements View.OnClickListener {

    private NetworkActivity activity;

    public ShowAnomaliesClickListener(NetworkActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        
    }
}
