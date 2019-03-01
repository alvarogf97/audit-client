package com.example.alvaro.client_audit.activities.actionActivities.yaraActivities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.InsideDeviceActivity;
import com.example.alvaro.client_audit.core.networks.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FileSearchActivity extends AsyncTaskActivity {

    private String cwd;
    private ListView file_system;
    private boolean onExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_search);
        this.cwd = InsideDeviceActivity.cwd;
        this.onExecute = false;
    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {
        onExecute = true;
        JSONObject response = (JSONObject) objects[0];
        try {
            if(response.getBoolean("status")){
              //TODO adapter
            }
        } catch (JSONException e) {
            Log.e("getFolderContentSto", Arrays.toString(e.getStackTrace()));
        }
    }

    public void get_folder_content(String path){
        JSONObject query = new JSONObject();
        try {
            onExecute = true;
            query.put("command","get folder content");
            query.put("args", path);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("get_folder_content", Arrays.toString(e.getStackTrace()));
        }
    }
}
