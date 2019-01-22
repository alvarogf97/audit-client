package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Create;
import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.ShowRules;

public class CreateRuleButonClickListener implements View.OnClickListener {

    private Activity activity;

    public CreateRuleButonClickListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.activity.getApplicationContext(), Create.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.activity.startActivity(intent);
    }
}
