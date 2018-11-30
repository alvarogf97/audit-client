package com.example.alvaro.client_audit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.networks.Connection;

public class InsideDeviceActivity extends AppCompatActivity {

    private TextView cwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_device);

        this.cwd = (TextView) findViewById(R.id.cwd);

        Intent intent = this.getIntent();
        String cwd = intent.getExtras().getString("cwd");
        this.cwd.setText(cwd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy connection","connection close");
        Connection.get_connection().close();
    }
}
