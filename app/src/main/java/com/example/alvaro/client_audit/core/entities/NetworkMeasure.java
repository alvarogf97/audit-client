package com.example.alvaro.client_audit.core.entities;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<NetworkMeasure> mapped_list = NetworkMeasure.map_time(measure_list);
        DataPoint[] dataPoints = new DataPoint[mapped_list.size()];
        int index = 0;
        int min = mapped_list.get(0).getSize();
        int max = mapped_list.get(0).getSize();
        for(NetworkMeasure m : mapped_list){
            dataPoints[index] = new DataPoint(index, m.getSize());
            if(m.getSize() < min){
                min = m.getSize();
            }
            if(m.getSize() > max){
                max = m.getSize();
            }
            index++;
        }
        return normalize(dataPoints, min, max);
    }

    private static DataPoint[] normalize(DataPoint[] dataPoints, int min, int max){
        DataPoint[] result = new DataPoint[dataPoints.length];
        for(int i = 0; i<dataPoints.length; i++){
            DataPoint dp = dataPoints[i];
            result[i] = new DataPoint(dp.getX(),((dp.getY()-min)/(max-min)));
        }
        return result;
    }

    public static List<Object> to_size_port(List<NetworkMeasure> measure_list){
        List<NetworkMeasure> mapped_list = NetworkMeasure.map_port(measure_list);
        Map<Integer, Integer> port_dict = new HashMap<>();
        DataPoint[] dataPoints = new DataPoint[mapped_list.size()];
        int index = 0;
        int min = mapped_list.get(0).getSize();
        int max = mapped_list.get(0).getSize();
        for(NetworkMeasure m : mapped_list ){
            dataPoints[index] = new DataPoint(index,m.getSize());
            port_dict.put(index, m.getPort());
            if(m.getSize() < min){
                min = m.getSize();
            }
            if(m.getSize() > max){
                max = m.getSize();
            }
            index++;
        }
        List<Object> result = new ArrayList<>();
        result.add(normalize(dataPoints, min, max));
        result.add(port_dict);
        return result;
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
        result.sort(new Comparator<NetworkMeasure>() {
            @Override
            public int compare(NetworkMeasure o1, NetworkMeasure o2) {
                return Double.compare(o1.getTimestamp(), o2.getTimestamp());
            }
        });
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
        result.sort(new Comparator<NetworkMeasure>() {
            @Override
            public int compare(NetworkMeasure o1, NetworkMeasure o2) {
                return Integer.compare(o1.getPort(), o2.getPort());
            }
        });
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
