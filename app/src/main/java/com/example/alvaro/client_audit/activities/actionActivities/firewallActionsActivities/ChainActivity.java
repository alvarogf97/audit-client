package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.controllers.adapters.FirewallChainAdapter;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.ChangeChainPolicyButtonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.CreateChainButtonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.DeleteChainButtonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.FlushChainButtonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.UpdateChainButtonClickListener;
import com.example.alvaro.client_audit.core.entities.Chain;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainActivity extends AsyncTaskActivity {

    private ListView chain_list_view;
    private SpinKitView loader;
    private Button update_button;
    private Button add_button;
    private EditText chain_name_edit;
    private FirewallChainAdapter adapter;
    private TextView reload_text_view;
    private ImageView reload_warning_image;

    private boolean is_getting_chains;
    private boolean is_flushing_chain;
    private boolean is_creating_chain;
    private boolean is_deleting_chain;
    private boolean is_changing_policy;
    private List<Chain> chain_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain);
        this.is_getting_chains = false;
        this.is_flushing_chain = false;
        this.is_creating_chain = false;
        this.is_deleting_chain = false;
        this.is_changing_policy = false;

        this.chain_list_view = (ListView) findViewById(R.id.chain_listView);
        this.chain_name_edit = (EditText) findViewById(R.id.chain_name_edit);
        this.add_button = (Button) findViewById(R.id.button_create_chain);
        this.update_button = (Button) findViewById(R.id.button_update_cahins);
        this.loader = (SpinKitView) findViewById(R.id.chain_loader);

        this.reload_text_view = (TextView) findViewById(R.id.reload_text_view);
        this.reload_warning_image = (ImageView) findViewById(R.id.reload_image_view);

        this.add_button.setOnClickListener(new CreateChainButtonClickListener(this));
        this.update_button.setOnClickListener(new UpdateChainButtonClickListener(this));

        this.adapter = new FirewallChainAdapter(this);
        this.chain_list_view.setAdapter(adapter);
        this.chain_list = new ArrayList<>();
        this.start_animation();
    }

    @Override
    public void start_animation() {
        this.get_chains();
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            if(this.is_getting_chains){
                this.is_getting_chains = false;
                if(response.getBoolean("status")){
                    this.chain_list = this.parse_chains(response.getJSONArray("data"));
                    this.adapter.addAll(this.chain_list);
                    this.finish_load();
                }
            }else if(this.is_changing_policy){
                this.is_changing_policy = false;
                this.finish_load();
                if(response.getBoolean("status")){
                    Toast toast = Toast.makeText(this, "policy changed correctly", Toast.LENGTH_SHORT);
                    toast.show();
                    this.show_reload();
                }else{
                    Toast toast = Toast.makeText(this, "Cannot change policy", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else if(this.is_deleting_chain){
                this.is_deleting_chain=false;
                this.finish_load();
                if(response.getBoolean("status")){
                    Toast toast = Toast.makeText(this, "Chain delete correctly", Toast.LENGTH_SHORT);
                    toast.show();
                    this.get_chains();
                }else{
                    Toast toast = Toast.makeText(this, "Cannot delete chain", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else if(this.is_creating_chain){
                this.is_creating_chain = false;
                this.finish_load();
                if(response.getBoolean("status")){
                    Toast toast = Toast.makeText(this, "Chain created correctly", Toast.LENGTH_SHORT);
                    toast.show();
                    this.get_chains();
                }else{
                    Toast toast = Toast.makeText(this, "Cannot create chain", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else if(this.is_flushing_chain){
                this.is_flushing_chain = false;
                this.finish_load();
                if(response.getBoolean("status")){
                    Toast toast = Toast.makeText(this, "chain flushed", Toast.LENGTH_SHORT);
                    toast.show();
                    this.get_chains();
                }else{
                    Toast toast = Toast.makeText(this, "Cannot flush this chain", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        } catch (JSONException e) {
            Log.e("chainActivity", Arrays.toString(e.getStackTrace()));
        }
    }

    private void show_reload(){
        this.loader.setVisibility(View.GONE);
        this.chain_list_view.setVisibility(View.GONE);
        this.chain_name_edit.setVisibility(View.GONE);
        this.update_button.setVisibility(View.GONE);
        this.add_button.setVisibility(View.GONE);
        this.reload_text_view.setVisibility(View.VISIBLE);
        this.reload_warning_image.setVisibility(View.VISIBLE);
    }

    private void load(){
        this.adapter.clear();
        this.loader.setIndeterminateDrawable(this.w);
        this.loader.setVisibility(View.VISIBLE);
        this.chain_list_view.setVisibility(View.GONE);
        this.chain_name_edit.setVisibility(View.GONE);
        this.update_button.setVisibility(View.GONE);
        this.add_button.setVisibility(View.GONE);
    }

    private void finish_load(){
        this.loader.setVisibility(View.GONE);
        this.chain_list_view.setVisibility(View.VISIBLE);
        this.chain_name_edit.setVisibility(View.VISIBLE);
        this.update_button.setVisibility(View.VISIBLE);
        this.add_button.setVisibility(View.VISIBLE);
    }

    public void get_chains(){
        this.load();
        FirewallAction action = FirewallActivity.getActionByName("view chains");
        JSONObject query = new JSONObject();
        try {
            this.is_getting_chains = true;
            query.put("command", action.getCommand());
            query.put("args", action.getArgs());
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("getting chains", Arrays.toString(e.getStackTrace()));
        }
    }

    public void flush_chain(Chain chain){
        this.load();
        FirewallAction action = FirewallActivity.getActionByName("flush chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            this.is_flushing_chain = true;
            query.put("command",action.getCommand());
            args.put("name",chain.getName());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("flush chain", Arrays.toString(e.getStackTrace()));
        }
    }

    public void create_chain(){
        String chain_name = this.chain_name_edit.getText().toString();
        if(!chain_name.equals("") && !chain_name.contains(" ")){
            this.load();
            FirewallAction action = FirewallActivity.getActionByName("add chain");
            JSONObject query = new JSONObject();
            JSONObject args = new JSONObject();
            try {
                this.is_creating_chain = true;
                query.put("command",action.getCommand());
                args.put("name",chain_name);
                query.put("args",args);
                Connection.get_connection().execute_command(query, this);
            } catch (JSONException e) {
                Log.e("create chain", Arrays.toString(e.getStackTrace()));
            }
        }else{
            Toast toast = Toast.makeText(this, "Chain name cannot be blank or contains whitespaces", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void delete_chain(Chain chain){
        this.load();
        FirewallAction action = FirewallActivity.getActionByName("remove chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            this.is_deleting_chain = true;
            query.put("command",action.getCommand());
            args.put("name",chain.getName());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("remove chain", Arrays.toString(e.getStackTrace()));
        }
    }

    public void change_policy(Chain chain){
        this.load();
        chain.changePolicy();
        FirewallAction action = FirewallActivity.getActionByName("policy chain");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            this.is_changing_policy = true;
            query.put("command",action.getCommand());
            args.put("name",chain.getName());
            args.put("policy",chain.getPolicy());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("change chain policy", Arrays.toString(e.getStackTrace()));
        }
    }

    private List<Chain> parse_chains(JSONArray chain_list){
        List<Chain> chains = new ArrayList<>();
        for(int i = 0; i<chain_list.length(); i++){
            try {
                JSONObject json_chain = chain_list.getJSONObject(i);
                Chain chain = new Chain(json_chain.getString("name"), json_chain.getString("policy"),
                        json_chain.getBoolean("is_removable"), json_chain.getBoolean("is_changeable"));
                chain.setChange_policy_listener(new ChangeChainPolicyButtonClickListener(this,chain));
                chain.setDelete_listener(new DeleteChainButtonClickListener(this,chain));
                chain.setFlush_listener(new FlushChainButtonClickListener(this,chain));
                chains.add(chain);
            } catch (JSONException e) {
                Log.e("parsing chains",Arrays.toString(e.getStackTrace()));
            }
        }
        return chains;
    }
}
