package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;
import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.ShowRules;
import com.example.alvaro.client_audit.controllers.adapters.NodeTreeViewAdapter;

public class DeleteRuleButonClickListener implements View.OnClickListener {

    private ShowRules activity;
    private NodeTreeViewAdapter.NodeItem node;

    public DeleteRuleButonClickListener(ShowRules activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.delete_query(Integer.parseInt(node.name));
    }

    public void setNode(NodeTreeViewAdapter.NodeItem node){
        this.node = node;
    }
}
