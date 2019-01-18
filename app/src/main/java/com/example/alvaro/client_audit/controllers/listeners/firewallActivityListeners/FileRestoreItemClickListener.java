package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.Import;
import com.example.alvaro.client_audit.core.entities.File;

public class FileRestoreItemClickListener implements AdapterView.OnItemClickListener {

    private Import activity;

    public FileRestoreItemClickListener(Import activity){
        this.activity = activity;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File selected_file = (File) parent.getItemAtPosition(position);
        this.activity.restore_firewall(selected_file);
    }
}
