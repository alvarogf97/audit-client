package com.example.alvaro.client_audit.core.utils;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

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


}
