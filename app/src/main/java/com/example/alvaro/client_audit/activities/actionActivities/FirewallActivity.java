package com.example.alvaro.client_audit.activities.actionActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.FirewallActionAdapter;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirewallActivity extends AsyncTaskActivity {

    private SpinKitView loader;
    private ListView action_list;
    private FirewallActionAdapter adapter;
    private TextView test;
    private TextView action_text;
    private TextView status_text;
    private TextView error_text;
    private ImageView error_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firewall);
        this.action_text = (TextView) findViewById(R.id.firewall_actions_text);
        this.status_text = (TextView) findViewById(R.id.firewall_status_text);
        this.error_text = (TextView) findViewById(R.id.firewall_error_text);
        this.error_image = (ImageView) findViewById(R.id.firewall_error_image);
        this.loader = (SpinKitView) findViewById(R.id.firewall_anim_load);
        this.action_list = (ListView) findViewById(R.id.firewall_action_list);
        this.test = (TextView) findViewById(R.id.ff_test);
        adapter = new FirewallActionAdapter(this);
        this.action_list.setAdapter(adapter);
        this.start_animation();
    }

    @Override
    public void start_animation() {
        this.loader.setVisibility(View.VISIBLE);
        this.action_list.setVisibility(View.GONE);
        loader.setIndeterminateDrawable(this.w);
        execute_query();
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            if(response.getBoolean("status")){
                adapter.addAll(this.getActions(response));
                loader.setVisibility(View.GONE);
                this.action_list.setVisibility(View.VISIBLE);
                this.test.setText(response.getJSONObject("fw_status").toString());
            }else{
                this.show_error();
            }
        } catch (JSONException e) {
            Log.e("firewall_response",Arrays.toString(e.getStackTrace()));
        }
    }

    private void show_error(){
        this.action_text.setVisibility(View.GONE);
        this.status_text.setVisibility(View.GONE);
        this.action_list.setVisibility(View.GONE);
        this.test.setVisibility(View.GONE);
        this.error_image.setVisibility(View.VISIBLE);
        this.error_text.setVisibility(View.VISIBLE);
    }

    private List<FirewallAction> getActions(JSONObject response){
        List<FirewallAction> actions = new ArrayList<>();
        try {
            JSONArray json_actions = response.getJSONArray("data");
            for(int i = 0; i<json_actions.length(); i++){
                JSONObject json_action = json_actions.getJSONObject(i);
                FirewallAction action = new FirewallAction(json_action.getString("name"),
                        json_action.getJSONObject("args"));
                actions.add(action);
            }
        } catch (JSONException e) {
            Log.e("firewall actions",Arrays.toString(e.getStackTrace()));
        }
        return actions;
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
}
