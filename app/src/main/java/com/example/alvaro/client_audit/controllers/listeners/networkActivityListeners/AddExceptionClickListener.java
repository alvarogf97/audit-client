package com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners;

import android.app.Activity;
import android.view.View;

import com.example.alvaro.client_audit.activities.NetworkAnomaliesActivity;
import com.example.alvaro.client_audit.core.entities.NetworkMeasure;

public class AddExceptionClickListener implements View.OnClickListener {

    private NetworkAnomaliesActivity activity;
    private NetworkMeasure measure;

    public AddExceptionClickListener(NetworkAnomaliesActivity activity, NetworkMeasure measure){
        this.activity = activity;
        this.measure = measure;
    }

    @Override
    public void onClick(View v) {
        this.activity.add_measure_exception(measure);
    }
}
