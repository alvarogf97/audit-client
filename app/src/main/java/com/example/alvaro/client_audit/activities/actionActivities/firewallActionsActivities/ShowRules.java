package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.CreateRuleButonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.DeleteRuleButonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.UpdateRulesButtonClickListener;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
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

public class ShowRules extends AsyncTaskActivity {

    private TreeNode root;
    private List<TreeNode> nodes;
    private AndroidTreeView tView;
    private JSONObject response;
    private RelativeLayout layout;
    private SpinKitView loader;
    private FloatingActionButton create_button;
    private FloatingActionButton reload_rules;
    public boolean is_getting_rules;
    public boolean is_deleting_rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rules);
        is_getting_rules = false;
        is_deleting_rule = false;
        this.loader = (SpinKitView) findViewById(R.id.show_rules_anim_load);
        this.layout = (RelativeLayout) findViewById(R.id.rule_nodes_layout);
        this.create_button = (FloatingActionButton) findViewById(R.id.b_add_rule);
        this.reload_rules = (FloatingActionButton) findViewById(R.id.b_update_rules);
        this.reload_rules.setOnClickListener(new UpdateRulesButtonClickListener(this));
        this.create_button.setOnClickListener(new CreateRuleButonClickListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.start_animation();
    }

    @Override
    public void start_animation() {
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(this.w);
        this.reload_rules.setEnabled(false);
        this.create_button.setEnabled(false);
        if(tView != null){
            this.layout.removeAllViews();
        }
        this.execute_query();
    }

    @Override
    public void stop_animation(Object... objects) {
        this.response = (JSONObject) objects[0];
        try {
            if(response.getBoolean("status") && this.is_getting_rules){
                this.is_getting_rules = false;
                this.nodes = this.getNodes(response.getJSONArray("data"));
                this.setNodes(nodes);
                loader.setVisibility(View.GONE);
                this.reload_rules.setEnabled(true);
                this.create_button.setEnabled(true);
            }else if(this.is_deleting_rule){
                this.is_getting_rules = false;
                Toast toast;
                if(response.getBoolean("status")){
                    toast = Toast.makeText(this.getApplicationContext(), "Rule delete successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    layout.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                    this.reload_rules.setEnabled(true);
                    this.create_button.setEnabled(true);
                    this.start_animation();
                }else{
                    toast = Toast.makeText(this.getApplicationContext(), "Whoops, some errors found :(", Toast.LENGTH_SHORT);
                    toast.show();
                    layout.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                    this.reload_rules.setEnabled(true);
                    this.create_button.setEnabled(true);
                }
            }
        } catch (JSONException e) {
            Log.e("view_rules", Arrays.toString(e.getStackTrace()));
        }
    }

    private void execute_query(){
        FirewallAction get_files_action = FirewallActivity.getActionByName("view rules");
        JSONObject query = new JSONObject();
        try {
            query.put("command",get_files_action.getCommand());
            query.put("args",get_files_action.getArgs());
            Connection.get_connection().execute_command(query, this);
            this.is_getting_rules = true;
        } catch (JSONException e) {
            Log.e("view_rules_query", Arrays.toString(e.getStackTrace()));
        }
    }

    public void delete_query(int rule_number){
        FirewallAction get_files_action = FirewallActivity.getActionByName("remove rule");
        JSONObject query = new JSONObject();
        JSONObject args = new JSONObject();
        try {
            layout.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
            this.reload_rules.setEnabled(false);
            this.create_button.setEnabled(false);
            query.put("command",get_files_action.getCommand());
            args.put("number",rule_number);
            query.put("args",args);
            Connection.get_connection().execute_command(query, this);
            this.is_deleting_rule = true;
        } catch (JSONException e) {
            Log.e("view_rules_query", Arrays.toString(e.getStackTrace()));
        }
    }

    public List<TreeNode> getNodes(JSONArray data) throws JSONException{
        List<TreeNode> nodes = new ArrayList<>();
        for(int i = 0; i<data.length(); i++){
            JSONObject json_rule = data.getJSONObject(i);
            String rule_number = String.valueOf(json_rule.getInt("number"));
            DeleteRuleButonClickListener listener = new DeleteRuleButonClickListener(this);
            NodeTreeViewAdapter.NodeItem node =
                    new NodeTreeViewAdapter.NodeItem(rule_number,"", R.drawable.ic_fw_rule,0, true,listener);
            listener.setNode(node);
            TreeNode new_node = new TreeNode(node).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
            JSONObject rule_kwargs = json_rule.getJSONObject("kwargs");
            List<TreeNode> kwargs = new ArrayList<>();
            Iterator<String> args = rule_kwargs.keys();
            while(args.hasNext()){
                String arg_name = args.next();
                NodeTreeViewAdapter.NodeItem node_arg =
                        new NodeTreeViewAdapter.NodeItem(arg_name,rule_kwargs.getString(arg_name), R.drawable.ic_rule_attr,1);
                TreeNode new_arg = new TreeNode(node_arg).setViewHolder(new NodeTreeViewAdapter(this.getApplicationContext()));
                kwargs.add(new_arg);
            }
            new_node.addChildren(kwargs);
            nodes.add(new_node);
        }
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes){
        root = TreeNode.root();
        root.addChildren(nodes);
        tView = new AndroidTreeView(this, root);
        //tView.setDefaultNodeClickListener(new OnNodeClickListener(this));
        tView.setDefaultAnimation(true);
        this.layout.removeAllViews();
        this.layout.addView(tView.getView());
    }
}
