package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.networks.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class HardwareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);

        JSONObject query = new JSONObject();
        try {
            query.put("command","HWinfo");
            JSONObject response = Connection.get_connection().execute_command(query);
        } catch (JSONException e) {
            Log.e("hardware", Arrays.toString(e.getStackTrace()));
        }

    }
}
