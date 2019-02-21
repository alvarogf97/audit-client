package com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners;

import android.content.Intent;
import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.NetworkAnomaliesActivity;
import com.example.alvaro.client_audit.activities.actionActivities.NetworkActivity;

public class ShowAnomaliesClickListener implements View.OnClickListener {

    private NetworkActivity activity;

    public ShowAnomaliesClickListener(NetworkActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.activity.getApplicationContext(), NetworkAnomaliesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.activity.startActivity(intent);
    }
}
