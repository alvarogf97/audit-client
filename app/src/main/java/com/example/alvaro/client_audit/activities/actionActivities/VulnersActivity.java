package com.example.alvaro.client_audit.activities.actionActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.example.alvaro.client_audit.controllers.listeners.PortsActivityListeners.OnNodeClickListener;
import com.example.alvaro.client_audit.controllers.listeners.VulnersActivityListener.FilterTextListener;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VulnersActivity extends AsyncTaskActivity {

    private TreeNode root;
    private List<TreeNode> nodes;
    private AndroidTreeView tView;
    private SpinKitView loader;
    private JSONObject response;
    private RelativeLayout layout;
    private String filter;
    private TextView search;
    private TextView status;

    /*
        On create
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vulners);

        search = (TextView) findViewById(R.id.search_vulner_node);
        status = (TextView) findViewById(R.id.vulner_status);
        search.addTextChangedListener(new FilterTextListener(this));
        filter = search.getText().toString();
        this.layout = (RelativeLayout) findViewById(R.id.vulner_layout);
        loader = (SpinKitView) findViewById(R.id.vulner_anim_load);
        this.start_animation();
    }


    public void execute_query(){
        JSONObject query = new JSONObject();
        try {
            query.put("command","vulners");
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("vulners", Arrays.toString(e.getStackTrace()));
        }
    }

    /*
        start animation while asyncTask
     */

    @Override
    public void start_animation() {
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(this.w);
        status.setVisibility(View.VISIBLE);
        search.setEnabled(false);
        if(tView != null){
            this.layout.removeAllViews();
            this.layout.addView(loader);
        }
        execute_query();
    }

    /*
        called by asyncTask when it terminates
     */

    @Override
    public void stop_animation(Object... objects) {
        response = (JSONObject) objects[0];
        try{
            if(response.getBoolean("status")){
                try {
                    nodes = this.getNodes(response.getJSONArray("data"), filter);
                } catch (JSONException e) {
                    nodes = new ArrayList<>();
                }
                root = TreeNode.root();
                root.addChildren(nodes);
                tView = new AndroidTreeView(this, root);
                tView.setDefaultAnimation(true);
                this.layout.addView(tView.getView());
                loader.setVisibility(View.GONE);
                status.setVisibility(View.GONE);
                search.setEnabled(true);
            }else{
                String status_str = response.getString("data");
                this.status.setText(status_str);
                execute_query();
            }
        } catch (JSONException e) {
            Log.e("stopVulner",Arrays.toString(e.getStackTrace()));
        }

    }

    public List<TreeNode> getNodes(JSONArray data, String filter) throws JSONException {
        List<TreeNode> nodes = new ArrayList<>();
        for(int i = 0; i<data.length(); i++){
            JSONObject vulner_data = data.getJSONObject(i);
            JSONObject packet_data = vulner_data.getJSONObject("Package");
            String name = packet_data.getString("name");
            String version = packet_data.getString("version");
            if(name.toLowerCase().startsWith(filter.toLowerCase())){
                NodeTreeViewAdapter.NodeItem packet_node =
                        new NodeTreeViewAdapter.NodeItem(name,version, R.drawable.ic_vulner,1);
                TreeNode new_packet = new TreeNode(packet_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                List<TreeNode> vulnerabilities = new ArrayList<>();
                JSONArray vulner_features = vulner_data.getJSONArray("Vulnerabilities");

                for(int j = 0; j<vulner_features.length(); j++){

                    JSONObject vuln = vulner_features.getJSONObject(j);
                    Iterator<String> keys = vuln.keys();
                    List<TreeNode> features = new ArrayList<>();
                    TreeNode new_vulner = null;

                    while(keys.hasNext()){
                        String key = keys.next();
                        String value = vuln.getString(key);
                        if(key.equals("Title")){
                            NodeTreeViewAdapter.NodeItem vulner_node =
                                    new NodeTreeViewAdapter.NodeItem(key,value, R.drawable.ic_bug,2);
                            new_vulner = new TreeNode(vulner_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                        }else{
                            if(!value.equals("")){
                                NodeTreeViewAdapter.NodeItem vulner_node =
                                        new NodeTreeViewAdapter.NodeItem(key,value, R.drawable.ic_hardware_default,3);
                                TreeNode new_feature = new TreeNode(vulner_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                                features.add(new_feature);
                            }
                        }
                    }
                    new_vulner.addChildren(features);
                    vulnerabilities.add(new_vulner);
                }
                new_packet.addChildren(vulnerabilities);
                nodes.add(new_packet);
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
}
