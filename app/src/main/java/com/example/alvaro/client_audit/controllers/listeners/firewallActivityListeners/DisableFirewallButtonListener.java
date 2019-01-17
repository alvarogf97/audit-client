package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Disable;

public class DisableFirewallButtonListener implements View.OnClickListener {

    private Disable activity;

    public DisableFirewallButtonListener(Disable activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.start_animation();
    }
}
