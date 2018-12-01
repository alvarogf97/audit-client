package com.example.alvaro.client_audit.activities.actionActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsynkTaskActivity;

public class VulnersActivity extends AsynkTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulners);
    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {

    }
}
