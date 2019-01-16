package com.example.alvaro.client_audit.core.entities;

public class StatusFirewall {

    private String name;
    private boolean is_active;

    public StatusFirewall(String name, boolean is_active){
        this.name = name;
        this.is_active = is_active;
    }

    public String getName(){
        return this.name;
    }

    public boolean is_active(){
        return this.is_active;
    }
}
