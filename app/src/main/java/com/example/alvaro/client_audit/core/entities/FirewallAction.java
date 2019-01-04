package com.example.alvaro.client_audit.core.entities;

import org.json.JSONObject;

public class FirewallAction{

    private String name;
    private JSONObject args;

    public FirewallAction(String name, JSONObject args){
        this.name = name;
        this.args = args;
    }

    public String getName(){
        return this.name;
    }

    public JSONObject getArgs(){
        return this.args;
    }
}
