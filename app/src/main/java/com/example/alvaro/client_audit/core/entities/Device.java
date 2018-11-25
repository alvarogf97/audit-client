package com.example.alvaro.client_audit.core.entities;

import android.content.ContentValues;
import com.example.alvaro.client_audit.core.bd.BD;
import com.example.alvaro.client_audit.core.Connection;
import java.util.ArrayList;
import java.util.List;
import static com.example.alvaro.client_audit.core.bd.BDHelper.DEVICE_ID;
import static com.example.alvaro.client_audit.core.bd.BDHelper.DEVICE_IP;
import static com.example.alvaro.client_audit.core.bd.BDHelper.DEVICE_NAME;
import static com.example.alvaro.client_audit.core.bd.BDHelper.DEVICE_PORT;
import static com.example.alvaro.client_audit.core.bd.BDHelper.DEVICE_TABLE;

public class Device {

    private int id;
    private String name;
    private String ip;
    private int port;
    private boolean status;

    public static List<Device> get_all(){
        List<Device> devices = new ArrayList<>();
        String[] columns = {
                DEVICE_ID,
                DEVICE_NAME,
                DEVICE_IP,
                DEVICE_PORT
        };
        String order_by = DEVICE_NAME + " ASC";
        List<String[]> parametrized_params = BD.getInstance().select(DEVICE_TABLE,columns,null,null,
                null,null, order_by);
        for(String[] params : parametrized_params){
            devices.add(new Device(Integer.parseInt(params[0]), params[1], params[2], Integer.parseInt(params[3])));
        }
        return devices;
    }

    private Device(int id, String name, String ip, int port){
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.status = Connection.get_connection().check_device_foreground(this.ip,this.port);
    }

    public Device(String name, String ip, int port){
        this.name = name;
        this.ip = ip;
        this.port = port;

        //insert into database
        ContentValues values = new ContentValues();

        values.put(DEVICE_NAME,this.name);
        values.put(DEVICE_IP,this.ip);
        values.put(DEVICE_PORT,this.port);
        BD.getInstance().insert(DEVICE_TABLE,null,values);

        //getting id
        String  [] columns = {DEVICE_ID};
        String where = DEVICE_IP + " = ?";
        String [] whereargs = {this.ip};
        this.id = Integer.parseInt(BD.getInstance().select(DEVICE_TABLE,columns,where,whereargs,
                null,null, null).get(0)[0]);

        this.status = Connection.get_connection().check_device(this.ip,this.port);
    }

    public void set_ip(String ip){
        this.ip = ip;
        ContentValues values = new ContentValues();
        values.put(DEVICE_IP,this.ip);
        String where = DEVICE_ID + " = ?";
        String [] whereargs = {String.valueOf(this.id)};
        BD.getInstance().update(DEVICE_TABLE,values,where,whereargs);
    }

    public void set_name(String name){
        this.name = name;
        ContentValues values = new ContentValues();
        values.put(DEVICE_NAME,this.name);
        String where = DEVICE_ID + " = ?";
        String [] whereargs = {String.valueOf(this.id)};
        BD.getInstance().update(DEVICE_TABLE,values,where,whereargs);
    }

    public void set_port(int port){
        this.port = port;
        ContentValues values = new ContentValues();
        values.put(DEVICE_PORT,this.port);
        String where = DEVICE_ID + " = ?";
        String [] whereargs = {String.valueOf(this.id)};
        BD.getInstance().update(DEVICE_TABLE,values,where,whereargs);
    }

    public void check_status(){
        this.status = Connection.get_connection().check_device(this.ip,this.port);
    }

    public void check_status_foreground(){
        this.status = Connection.get_connection().check_device_foreground(this.ip,this.port);
    }

    public void delete(){
        String where = DEVICE_ID + " = ?";
        String [] whereargs = {String.valueOf(this.id)};
        BD.getInstance().delete(DEVICE_TABLE,where,whereargs);
    }

    public String get_name(){
        return this.name;
    }

    public String get_ip(){
        return this.ip;
    }

    public int get_port(){
        return this.port;
    }

    public boolean get_status(){
        return this.status;
    }

    public boolean equals(Object o){
        boolean res = false;
        if(o instanceof Device){
            res = this.id == ((Device) o).id;
        }
        return  res;
    }

    public String toString(){
        return this.name;
    }
}
