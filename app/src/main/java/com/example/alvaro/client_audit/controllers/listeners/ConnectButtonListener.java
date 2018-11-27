package com.example.alvaro.client_audit.controllers.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.alvaro.client_audit.activities.InsideDeviceActivity;
import com.example.alvaro.client_audit.core.Connection;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

public class ConnectButtonListener implements View.OnClickListener {

    private Activity activity;

    public ConnectButtonListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Device device = DeviceBook.get_instance().get_selected_device();
        String cwd = Connection.get_connection().connect(device.get_ip(),device.get_port());
        if(cwd == null){
            this.makeToast();
        }else{
            Intent intent = new Intent(this.activity.getApplicationContext(), InsideDeviceActivity.class);
            intent.putExtra("cwd",cwd);
            this.activity.startActivity(intent);
        }
    }

    public void makeToast(){
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), "Could not connect to device", Toast.LENGTH_SHORT);
        toast.show();
    }

}

