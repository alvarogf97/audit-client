package com.example.alvaro.client_audit.core.entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
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

    public InfectedProcess(JSONObject infected_object){
        try {
            this.name = infected_object.getString("name");
            this.pid = infected_object.getString("pid");
            this.location = infected_object.getString("location");
            this.rules = parse_JSON_string_array(infected_object.getJSONArray("rules"));
        } catch (JSONException e) {
            Log.e("InfectedProcConstr", Arrays.toString(e.getStackTrace()));
        }
    }

    public static List<InfectedProcess> from_JSON_array(JSONArray array){
        List<InfectedProcess> infected = new ArrayList<>();
        for(int i = 0; i<array.length(); i++){
            try {
                infected.add(new InfectedProcess(array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e("fromJSONarrayProc", Arrays.toString(e.getStackTrace()));
            }
        }
        return infected;
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
