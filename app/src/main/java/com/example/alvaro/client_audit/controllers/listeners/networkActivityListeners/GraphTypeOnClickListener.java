package com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.actionActivities.NetworkActivity;

public class GraphTypeOnClickListener implements AdapterView.OnItemClickListener {

    private NetworkActivity activity;

    public GraphTypeOnClickListener(NetworkActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getItemAtPosition(position);
        int choose;
        if(selected.equalsIgnoreCase(this.activity.getString(R.string.graph_input_size_time))){
            choose = 0;
        }else if(selected.equalsIgnoreCase(this.activity.getString(R.string.graph_output_size_time))){
            choose = 1;
        }else if(selected.equalsIgnoreCase(this.activity.getString(R.string.graph_input_size_port))){
            choose = 2;
        }else{
            choose = 3;
        }
        this.activity.change_graph(choose);
        this.activity.hide_dialog();
    }

}
