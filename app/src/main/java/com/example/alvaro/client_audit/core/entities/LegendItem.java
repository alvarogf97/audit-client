package com.example.alvaro.client_audit.core.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LegendItem {

    private int index;
    private int port;

    public LegendItem(int index, int port){
        this.index = index;
        this.port = port;
    }

    public int getIndex() {
        return index;
    }

    public int getPort() {
        return port;
    }

    public static List<LegendItem> getItemsFromMap(Map<Integer,Integer> map){
        List<LegendItem> result = new ArrayList<>();
        for(Integer key : map.keySet()){
            result.add(new LegendItem(key, map.get(key)));
        }
        return result;
    }
}
