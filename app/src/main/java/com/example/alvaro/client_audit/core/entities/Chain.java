package com.example.alvaro.client_audit.core.entities;

import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.ChangeChainPolicyButtonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.DeleteChainButtonClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.FlushChainButtonClickListener;

public class Chain {

    private String name;
    private String policy;
    private boolean is_removable;
    private ChangeChainPolicyButtonClickListener change_policy_listener;
    private FlushChainButtonClickListener flush_listener;
    private DeleteChainButtonClickListener delete_listener;

    public Chain(String name, String policy, boolean is_removable){
        this.name = name;
        this.policy = policy;
        this.is_removable = is_removable;
    }

    public String getName(){
        return this.name;
    }

    public String getPolicy(){
        return this.policy;
    }

    public void changePolicy(){
        if(this.policy.equals("ACCEPT")){
            this.policy = "DROP";
        }else{
            this.policy = "ACCEPT";
        }
    }

    public boolean isRemovable(){
        return this.is_removable;
    }

    public ChangeChainPolicyButtonClickListener getChange_policy_listener() {
        return change_policy_listener;
    }

    public FlushChainButtonClickListener getFlush_listener() {
        return flush_listener;
    }

    public DeleteChainButtonClickListener getDelete_listener() {
        return delete_listener;
    }

    public void setChange_policy_listener(ChangeChainPolicyButtonClickListener change_policy_listener) {
        this.change_policy_listener = change_policy_listener;
    }

    public void setFlush_listener(FlushChainButtonClickListener flush_listener) {
        this.flush_listener = flush_listener;
    }

    public void setDelete_listener(DeleteChainButtonClickListener delete_listener) {
        this.delete_listener = delete_listener;
    }
}
