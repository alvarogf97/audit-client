package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Disable;
import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Export;
import com.example.alvaro.client_audit.core.entities.FirewallAction;

public class ActionListClickListener implements AdapterView.OnItemClickListener {

    private FirewallActivity activity;

    public ActionListClickListener(FirewallActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FirewallAction selected_action = (FirewallAction) parent.getItemAtPosition(position);
        if(selected_action.getName().equals("disable")){
            Intent intent = new Intent(this.activity.getApplicationContext(), Disable.class);
            this.activity.startActivity(intent);
        }else if(selected_action.getName().equals("enable")){
            this.activity.is_execute_enable = true;
            FirewallAction action_disable = FirewallActivity.getActionByName("enable");
            this.activity.execute_firewall_action(action_disable);
        }else if(selected_action.getName().equals("export settings")){
            Intent intent = new Intent(this.activity.getApplicationContext(), Export.class);
            this.activity.startActivity(intent);
        }
    }
}
