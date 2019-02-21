package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.DisableFirewallButtonListener;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Disable extends AsyncTaskActivity {

    private SpinKitView loader;
    private Button disable_button;
    private boolean onExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable);
        this.onExecute = false;
        this.loader = (SpinKitView) findViewById(R.id.disable_fw_anim_load);
        this.disable_button = (Button) findViewById(R.id.button_disable_firewall);
        this.disable_button.setOnClickListener(new DisableFirewallButtonListener(this));
    }

    @Override
    public void start_animation() {
        this.onExecute = true;
        this.disable_button.setEnabled(false);
        this.loader.setIndeterminateDrawable(this.w);
        this.loader.setVisibility(View.VISIBLE);
        this.execute_query();
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
                this.disable_button.setEnabled(true);
                Toast toast = Toast.makeText(this.getApplicationContext(), "Whoops, some errors found :(", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (JSONException e) {
            Log.e("disable_fw", Arrays.toString(e.getStackTrace()));
        } finally {
            this.onExecute = false;
        }
    }

    private void execute_query(){
        JSONObject query = new JSONObject();
        try {
            FirewallAction action = FirewallActivity.getActionByName("disable");
            query.put("command",action.getCommand());
            query.put("args","");
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("descriptor", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!onExecute) {
            super.onBackPressed();
        }
    }
}
