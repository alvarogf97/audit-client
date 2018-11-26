package com.example.alvaro.client_audit.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.listeners.DeleteDeviceButtonListener;

public class DeviceHomeActivity extends AppCompatActivity {

    private FloatingActionButton delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_home);

        this.delete_button = (FloatingActionButton) findViewById(R.id.delete_button);
        this.delete_button.setOnClickListener(new DeleteDeviceButtonListener(this));
    }
}
