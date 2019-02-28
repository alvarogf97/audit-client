package com.example.alvaro.client_audit.activities.actionActivities.yaraActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.listeners.yaraScanActivityListeners.OnFilesScanClickListener;
import com.example.alvaro.client_audit.controllers.listeners.yaraScanActivityListeners.OnProcesScanClickListener;
import com.example.alvaro.client_audit.core.networks.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class YaraScanActivity extends AsyncTaskActivity {

    private CardView process_scanner;
    private CardView files_scanner;
    private boolean is_scan_active;
    private boolean in_process = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yara_scan);
        this.process_scanner = (CardView) findViewById(R.id.card_process_scan);
        this.files_scanner = (CardView) findViewById(R.id.card_file_scan);
        this.process_scanner.setOnClickListener(new OnProcesScanClickListener(this));
        this.files_scanner.setOnClickListener(new OnFilesScanClickListener(this));
        this.start_animation();
    }

    @Override
    public void start_animation() {
        this.process_scanner.setVisibility(View.GONE);
        this.files_scanner.setVisibility(View.GONE);
        this.execute_query();
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        this.in_process = false;
        try {
            if(response.getBoolean("status")){
                Intent intent = new Intent(this, ScanResultsActivity.class);
                intent.putExtra("is_scan_active", this.is_scan_active);
                intent.putExtra("scan_type", 2);
                this.startActivity(intent);
            }else{
                this.process_scanner.setVisibility(View.VISIBLE);
                this.files_scanner.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            Log.e("yara_check_scan", Arrays.toString(e.getStackTrace()));
        }
    }

    public void start_process_scanner(){
        Intent intent = new Intent(this, ScanResultsActivity.class);
        intent.putExtra("is_scan_active", this.is_scan_active);
        intent.putExtra("scan_type", 2);
        this.startActivity(intent);
    }

    public void start_file_search(){
        Intent intent = new Intent(this, FileSearchActivity.class);
        this.startActivity(intent);
    }

    public void execute_query(){
        JSONObject query = new JSONObject();
        try {
            in_process = true;
            query.put("command","yarascan is active");
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("yara_check_scan", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!in_process) {
            super.onBackPressed();
        }
    }
}
