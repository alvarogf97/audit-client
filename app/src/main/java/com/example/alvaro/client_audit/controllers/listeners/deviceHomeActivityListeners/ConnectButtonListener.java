package com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.alvaro.client_audit.activities.InsideDeviceActivity;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;
import org.json.JSONObject;

import java.util.Arrays;

public class ConnectButtonListener implements View.OnClickListener {

    private Activity activity;

    public ConnectButtonListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Device device = DeviceBook.get_instance().get_selected_device();
        JSONObject response = Connection.get_connection().connect(device.get_ip(),device.get_port());
        if(response == null){
            this.makeToast();
        }else{
            Intent intent = new Intent(this.activity.getApplicationContext(), InsideDeviceActivity.class);
            try {
                intent.putExtra("cwd",response.getString("data"));
                this.activity.startActivity(intent);
            } catch (Exception e) {
                Log.e("ConnectButton", Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private void makeToast(){
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), "Could not connect to device", Toast.LENGTH_SHORT);
        toast.show();
    }

}

