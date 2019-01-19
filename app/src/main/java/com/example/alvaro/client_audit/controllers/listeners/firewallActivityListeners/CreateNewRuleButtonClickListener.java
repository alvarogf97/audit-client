package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Create;

public class CreateNewRuleButtonClickListener implements View.OnClickListener {

    private Create activity;

    public CreateNewRuleButtonClickListener(Create activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.execute_action();
    }
}
