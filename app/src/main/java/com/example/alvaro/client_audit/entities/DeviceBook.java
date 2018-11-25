package com.example.alvaro.client_audit.entities;


import android.util.Log;
import com.example.alvaro.client_audit.adapters.CardsAdapter;
import java.util.Arrays;
import java.util.List;

public class DeviceBook {

    private static DeviceBook instance;
    private static CardsAdapter adapter;
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

    public static void set_adapter(CardsAdapter cardsAdapter){
        adapter = cardsAdapter;
        instance = new DeviceBook();
    }

    public boolean add_device(String name, String ip, int port){
        boolean res = false;
        try{
            Device d = new Device(name, ip, port);
            this.devices.add(d);
            adapter.clear();
            adapter.addAll(this.devices);
            res = true;
        }catch (Exception e){
            Log.e("DeviceBook::add", Arrays.toString(e.getStackTrace()));
        }
        return res;
    }

    public void delete_device(Device device){
        this.devices.remove(device);
        device.delete();
        adapter.clear();
        adapter.addAll(this.devices);
    }

    public void update_status_foreground(){
        for(Device device : this.devices){
            device.check_status_foreground();
        }
    }

    public void update_adapter(){
        adapter.clear();
        adapter.addAll(this.devices);
    }

    public List<Device> getDevices(){
        return this.devices;
    }
}
