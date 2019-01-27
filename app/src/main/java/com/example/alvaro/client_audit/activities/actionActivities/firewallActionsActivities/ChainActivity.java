package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.core.entities.Chain;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.networks.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainActivity extends AsyncTaskActivity {

    private boolean is_getting_chains;
    private boolean is_flushing_chain;
    private List<Chain> chain_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        this.is_getting_chains = false;
        this.is_flushing_chain = false;
        this.chain_list = new ArrayList<>();
    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {

    }

    private void get_chains(){
        FirewallAction action = FirewallActivity.getActionByName("view chains");
        JSONObject query = new JSONObject();
        try {
            query.put("command", action.getCommand());
            query.put("args", action.getArgs());
            Connection.get_connection().execute_command(query, this);
            this.is_getting_chains = true;
        } catch (JSONException e) {
            Log.e("getting chains", Arrays.toString(e.getStackTrace()));
        }
    }

    public void flush_chain(Chain chain){
        FirewallAction action = FirewallActivity.getActionByName("flush chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            query.put("command",action.getCommand());
            args.put("name",chain.getName());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
            this.is_flushing_chain = true;
        } catch (JSONException e) {
            Log.e("flush chain", Arrays.toString(e.getStackTrace()));
        }
    }

    public void create_chain(){
        FirewallAction action = FirewallActivity.getActionByName("add chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            query.put("command",action.getCommand());
            args.put("name",""); //TODO
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
            this.is_flushing_chain = true;
        } catch (JSONException e) {
            Log.e("create chain", Arrays.toString(e.getStackTrace()));
        }
    }

    public void delete_chain(Chain chain){
        FirewallAction action = FirewallActivity.getActionByName("remove chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            query.put("command",action.getCommand());
            args.put("name",chain.getName());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
            this.is_flushing_chain = true;
        } catch (JSONException e) {
            Log.e("remove chain", Arrays.toString(e.getStackTrace()));
        }
    }

    public void change_policy(Chain chain){
        chain.changePolicy();
        FirewallAction action = FirewallActivity.getActionByName("policy chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            query.put("command",action.getCommand());
            args.put("name",chain.getName());
            args.put("policy",chain.getPolicy());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
            this.is_flushing_chain = true;
        } catch (JSONException e) {
            Log.e("change chain policy", Arrays.toString(e.getStackTrace()));
        }
    }

    private List<Chain> parse_chains(JSONArray chain_list){
        List<Chain> chains = new ArrayList<>();
        for(int i = 0; i<chain_list.length(); i++){
            try {
                JSONObject json_chain = chain_list.getJSONObject(i);
                Chain chain = new Chain(json_chain.getString("name"), json_chain.getString("policy"), json_chain.getBoolean("is_removable"));
                chains.add(chain);
            } catch (JSONException e) {
                Log.e("parsing chains",Arrays.toString(e.getStackTrace()));
            }
        }
        return chains;
    }
}
