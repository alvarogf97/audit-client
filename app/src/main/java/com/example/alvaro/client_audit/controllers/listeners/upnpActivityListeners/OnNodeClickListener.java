package com.example.alvaro.client_audit.controllers.listeners.upnpActivityListeners;

import android.content.Intent;
import android.util.Log;

import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;
import com.example.alvaro.client_audit.activities.actionActivities.UpnpActivity;
import com.example.alvaro.client_audit.controllers.adapters.ActionNodeTreeAdapter;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.unnamed.b.atv.model.TreeNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class OnNodeClickListener implements TreeNode.TreeNodeClickListener {

    private UpnpActivity activity;

    public OnNodeClickListener(UpnpActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        if(node.isLeaf() && node.getValue() instanceof ActionNodeTreeAdapter.Action){
            ActionNodeTreeAdapter.Action action_node = (ActionNodeTreeAdapter.Action) node.getValue();
            NodeTreeViewAdapter.NodeItem service_node = (NodeTreeViewAdapter.NodeItem) node.getParent().getValue();
            String location = action_node.url;
            String service = service_node.name;
            String action = action_node.name;
            JSONObject query = new JSONObject();
            try {
                query.put("location",location);
                query.put("service",service);
                query.put("action",action);
                query.put("args_in", action_node.args_in);
                query.put("args_out", action_node.args_out);
                this.activity.setExecutionArgs(query);
                Intent intent = new Intent(this.activity.getApplicationContext(), UpnpActionActivity.class);
                intent.putExtra("args",query.toString());
                this.activity.startActivity(intent);
            } catch (JSONException e) {
                Log.e("hardware", Arrays.toString(e.getStackTrace()));
            }
        }
    }

}
