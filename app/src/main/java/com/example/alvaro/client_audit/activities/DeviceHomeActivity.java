package com.example.alvaro.client_audit.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.listeners.DeleteDeviceButtonListener;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

public class DeviceHomeActivity extends AppCompatActivity {

    private FloatingActionButton delete_button;
    private TextView device_name;
    private TextView device_ip;
    private TextView device_port;
    private TextView device_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_home);

        this.delete_button = (FloatingActionButton) findViewById(R.id.delete_button);
        this.device_name = (TextView) findViewById(R.id.device_device_name);
        this.device_ip = (TextView) findViewById(R.id.device_device_ip);
        this.device_port = (TextView) findViewById(R.id.device_device_port);
        this.device_status = (TextView) findViewById(R.id.device_device_status);

        this.delete_button.setOnClickListener(new DeleteDeviceButtonListener(this));

        Device device = DeviceBook.get_instance().get_selected_device();
        Log.e("DeviceHome",device.toString());
        this.device_name.setText(device.get_name());
        this.device_ip.setText(device.get_ip());
        this.device_port.setText(String.valueOf(device.get_port()));

        if(device.get_status()){
            this.device_status.setText("Online");
        }else{
            this.device_status.setText("Offline");
        }

    }
}
