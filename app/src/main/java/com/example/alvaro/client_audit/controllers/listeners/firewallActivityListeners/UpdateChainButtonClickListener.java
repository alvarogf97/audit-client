package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.ChainActivity;

public class UpdateChainButtonClickListener implements View.OnClickListener {

    private ChainActivity activity;

    public UpdateChainButtonClickListener(ChainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.get_chains();
    }
}
