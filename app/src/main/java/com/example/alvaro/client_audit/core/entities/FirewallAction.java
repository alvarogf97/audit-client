package com.example.alvaro.client_audit.core.entities;

import org.json.JSONObject;

public class FirewallAction{

    private String name;
    private JSONObject args;
    private boolean show;

    public FirewallAction(String name, JSONObject args, boolean show){
        this.name = name;
        this.args = args;
        this.show = show;
    }

    public String getName(){
        return this.name;
    }

    public JSONObject getArgs(){
        return this.args;
    }

    public boolean is_visible(){
        return this.show;
    }
}
