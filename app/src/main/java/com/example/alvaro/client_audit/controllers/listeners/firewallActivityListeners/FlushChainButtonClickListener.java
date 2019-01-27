package com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners;

import android.view.View;

import com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities.ChainActivity;
import com.example.alvaro.client_audit.core.entities.Chain;

public class FlushChainButtonClickListener implements View.OnClickListener {
    private ChainActivity activity;
    private Chain chain;

    public FlushChainButtonClickListener(ChainActivity activity, Chain chain){
        this.activity = activity;
        this.chain = chain;
    }

    @Override
    public void onClick(View v) {
        this.activity.flush_chain(chain);
    }
}
