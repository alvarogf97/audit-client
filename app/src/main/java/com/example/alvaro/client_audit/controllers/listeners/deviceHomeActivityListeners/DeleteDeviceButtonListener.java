package com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners;

import android.app.Activity;
import android.view.View;

import com.example.alvaro.client_audit.core.entities.DeviceBook;

public class DeleteDeviceButtonListener implements View.OnClickListener {

    private Activity activity;

    public DeleteDeviceButtonListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        DeviceBook.get_instance().delete_device(DeviceBook.get_instance().get_selected_device());
        this.activity.finish();
    }
}
