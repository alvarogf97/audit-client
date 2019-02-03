package com.example.alvaro.client_audit.core.entities;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkMeasure {

    private int port;
    private String process_name;
    private int size;
    private double timestamp;
    private String hour;
    private boolean is_input;

    public NetworkMeasure(JSONObject json_measure){
        try {
            this.port = json_measure.getInt("port");
            this.process_name = json_measure.getString("process_name");
            this.size = json_measure.getInt("size");
            this.timestamp = json_measure.getDouble("timestamp");
            this.hour = json_measure.getString("hour");
            this.is_input = json_measure.getBoolean("is_input");
        } catch (JSONException e) {
            Log.e("NMConstruct", Arrays.toString(e.getStackTrace()));
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProcess_name() {
        return process_name;
    }

    public void setProcess_name(String process_name) {
        this.process_name = process_name;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean isIs_input() {
        return is_input;
    }

    public void setIs_input(boolean is_input) {
        this.is_input = is_input;
    }

    public JSONObject to_json(){
        JSONObject json_measure = new JSONObject();
        try {
            json_measure.put("port", this.port);
            json_measure.put("size", this.size);
            json_measure.put("timestamp", this.timestamp);
            json_measure.put("is_input", this.is_input);
        } catch (JSONException e) {
            Log.e("MSTJ", Arrays.toString(e.getStackTrace()));
        }
        return json_measure;
    }

    public static DataPoint[] to_size_time(List<NetworkMeasure> measure_list){
        DataPoint[] dataPoints = new DataPoint[measure_list.size()];
        int index = 0;
        for(NetworkMeasure m : NetworkMeasure.map_time(measure_list) ){
            dataPoints[index] = new DataPoint(m.getSize(), m.getTimestamp());
        }
        return dataPoints;
    }

    public static DataPoint[] to_size_port(List<NetworkMeasure> measure_list){
        DataPoint[] dataPoints = new DataPoint[measure_list.size()];
        int index = 0;
        for(NetworkMeasure m : NetworkMeasure.map_port(measure_list) ){
            dataPoints[index] = new DataPoint(m.getSize(), m.getPort());
        }
        return dataPoints;
    }

    private static List<NetworkMeasure> map_time(List<NetworkMeasure> measureList){
        List<NetworkMeasure> result = new ArrayList<>();
        for (NetworkMeasure m : measureList) {
            int index = NetworkMeasure.contains_by_time(result, m);
            if(index != -1){
                result.get(index).setSize(result.get(index).getSize() + m.getSize());
            }else{
                result.add(m);
            }
        }
        return result;
    }

    private static List<NetworkMeasure> map_port(List<NetworkMeasure> measureList){
        List<NetworkMeasure> result = new ArrayList<>();
        for (NetworkMeasure m : measureList) {
            int index = NetworkMeasure.contains_by_port(result, m);
            if(index != -1){
                result.get(index).setSize(result.get(index).getSize() + m.getSize());
            }else{
                result.add(m);
            }
        }
        return result;
    }

    private static int contains_by_time(List<NetworkMeasure> measureList, NetworkMeasure measure){
        boolean found = false;
        int i = 0;
        while(i < measureList.size() && !found){
            if(measureList.get(i).getTimestamp() == measure.getTimestamp()){
                found = true;
            }
            i++;
        }

        if (found){
            i--;
        }else{
            i = -1;
        }
        return i;
    }

    private static int contains_by_port(List<NetworkMeasure> measureList, NetworkMeasure measure){
        boolean found = false;
        int i = 0;
        while(i < measureList.size() && !found){
            if(measureList.get(i).getPort() == measure.getPort()){
                found = true;
            }
            i++;
        }

        if (found){
            i--;
        }else{
            i = -1;
        }
        return i;
    }

    public static List<NetworkMeasure> get_list_from_json(JSONArray measure_array){
        List<NetworkMeasure> result = new ArrayList<>();
        for(int i = 0; i<measure_array.length(); i++){
            try {
                result.add(new NetworkMeasure(measure_array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e("measureList", Arrays.toString(e.getStackTrace()));
            }
        }
        return result;
    }

}
