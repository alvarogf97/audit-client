package com.example.alvaro.client_audit.activities.actionActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.FirewallActionAdapter;
import com.example.alvaro.client_audit.controllers.adapters.StatusFirewallItemAdapter;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.ActionListClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.ButtonUpdateStatusListener;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.entities.StatusFirewall;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FirewallActivity extends AsyncTaskActivity {

    private ThreeBounce w_w;
    private SpinKitView loader;
    private SpinKitView loader_status;
    private Button button_update_Status;
    private ListView action_list;
    private FirewallActionAdapter adapter;
    private StatusFirewallItemAdapter adapter_status;
    private ListView status_list;
    private TextView action_text;
    private TextView status_text;
    private TextView error_text;
    private ImageView error_image;
    public static List<FirewallAction> actions;
    public static boolean is_admin;

    public boolean is_execute_status;
    public boolean is_execute_enable;
    public boolean is_execute_descriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firewall);
        this.is_execute_status = false;
        this.w_w = new ThreeBounce();
        this.is_execute_enable = false;
        this.action_text = (TextView) findViewById(R.id.firewall_actions_text);
        this.status_text = (TextView) findViewById(R.id.firewall_status_text);
        this.error_text = (TextView) findViewById(R.id.firewall_error_text);
        this.error_image = (ImageView) findViewById(R.id.firewall_error_image);
        this.loader = (SpinKitView) findViewById(R.id.firewall_anim_load);
        this.loader_status = (SpinKitView) findViewById(R.id.firewall_anim_status_load);
        this.button_update_Status = (Button) findViewById(R.id.button_update_status);
        this.button_update_Status.setOnClickListener(new ButtonUpdateStatusListener(this));
        this.action_list = (ListView) findViewById(R.id.firewall_action_list);
        this.status_list = (ListView) findViewById(R.id.firewall_status_list);
        adapter = new FirewallActionAdapter(this);
        adapter_status = new StatusFirewallItemAdapter(this);
        this.action_list.setAdapter(adapter);
        this.status_list.setAdapter(adapter_status);
        this.action_list.setOnItemClickListener(new ActionListClickListener(this));
        this.start_animation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.update_status();
    }

    @Override
    public void start_animation() {
        this.is_execute_descriptor = true;
        this.loader.setVisibility(View.VISIBLE);
        this.action_list.setVisibility(View.GONE);
        this.button_update_Status.setVisibility(View.GONE);
        loader.setIndeterminateDrawable(this.w_w);
        loader.setIndeterminateDrawable(this.w);
        execute_query();
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            if (response.getBoolean("status") && this.is_execute_descriptor) {
                actions = this.getActions(response);
                adapter.addAll(this.filterActions(actions));
                loader.setVisibility(View.GONE);
                this.action_list.setVisibility(View.VISIBLE);
                this.button_update_Status.setVisibility(View.VISIBLE);
                this.set_status(response.getJSONObject("fw_status"));
                this.is_execute_descriptor = false;
            } else if(response.getBoolean("status") && this.is_execute_status && is_admin){
                Log.e("execute_status:","true");
                this.status_list.setVisibility(View.VISIBLE);
                this.set_status(response);
                this.button_update_Status.setEnabled(true);
                this.loader_status.setVisibility(View.GONE);
                this.is_execute_status = false;
            }else if(this.is_execute_enable){
                Toast toast;
                if(response.getBoolean("status")){
                    toast = Toast.makeText(this.getApplicationContext(), "Firewall enabled", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    toast = Toast.makeText(this.getApplicationContext(), "Whoops, some errors found :(", Toast.LENGTH_SHORT);
                    toast.show();
                }
                this.action_list.setEnabled(true);
                this.is_execute_enable = false;
                this.update_status();
            }else if(response.getBoolean("status") && !is_admin){
                this.show_error(R.drawable.ic_lock, getResources().getString(R.string.no_admin));
            }else{
                this.show_error(R.drawable.ic_warning, getResources().getString(R.string.firewall_incompatible));
            }
        } catch (JSONException e) {
            Log.e("firewall_response",Arrays.toString(e.getStackTrace()));
        }
    }

    private void set_status(JSONObject response) throws JSONException {
        JSONObject status_data = response.getJSONObject("data");
        is_admin = response.getBoolean("administrator");
        if(!is_admin){

        }
        List<StatusFirewall> status_items = new ArrayList<>();
        Iterator<String> data_iter = status_data.keys();
        while (data_iter.hasNext()){
            String key = data_iter.next();
            boolean is_active = status_data.getBoolean(key);
            status_items.add(new StatusFirewall(key, is_active));
        }
        adapter_status.clear();
        adapter_status.addAll(status_items);
    }

    public void update_status(){
        JSONObject query = new JSONObject();
        try {
            this.is_execute_status = true;
            query.put("command","firewall status");
            query.put("args","");
            Connection.get_connection().execute_command(query, this);
            this.status_list.setVisibility(View.GONE);
            this.button_update_Status.setEnabled(false);
            this.loader_status.setIndeterminateDrawable(this.w);
            this.loader_status.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            Log.e("status", Arrays.toString(e.getStackTrace()));
        }
    }

    public void execute_firewall_action(FirewallAction action){
        JSONObject query = new JSONObject();
        try {
            this.action_list.setEnabled(false);
            query.put("command",action.getCommand());
            query.put("args",action.getArgs());
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("status", Arrays.toString(e.getStackTrace()));
        }
    }

    private void show_error(int image, String text){
        this.action_text.setVisibility(View.GONE);
        this.status_text.setVisibility(View.GONE);
        this.action_list.setVisibility(View.GONE);
        this.button_update_Status.setVisibility(View.GONE);
        this.status_list.setVisibility(View.GONE);

        this.error_image.setImageResource(image);
        this.error_text.setText(text);
        this.error_image.setVisibility(View.VISIBLE);
        this.error_text.setVisibility(View.VISIBLE);

        this.loader.setVisibility(View.GONE);
        this.loader_status.setVisibility(View.GONE);
    }

    private List<FirewallAction> getActions(JSONObject response){
        List<FirewallAction> actions = new ArrayList<>();
        try {
            JSONArray json_actions = response.getJSONArray("data");
            for(int i = 0; i<json_actions.length(); i++){
                JSONObject json_action = json_actions.getJSONObject(i);
                FirewallAction action = new FirewallAction(json_action.getString("name"),
                        json_action.getJSONObject("args"), json_action.getBoolean("show"), json_action.getString("command"));
                actions.add(action);
            }
        } catch (JSONException e) {
            Log.e("firewall actions",Arrays.toString(e.getStackTrace()));
        }
        return actions;
    }

    private List<FirewallAction> filterActions(List<FirewallAction> actions){
        List<FirewallAction> filter_actions = new ArrayList<>();
        for(FirewallAction action : actions){
            Log.e("visible",String.valueOf(action.is_visible()));
            if(action.is_visible()) {
                filter_actions.add(action);
            }
        }
        return filter_actions;
    }

    private void execute_query(){
        JSONObject query = new JSONObject();
        try {
            query.put("command","firewall descriptor");
            query.put("args","");
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("descriptor", Arrays.toString(e.getStackTrace()));
        }
    }

    public static FirewallAction getActionByName(String name){
        boolean found = false;
        FirewallAction action = null;
        int i = 0;
        while(i<actions.size() && !found){
            FirewallAction cursor_action = actions.get(i);
            if(cursor_action.getName().equals(name)){
                action = cursor_action;
                found = true;
            }
            i++;
        }
        return action;
    }
}
