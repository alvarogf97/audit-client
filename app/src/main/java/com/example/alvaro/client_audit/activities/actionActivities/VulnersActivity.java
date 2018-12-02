package com.example.alvaro.client_audit.activities.actionActivities;

import android.os.Bundle;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;

public class VulnersActivity extends AsyncTaskActivity {

    /*
        On create
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulners);
    }

    /*
        start animation while asyncTask
     */

    @Override
    public void start_animation() {

    }

    /*
        called by asyncTask when it terminates
     */

    @Override
    public void stop_animation(Object... objects) {

    }
}
