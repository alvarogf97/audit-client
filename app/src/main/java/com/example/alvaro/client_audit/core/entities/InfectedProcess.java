package com.example.alvaro.client_audit.core.entities;

import org.json.JSONArray;

import java.util.List;

import static com.example.alvaro.client_audit.core.utils.JsonParsers.parse_JSON_string_array;

public class InfectedProcess {

    private String name;
    private String pid;
    private String location;
    private List<String> rules;

    public InfectedProcess(String name, String pid, String location, JSONArray rules){
        this.name = name;
        this.pid = pid;
        this.location = location;
        this.rules = parse_JSON_string_array(rules);
    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getRules() {
        return rules;
    }
}
