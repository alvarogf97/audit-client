package com.example.alvaro.client_audit.activities.actionActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsynkTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.example.alvaro.client_audit.controllers.listeners.PortsActivityListeners.OnNodeClickListener;
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

public class ProcessesActivity extends AsynkTaskActivity {

    private TreeNode root;
    private List<TreeNode> nodes;
    private AndroidTreeView tView;
    private SpinKitView loader;
    private JSONObject response;
    private RelativeLayout layout;
    private boolean in_process = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processes);

        this.layout = (RelativeLayout) findViewById(R.id.process_layout);
        loader = (SpinKitView) findViewById(R.id.processes_anim_load);
        this.start_animation();

    }

    @Override
    public void start_animation() {
        if(!in_process){
            loader.setVisibility(View.VISIBLE);
            loader.setIndeterminateDrawable(this.w);
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

    @Override
    public void stop_animation(Object... objects) {
        response = (JSONObject) objects[0];
        try {
            if(response.get("data") instanceof JSONArray){
                try {
                    nodes = this.getNodes(response.getJSONArray("data"));
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

    public List<TreeNode> getNodes(JSONArray data) throws JSONException {
        List<TreeNode> nodes = new ArrayList<>();
        for(int i = 0; i<data.length(); i++){
            JSONObject process_data = data.getJSONObject(i);
            String pid = String.valueOf(process_data.getInt("pid"));
            String name = process_data.getString("name");
            NodeTreeViewAdapter.NodeItem process_node =
                        new NodeTreeViewAdapter.NodeItem(name,pid, R.drawable.ic_process,1);
            TreeNode new_process_node = new TreeNode(process_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
            nodes.add(new_process_node);
            }

        return nodes;
    }
}
