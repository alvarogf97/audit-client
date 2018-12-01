package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public abstract class AsynkTaskActivity extends AppCompatActivity {

    protected ThreeBounce w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.w = new ThreeBounce();
    }

    public abstract void start_animation();

    public abstract void stop_animation(Object... objects);

}
