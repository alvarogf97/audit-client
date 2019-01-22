package com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.alvaro.client_audit.activities.DeviceHomeActivity;
import com.example.alvaro.client_audit.activities.InsideDeviceActivity;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ConnectButtonListener implements View.OnClickListener {

    private DeviceHomeActivity activity;

    public ConnectButtonListener(DeviceHomeActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Device device = DeviceBook.get_instance().get_selected_device();
        JSONObject response = Connection.get_connection().connect(device.get_ip(),device.get_port());
        if(response == null){
            this.makeToast();
        }else{

            JSONObject query = new JSONObject();
            try {
                query.put("name",this.activity.getName());
                query.put("password",this.activity.getPassword());
            } catch (JSONException e) {
                Log.e("hardware", Arrays.toString(e.getStackTrace()));
            }

            if(Connection.get_connection().login(query)){
                Intent intent = new Intent(this.activity.getApplicationContext(), InsideDeviceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.activity.hide_dialog();
                try {
                    intent.putExtra("cwd",response.getString("data"));
                    this.activity.startActivity(intent);
                } catch (Exception e) {
                    Log.e("ConnectButton", Arrays.toString(e.getStackTrace()));
                }
            }else {
                Connection.get_connection().close();
                this.activity.setError("User / password incorrect!");
            }
        }
    }

    private void makeToast(){
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), "Could not connect to device", Toast.LENGTH_SHORT);
        toast.show();
    }

}

