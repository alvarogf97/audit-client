package com.example.alvaro.client_audit.core.entities;

public class Chain {

    private String name;
    private String policy;
    private boolean is_removable;

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
}
