package com.example.alvaro.client_audit.activities.actionActivities.yaraActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ScanResultsActivity extends AsyncTaskActivity {

    private SpinKitView loader;
    private ListView result_list;
    private TextView result_text;

    private int scan_type;
    private boolean is_scan_active;
    private boolean onExecute;
    private String directory;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_results);
        this.onExecute = false;
        Intent intent = this.getIntent();
        this.scan_type = intent.getExtras().getInt("scan_type");
        this.is_scan_active = intent.getExtras().getBoolean("is_scan_active");
        if (this.scan_type == 1){
            this.directory = intent.getExtras().getString("directory");
        }else if(this.scan_type == 0){
            this.filename = intent.getExtras().getString("filename");
        }
        this.start_animation();
    }

    @Override
    public void start_animation() {
        loader.setIndeterminateDrawable(this.w);
        result_list.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        this.scan();
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        this.onExecute = false;
        try {
            if(response.getBoolean("status")){

            }else{
                this.result_text.setText(response.getString("data"));
                this.scan();
            }
        } catch (JSONException e) {
            Log.e("yara_scan_finish", Arrays.toString(e.getStackTrace()));
        }
    }

    public void scan(){
        if(this.is_scan_active){
            this.get_scan_info();
        }else if(this.scan_type == 2){
            this.scan_process();
        }else if(this.scan_type == 1){
            this.scan_folder();
        }else{
            this.scan_file();
        }
    }

    public void scan_process(){
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            onExecute = true;
            query.put("command","yarascan scan");
            args.put("scan_type", this.scan_type);
            query.put("args", args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("yara_scan_process", Arrays.toString(e.getStackTrace()));
        }
    }

    public void scan_folder(){
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            onExecute = true;
            query.put("command","yarascan scan");
            args.put("scan_type", this.scan_type);
            args.put("directory", this.directory);
            query.put("args", args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("yara_scan_folder", Arrays.toString(e.getStackTrace()));
        }
    }

    public void scan_file(){
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            onExecute = true;
            query.put("command","yarascan scan");
            args.put("scan_type", this.scan_type);
            args.put("filename", this.filename);
            query.put("args", args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("yara_scan_file", Arrays.toString(e.getStackTrace()));
        }
    }

    public void get_scan_info(){
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            onExecute = true;
            query.put("command","yarascan scan");
            args.put("scan_type", this.scan_type);
            query.put("args", args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("yara_scan", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!onExecute) {
            super.onBackPressed();
        }
    }
}
