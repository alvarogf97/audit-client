package com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener;

import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;
import com.example.alvaro.client_audit.core.entities.Argument;

public class onArgumentClickListener implements AdapterView.OnItemClickListener {

    private UpnpActionActivity activity;

    public onArgumentClickListener(UpnpActionActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Argument selected_argument = (Argument) parent.getItemAtPosition(position);
        this.activity.setSelected_Argument(selected_argument);
        this.activity.showDialog();
    }

}
