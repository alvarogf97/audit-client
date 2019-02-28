package com.example.alvaro.client_audit.core.entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.example.alvaro.client_audit.core.utils.JsonParsers.parse_JSON_string_array;

public class InfectedFile {

    private String filename;
    private String file_route;
    private String size;
    private List<String> rules;

    public InfectedFile(String filename, String file_route, String size, JSONArray rules){
        this.filename = filename;
        this.file_route = file_route;
        this.size = size;
        this.rules = parse_JSON_string_array(rules);
    }

    public InfectedFile(JSONObject infected_object){
        try {
            this.filename = infected_object.getString("filename");
            this.file_route = infected_object.getString("file_route");
            this.size = infected_object.getString("size");
            this.rules = parse_JSON_string_array(infected_object.getJSONArray("rules"));
        } catch (JSONException e) {
            Log.e("InfectedProcConstr", Arrays.toString(e.getStackTrace()));
        }
    }

    public static List<InfectedFile> from_JSON_array(JSONArray array){
        List<InfectedFile> infected = new ArrayList<>();
        for(int i = 0; i<array.length(); i++){
            try {
                infected.add(new InfectedFile(array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e("fromJSONarrayFile", Arrays.toString(e.getStackTrace()));
            }
        }
        return infected;
    }

    public String getFilename() {
        return filename;
    }

    public String getFile_route() {
        return file_route;
    }

    public String getSize() {
        return size;
    }

    public List<String> getRules() {
        return rules;
    }
}
