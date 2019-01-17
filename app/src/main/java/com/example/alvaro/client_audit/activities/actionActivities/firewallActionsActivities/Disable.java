package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Disable extends AsyncTaskActivity {

    private SpinKitView loader;
    private Button disable_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable);
        this.loader = (SpinKitView) findViewById(R.id.disable_fw_anim_load);
        this.disable_button = (Button) findViewById(R.id.button_disable_firewall);
    }

    @Override
    public void start_animation() {
        this.disable_button.setEnabled(false);
        this.loader.setIndeterminateDrawable(this.w);
        this.loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            if(response.getBoolean("status")){
                this.disable_button.setEnabled(true);
                this.loader.setVisibility(View.GONE);
                Toast toast = Toast.makeText(this.getApplicationContext(), "Firewall successfully disabled", Toast.LENGTH_SHORT);
                toast.show();
                this.finish();
            }else{
                Toast toast = Toast.makeText(this.getApplicationContext(), "You do not have privileges", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (JSONException e) {
            Log.e("disable_fw", Arrays.toString(e.getStackTrace()));
        }
    }

    private void execute_query(){
        
    }
}
