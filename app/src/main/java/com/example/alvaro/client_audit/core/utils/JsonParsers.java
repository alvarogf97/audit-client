package com.example.alvaro.client_audit.core.utils;

import android.util.Log;

import com.example.alvaro.client_audit.core.entities.Argument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JsonParsers {

    public static JSONObject parse_string(String str){
        JSONObject result = null;
        try {
            result = new JSONObject(str);
        } catch (Exception e) {
            Log.e("Json", Arrays.toString(e.getStackTrace()));
        }
        return result;
    }

    public static JSONObject parse_firewall_arguments(List<Argument> arguments_data){
        JSONObject arguments = new JSONObject();
        for(Argument arg : arguments_data){
            try {
                arguments.put(arg.getName(),arg.getValue());
            } catch (JSONException e) {
                Log.e("parse_fw_args", Arrays.toString(e.getStackTrace()));
            }
        }
        return arguments;
    }

    public static List<Argument> parse_JSON_firewall_arguments(JSONObject arguments_object){
        List<Argument> arguments = new ArrayList<>();
        Iterator<String> iterator = arguments_object.keys();
        while(iterator.hasNext()){
            String next_key = iterator.next();
            arguments.add(new Argument(next_key));
        }
        return arguments;
    }

    public static List<String> parse_JSON_string_array(JSONArray array){
        List<String> result = new ArrayList<>();
        for(int i = 0; i<array.length(); i++){
            try {
                result.add(array.getString(i));
            } catch (JSONException e) {
                Log.e("parsinStringArray", Arrays.toString(e.getStackTrace()));
            }
        }
        return result;
    }


}
