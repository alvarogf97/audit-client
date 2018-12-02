package com.example.alvaro.client_audit.controllers.listeners.upnpActivityListeners;

import android.view.View;
import com.example.alvaro.client_audit.activities.actionActivities.UpnpActivity;
import com.example.alvaro.client_audit.activities.actionActivities.VulnersActivity;

public class ReScanButtonClickListener implements View.OnClickListener {

    private UpnpActivity activity;

    public ReScanButtonClickListener(UpnpActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        activity.start_animation();
    }

}
