package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;

public class ButtonUpdateStatusListener implements View.OnClickListener {

    private FirewallActivity activity;

    public ButtonUpdateStatusListener(FirewallActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.update_status();
    }
}
