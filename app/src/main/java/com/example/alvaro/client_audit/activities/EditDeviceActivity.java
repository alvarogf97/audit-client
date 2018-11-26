package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.listeners.SaveButtonListener;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

public class EditDeviceActivity extends AppCompatActivity {

    TextView device_name;
    TextView device_ip;
    TextView device_port;
    Button b_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);

        b_save = (Button) findViewById(R.id.button_save);
        device_name = (TextView) findViewById(R.id.device_edit_name);
        device_ip = (TextView) findViewById(R.id.device_edit_ip);
        device_port = (TextView) findViewById(R.id.device_edit_port);

        Device device = DeviceBook.get_instance().get_selected_device();

        this.device_name.setText(device.get_name());
        this.device_ip.setText(device.get_ip());
        this.device_port.setText(String.valueOf(device.get_port()));

        this.b_save.setOnClickListener(new SaveButtonListener(this));

    }
}
