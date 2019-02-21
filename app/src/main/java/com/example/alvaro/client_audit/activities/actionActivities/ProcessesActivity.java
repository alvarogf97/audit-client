package com.example.alvaro.client_audit.activities.actionActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.example.alvaro.client_audit.controllers.listeners.portsActivityListeners.OnNodeClickListener;
import com.example.alvaro.client_audit.controllers.listeners.processeActivitiListeners.FilterTextListener;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessesActivity extends AsyncTaskActivity {

    private TreeNode root;
    private List<TreeNode> nodes;
    private AndroidTreeView tView;
    private SpinKitView loader;
    private JSONObject response;
    private RelativeLayout layout;
    private String filter;
    private TextView search;
    private boolean in_process = false;

    /*
        On create
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processes);

        search = (TextView) findViewById(R.id.search_node);
        search.addTextChangedListener(new FilterTextListener(this));
        filter = search.getText().toString();
        this.layout = (RelativeLayout) findViewById(R.id.process_layout);
        loader = (SpinKitView) findViewById(R.id.processes_anim_load);
        this.start_animation();

    }

    /*
        start animation while asyncTask
     */

    @Override
    public void start_animation() {
        if(!in_process){
            loader.setVisibility(View.VISIBLE);
            loader.setIndeterminateDrawable(this.w);
            search.setEnabled(false);
            if(tView != null){
                this.layout.removeAllViews();
                this.layout.addView(loader);
            }
            JSONObject query = new JSONObject();
            try {
                query.put("command","ps");
                Connection.get_connection().execute_command(query, this);
                in_process = true;
            } catch (JSONException e) {
                Log.e("hardware", Arrays.toString(e.getStackTrace()));
            }
        }
    }

    /*
        called by asyncTask when it terminates
     */

    @Override
    public void stop_animation(Object... objects) {
        response = (JSONObject) objects[0];
        try {
            if(response.get("data") instanceof JSONArray){
                try {
                    nodes = this.getNodes(response.getJSONArray("data"), filter);
                } catch (JSONException e) {
                    nodes = new ArrayList<>();
                }
                root = TreeNode.root();
                root.addChildren(nodes);
                tView = new AndroidTreeView(this, root);
                tView.setDefaultNodeClickListener(new OnNodeClickListener(this));
                tView.setDefaultAnimation(true);
                this.layout.addView(tView.getView());
                loader.setVisibility(View.GONE);
                search.setEnabled(true);
            }else{
                String result = response.getString("data");
                Toast toast = Toast.makeText(this.getApplicationContext(),result,Toast.LENGTH_SHORT);
                toast.show();
                if(response.getBoolean("status")){
                    this.start_animation();
                }
            }
        } catch (Exception e) {
            Log.e("stopAnimationPorts",Arrays.toString(e.getStackTrace()));
        }
        in_process =false;
    }

    /*
        Construct TreeView
            * recursive method
     */

    public List<TreeNode> getNodes(JSONArray data, String filter) throws JSONException {
        List<TreeNode> nodes = new ArrayList<>();
        for(int i = 0; i<data.length(); i++){
            JSONObject process_data = data.getJSONObject(i);
            String pid = String.valueOf(process_data.getInt("pid"));
            String name = process_data.getString("name");
            if(name.toLowerCase().startsWith(filter.toLowerCase())){
                NodeTreeViewAdapter.NodeItem process_node =
                        new NodeTreeViewAdapter.NodeItem(name,pid, R.drawable.ic_process,1);
                TreeNode new_process_node = new TreeNode(process_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                nodes.add(new_process_node);
            }
        }

        return nodes;
    }

    /*
        method to change view when editText changes
     */

    public void setNodes(List<TreeNode> nodes){
        root = TreeNode.root();
        root.addChildren(nodes);
        tView = new AndroidTreeView(this, root);
        tView.setDefaultNodeClickListener(new OnNodeClickListener(this));
        tView.setDefaultAnimation(true);
        this.layout.removeAllViews();
        this.layout.addView(tView.getView());
    }

    /*
        getter's & setter's
     */

    public void setFilter(String filter){
        this.filter = filter;
    }

    public String getFilter(){
        return this.filter;
    }

    public JSONObject getResponse(){
        return this.response;
    }

    @Override
    public void onBackPressed() {
        if (!in_process) {
            super.onBackPressed();
        }
    }


}
