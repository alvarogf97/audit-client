package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.controllers.adapters.FileAdapter;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.FileRestoreItemClickListener;
import com.example.alvaro.client_audit.core.entities.File;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Import extends AsyncTaskActivity {

    private boolean is_getting_files;
    private boolean is_restoring;
    private ListView file_list_view;
    private SpinKitView loader;
    private FileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        this.is_restoring = false;
        this.is_getting_files = false;
        this.file_list_view = (ListView) findViewById(R.id.import_file_list);
        this.loader = (SpinKitView) findViewById(R.id.import_fw_anim);
        this.adapter = new FileAdapter(Import.this);
        this.file_list_view.setAdapter(adapter);
        this.file_list_view.setOnItemClickListener(new FileRestoreItemClickListener(this));
        this.start_animation();
    }

    @Override
    public void start_animation() {
        file_list_view.setVisibility(View.GONE);
        loader.setIndeterminateDrawable(this.w);
        loader.setVisibility(View.VISIBLE);
        this.execute_query();
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            if(response.getBoolean("status") && this.is_getting_files){
                adapter.addAll(this.parse_response(response.getJSONArray("data")));
                this.is_getting_files = false;
                this.file_list_view.setVisibility(View.VISIBLE);
                this.loader.setVisibility(View.GONE);
            }else if(this.is_restoring){
                Toast toast;
                if(response.getBoolean("status")){
                    toast = Toast.makeText(this.getApplicationContext(), "Firewall restored successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    this.finish();
                }else{
                    toast = Toast.makeText(this.getApplicationContext(), "You do not have privileges", Toast.LENGTH_SHORT);
                    toast.show();
                }
                this.is_restoring = false;
            }else{
                Log.e("Unexpected","error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<File> parse_response(JSONArray json_files) throws JSONException {
        List<File> file_list = new ArrayList<>();
        for(int i = 0; i<json_files.length(); i++){
            file_list.add(new File(json_files.getString(i)));
        }
        return file_list;
    }

    public void execute_query(){
        this.is_getting_files = true;
        FirewallAction get_files_action = FirewallActivity.getActionByName("files");
        JSONObject query = new JSONObject();
        try {
            query.put("command",get_files_action.getCommand());
            query.put("args",get_files_action.getArgs());
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("files_firewall", Arrays.toString(e.getStackTrace()));
        }
    }

    public void restore_firewall(File selected_file){
        this.is_restoring = true;
        FirewallAction import_settings_action = FirewallActivity.getActionByName("import settings");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            query.put("command",import_settings_action.getCommand());
            args.put("filename",selected_file.getName());
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("restore_firewall", Arrays.toString(e.getStackTrace()));
        }
    }
}
