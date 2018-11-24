package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.bd.BD;
import com.example.alvaro.client_audit.core.Connection;
import com.example.alvaro.client_audit.entities.DeviceBook;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Connection.set_resources_context(this.getResources());
        BD.setInstanceContext(this.getApplicationContext());
        DeviceBook.set_context(this.getApplicationContext());
        String cwd = Connection.get_connection().connect("192.168.1.112",5000);

    }
}
