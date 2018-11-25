package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.listeners.CreateDeviceButton;

public class AddDeviceActivity extends AppCompatActivity {

    Button b_create;
    private TextView device_name;
    private TextView device_ip;
    private TextView device_port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device_activity);

        b_create = (Button) findViewById(R.id.button_create);
        device_name = (TextView) findViewById(R.id.device_name);
        device_ip = (TextView) findViewById(R.id.device_ip);
        device_port = (TextView) findViewById(R.id.device_port);

        b_create.setOnClickListener(new CreateDeviceButton(this,device_name,device_ip,device_port));
    }

}
