package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public abstract class AsyncTaskActivity extends AppCompatActivity {

    protected ThreeBounce w;

    /*
        On create
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.w = new ThreeBounce();
    }

    /*
        start animation while asyncTask
     */
    public abstract void start_animation();

    /*
        called by asyncTask when it terminates
     */
    public abstract void stop_animation(Object... objects);

}
