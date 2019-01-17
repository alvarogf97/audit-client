package com.example.alvaro.client_audit.core.entities;

import org.json.JSONObject;

public class FirewallAction{

    private String name;
    private JSONObject args;
    private boolean show;
    private String command;

    public FirewallAction(String name, JSONObject args, boolean show, String command){
        this.name = name;
        this.args = args;
        this.show = show;
        this.command = command;
    }

    public String getName(){
        return this.name;
    }

    public String getCommand(){
        return this.command;
    }

    public JSONObject getArgs(){
        return this.args;
    }

    public boolean is_visible(){
        return this.show;
    }
}
