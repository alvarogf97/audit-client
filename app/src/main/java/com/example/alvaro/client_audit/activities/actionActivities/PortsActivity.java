package com.example.alvaro.client_audit.activities.actionActivities;

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

public class PortsActivity extends AsynkTaskActivity {

    private TreeNode root;
    private List<TreeNode> nodes;
    private AndroidTreeView tView;
    private SpinKitView loader;
    private JSONObject response;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ports);

        this.layout = (RelativeLayout) findViewById(R.id.ports_layout);
        loader = (SpinKitView) findViewById(R.id.ports_anim_load);
        this.start_animation();

    }

    @Override
    public void start_animation() {
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(this.w);
        if(tView != null){
            this.layout.removeAllViews();
            this.layout.addView(loader);
        }
        JSONObject query = new JSONObject();
        try {
            query.put("command","ports");
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("hardware", Arrays.toString(e.getStackTrace()));
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

    }

    public List<TreeNode> getNodes(JSONArray data) throws JSONException {
        List<TreeNode> nodes = new ArrayList<>();

        for(int i = 0; i<data.length(); i++){
            JSONObject port_data = data.getJSONObject(i);
            String port = String.valueOf(port_data.getInt("port"));
            NodeTreeViewAdapter.NodeItem node =
                    new NodeTreeViewAdapter.NodeItem(port,"", R.drawable.ic_port,0);
            TreeNode new_node = new TreeNode(node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
            JSONArray processes_data = port_data.getJSONArray("processes");
            List<TreeNode> processes_nodes = new ArrayList<>();
            for(int j = 0; j<processes_data.length(); j++){
                JSONObject process_data = processes_data.getJSONObject(j);
                String pid = String.valueOf(process_data.getInt("pid"));
                String name = process_data.getString("name");
                NodeTreeViewAdapter.NodeItem process_node =
                        new NodeTreeViewAdapter.NodeItem(name,pid, R.drawable.ic_process,1);
                TreeNode new_process_node = new TreeNode(process_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                processes_nodes.add(new_process_node);
            }
            new_node.addChildren(processes_nodes);
            nodes.add(new_node);
        }

        return nodes;
    }


}
