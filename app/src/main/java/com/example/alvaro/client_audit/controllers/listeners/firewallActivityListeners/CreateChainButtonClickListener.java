package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.ChainActivity;

public class CreateChainButtonClickListener implements View.OnClickListener {

    private ChainActivity activity;

    public CreateChainButtonClickListener(ChainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.create_chain();
    }
}
