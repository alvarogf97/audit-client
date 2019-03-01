package com.example.alvaro.client_audit.activities.actionActivities.yaraActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.InsideDeviceActivity;
import com.example.alvaro.client_audit.controllers.adapters.DocumentAdapter;
import com.example.alvaro.client_audit.controllers.listeners.FileSearchActivityListeners.OnDocumentClickListener;
import com.example.alvaro.client_audit.core.entities.Document;
import com.example.alvaro.client_audit.core.networks.Connection;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class FileSearchActivity extends AsyncTaskActivity {

    private String cwd;
    private ListView file_system;
    private boolean onExecute;
    private DocumentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_search);
        this.file_system = (ListView) findViewById(R.id.file_list);
        this.file_system.setOnItemClickListener(new OnDocumentClickListener(this));
        this.cwd = InsideDeviceActivity.cwd;
        this.onExecute = false;
        this.adapter = new DocumentAdapter(this);
        this.file_system.setAdapter(adapter);
        this.get_folder_content(cwd);
    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {
        onExecute = false;
        JSONObject response = (JSONObject) objects[0];
        try {
            if(response.getBoolean("status")){
              adapter.clear();
              adapter.addAll(Document.from_JSON_array(response.getJSONArray("data")));
            }
            this.file_system.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            Log.e("getFolderContentSto", Arrays.toString(e.getStackTrace()));
        }
    }

    public void get_folder_content(String path){
        JSONObject query = new JSONObject();
        try {
            this.file_system.setVisibility(View.GONE);
            onExecute = true;
            query.put("command","get folder content");
            query.put("args", path);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("get_folder_content", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!onExecute) {
            super.onBackPressed();
        }
    }
}
