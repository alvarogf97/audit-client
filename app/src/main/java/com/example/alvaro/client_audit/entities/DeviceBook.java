package com.example.alvaro.client_audit.entities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class DeviceBook {

    private static DeviceBook instance;
    private static Context context;
    private List<Device> devices;

    private DeviceBook(){
        this.devices = Device.get_all();
    }

    public static DeviceBook get_instance(){
        if(instance == null){
            instance = new DeviceBook();
        }

        return instance;
    }

    public static void set_context(Context context_application){
        context = context_application;
    }

    public void add_device(String name, String ip, int port){
        try{
            Device d = new Device(name, ip, port);
            this.devices.add(d);
        }catch (Exception e){
            Log.e("DeviceBook::add", Arrays.toString(e.getStackTrace()));
            Toast toast = Toast.makeText(this.context, "Cannot create this device", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void delete_device(Device device){
        this.devices.remove(device);
        device.delete();
    }
}
