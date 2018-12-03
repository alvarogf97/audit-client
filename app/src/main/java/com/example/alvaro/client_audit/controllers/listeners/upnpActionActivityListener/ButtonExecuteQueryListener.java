package com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener;

import android.util.Log;
import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;

import org.json.JSONException;

import java.util.Arrays;

public class ButtonExecuteQueryListener implements View.OnClickListener {

    private UpnpActionActivity activity;

    public ButtonExecuteQueryListener(UpnpActionActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        try {
            this.activity.execute_query();
        } catch (JSONException e) {
            Log.e("execute action", Arrays.toString(e.getStackTrace()));
        }
    }
}
