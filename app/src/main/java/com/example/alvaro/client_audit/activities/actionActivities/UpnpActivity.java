package com.example.alvaro.client_audit.activities.actionActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.ActionNodeTreeAdapter;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.example.alvaro.client_audit.controllers.listeners.upnpActivityListeners.OnNodeClickListener;
import com.example.alvaro.client_audit.controllers.listeners.upnpActivityListeners.FilterTextListener;
import com.example.alvaro.client_audit.controllers.listeners.upnpActivityListeners.ReScanButtonClickListener;
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

public class UpnpActivity extends AsyncTaskActivity {

    private TreeNode root;
    private List<TreeNode> nodes;
    private AndroidTreeView tView;
    private SpinKitView loader;
    private JSONObject response;
    private JSONObject execution_args;
    private RelativeLayout layout;
    private String filter;
    private TextView search;
    private Button rescan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upnp);

        search = (TextView) findViewById(R.id.search_upnp_node);
        search.addTextChangedListener(new FilterTextListener(this));
        filter = search.getText().toString();
        this.layout = (RelativeLayout) findViewById(R.id.upnp_layout);
        loader = (SpinKitView) findViewById(R.id.upnp_anim_load);
        rescan = (Button) findViewById(R.id.upnp_rescan_button);
        rescan.setOnClickListener(new ReScanButtonClickListener(this));
        this.start_animation();
    }

    public void execute_query_search(){
        JSONObject query = new JSONObject();
        try {
            query.put("command","upnp devices");
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("upnp", Arrays.toString(e.getStackTrace()));
        }
    }

    public void execute_query_action(){
        JSONObject query = new JSONObject();
        try {
            query.put("command","upnp exec");
            query.put("args",execution_args);
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("upnp", Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void start_animation() {
        rescan.setEnabled(false);
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(this.w);
        search.setEnabled(false);
        if(tView != null){
            this.layout.removeAllViews();
            this.layout.addView(loader);
        }
        execute_query_search();
    }

    @Override
    public void stop_animation(Object... objects) {
        response = (JSONObject) objects[0];
        try {
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
            rescan.setEnabled(true);
        } catch (Exception e) {
            Log.e("stopAnimationPorts",Arrays.toString(e.getStackTrace()));
        }
    }

    public void setNodes(List<TreeNode> nodes){
        root = TreeNode.root();
        root.addChildren(nodes);
        tView = new AndroidTreeView(this, root);
        tView.setDefaultNodeClickListener(new OnNodeClickListener(this));
        tView.setDefaultAnimation(true);
        this.layout.removeAllViews();
        this.layout.addView(tView.getView());
    }

    public List<TreeNode> getNodes(JSONArray data, String filter) throws JSONException {
        List<TreeNode> nodes = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject device = data.getJSONObject(i);
            String device_name = device.getString("name");

            if(device_name.toLowerCase().startsWith(filter.toLowerCase())){
                String device_locations = device.getString("location");
                JSONArray services_data = device.getJSONArray("services");
                List<TreeNode> services = new ArrayList<>();

                for(int j = 0; j<services_data.length(); j++){
                    JSONObject service = services_data.getJSONObject(j);
                    String service_name = service.getString("id");
                    JSONArray actions_data = service.getJSONArray("actions");
                    List<TreeNode> actions = new ArrayList<>();

                    for(int k = 0; k<actions_data.length(); k++){
                        JSONObject action = actions_data.getJSONObject(k);
                        String action_name = action.getString("name");
                        JSONArray args_in = action.getJSONArray("args_in");
                        JSONArray args_out = action.getJSONArray("args_out");
                        String url = action.getString("url");

                        ActionNodeTreeAdapter.Action action_node =
                                new ActionNodeTreeAdapter.Action(action_name,args_in, args_out,url,3);
                        TreeNode new_action = new TreeNode(action_node).setViewHolder(new ActionNodeTreeAdapter(this.getApplicationContext()));
                        actions.add(new_action);
                    }

                    NodeTreeViewAdapter.NodeItem service_node =
                            new NodeTreeViewAdapter.NodeItem(service_name,"", R.drawable.ic_process,2);
                    TreeNode new_service = new TreeNode(service_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                    new_service.addChildren(actions);
                    services.add(new_service);
                }


                NodeTreeViewAdapter.NodeItem device_node =
                        new NodeTreeViewAdapter.NodeItem(device_name,"", R.drawable.ic_system,1);
                TreeNode new_device = new TreeNode(device_node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                new_device.addChildren(services);
                nodes.add(new_device);
            }

        }

        return nodes;


    }

    public void setFilter(String filter){
        this.filter = filter;
    }

    public String getFilter(){
        return this.filter;
    }

    public JSONObject getResponse(){
        return this.response;
    }

    public void setExecutionArgs(JSONObject args){
        this.execution_args = args;
    }
}
