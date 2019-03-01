package com.example.alvaro.client_audit.core.entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Document {

    private boolean is_file;
    private String name;
    private String abs_path;

    public Document(String name, String abs_path, boolean is_file){
        this.name = name;
        this.abs_path = abs_path;
        this.is_file = is_file;
    }

    public Document(JSONObject serialized_document){
        try {
            this.name = serialized_document.getString("name");
            this.abs_path = serialized_document.getString("abs_path");
            this.is_file = serialized_document.getBoolean("is_file");
        } catch (JSONException e) {
            Log.e("serializeDocument", Arrays.toString(e.getStackTrace()));
        }
    }

    public static List<Document> from_JSON_array(JSONArray array){
        List<Document> result = new ArrayList<>();
        for(int i = 0; i<array.length(); i++){
            try {
                result.add(new Document(array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e("serializeDocument", Arrays.toString(e.getStackTrace()));
            }
        }
        return result;
    }

    public boolean isIs_file() {
        return is_file;
    }

    public String getName() {
        return name;
    }

    public String getAbs_path() {
        return abs_path;
    }
}
