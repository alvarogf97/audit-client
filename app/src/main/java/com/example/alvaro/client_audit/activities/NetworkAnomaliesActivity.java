package com.example.alvaro.client_audit.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.actionActivities.NetworkActivity;
import com.example.alvaro.client_audit.controllers.adapters.AnomalyAdapter;
import com.example.alvaro.client_audit.core.entities.NetworkMeasure;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class NetworkAnomaliesActivity extends AsyncTaskActivity{

    private ListView anomalies_list_view;
    private ImageView success_image;
    private TextView success_text;
    private SpinKitView loader;
    private AnomalyAdapter adapter;
    private NetworkMeasure current_measure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_anomalies);
        this.anomalies_list_view = (ListView) findViewById(R.id.abnormal_list);
        this.success_image = (ImageView) findViewById(R.id.success_image);
        this.success_text = (TextView) findViewById(R.id.n_a_f);
        this.loader = (SpinKitView) findViewById(R.id.abnormal_anim_load);
        this.adapter = new AnomalyAdapter(this.getApplicationContext(), this);
        adapter.addAll(NetworkActivity.abnormal_measures);
        this.anomalies_list_view.setAdapter(adapter);
        if(NetworkActivity.abnormal_measures.size() == 0){
            this.show_no();
        }
    }

    public void show_no(){
        loader.setVisibility(View.GONE);
        anomalies_list_view.setVisibility(View.GONE);
        success_image.setVisibility(View.VISIBLE);
        success_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void start_animation() {
        loader.setIndeterminateDrawable(this.w);
        loader.setVisibility(View.VISIBLE);
        anomalies_list_view.setVisibility(View.GONE);
    }

    public void setCurrentMeasure(NetworkMeasure measure){
        this.current_measure = measure;
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            loader.setVisibility(View.GONE);
            anomalies_list_view.setVisibility(View.VISIBLE);
            if(response.getBoolean("status")){
                Toast toast = Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT);
                NetworkActivity.abnormal_measures.remove(current_measure);
                this.adapter.clear();
                this.adapter.addAll(NetworkActivity.abnormal_measures);
                if(NetworkActivity.abnormal_measures.size() == 0){
                    this.show_no();
                }
                toast.show();
            }else{
                Toast toast = Toast.makeText(this, "Cannot add as exception", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (JSONException e) {
            Log.e("NAAST", Arrays.toString(e.getStackTrace()));
        }
    }

    public void add_measure_exception(NetworkMeasure measure){
        this.start_animation();
        JSONObject query = new JSONObject();
        this.current_measure = measure;
        try {
            String command = "add network measure exception";
            query.put("command",command);
            query.put("args", measure.to_json());
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("adding m exception", Arrays.toString(e.getStackTrace()));
        }
    }
}
