package com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;

public class ArgumentValueChangeListener implements TextWatcher {

    private UpnpActionActivity activity;

    public ArgumentValueChangeListener(UpnpActionActivity activity){
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
