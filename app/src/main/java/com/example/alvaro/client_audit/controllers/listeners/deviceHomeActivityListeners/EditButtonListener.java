package com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.alvaro.client_audit.activities.EditDeviceActivity;

public class EditButtonListener implements View.OnClickListener {

    private Context context;

    public EditButtonListener(Context context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.context,EditDeviceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
    }

}
