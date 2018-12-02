package com.example.alvaro.client_audit.controllers.listeners.VulnersActivityListener;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.VulnersActivity;

public class ReScanButtonClickListener implements View.OnClickListener {

    private VulnersActivity activity;

    public ReScanButtonClickListener(VulnersActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        activity.re_scan();
    }

}
