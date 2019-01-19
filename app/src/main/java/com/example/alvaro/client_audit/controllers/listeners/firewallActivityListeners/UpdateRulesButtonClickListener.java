package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.ShowRules;

public class UpdateRulesButtonClickListener implements View.OnClickListener {

    private ShowRules activity;

    public UpdateRulesButtonClickListener(ShowRules activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.start_animation();
    }
}
