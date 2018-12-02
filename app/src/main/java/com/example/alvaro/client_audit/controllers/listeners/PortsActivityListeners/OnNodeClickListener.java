package com.example.alvaro.client_audit.controllers.listeners.PortsActivityListeners;

import android.util.Log;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.unnamed.b.atv.model.TreeNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class OnNodeClickListener implements TreeNode.TreeNodeClickListener {

    private AsyncTaskActivity activity;

    public OnNodeClickListener(AsyncTaskActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        if(node.isLeaf()){
            NodeTreeViewAdapter.NodeItem nodeItem = (NodeTreeViewAdapter.NodeItem) node.getValue();
            String pid = nodeItem.value;
            JSONObject query = new JSONObject();
            try {
                query.put("command","kill");
                query.put("args",pid);
                Connection.get_connection().execute_command(query, this.activity);
            } catch (JSONException e) {
                Log.e("hardware", Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
