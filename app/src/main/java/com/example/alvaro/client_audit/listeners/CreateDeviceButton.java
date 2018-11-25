package com.example.alvaro.client_audit.listeners;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.alvaro.client_audit.core.entities.DeviceBook;
import java.util.Arrays;

public class CreateDeviceButton implements View.OnClickListener {

    private Activity activity;
    private TextView device_name;
    private TextView device_ip;
    private TextView device_port;

    public CreateDeviceButton(Activity activity, TextView device_name, TextView device_ip, TextView device_port){
        this.activity = activity;
        this.device_name = device_name;
        this.device_ip = device_ip;
        this.device_port = device_port;
    }

    @Override
    public void onClick(View v) {
        try {
            v.setEnabled(false);
            String d_name = this.device_name.getText().toString();
            String d_ip = this.device_ip.getText().toString();
            int d_port = Integer.parseInt(this.device_port.getText().toString());
            if(DeviceBook.get_instance().add_device(d_name, d_ip, d_port)){
                v.setEnabled(true);
                this.activity.finish();
            }else{
                this.make_toast();
                v.setEnabled(true);
            }
        }catch(Exception e){
            Log.e("CreateDevice::onClick", Arrays.toString(e.getStackTrace()));
            this.make_toast();
            v.setEnabled(true);
        }

    }

    private void make_toast(){
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), "Cannot create device", Toast.LENGTH_SHORT);
        toast.show();
    }
}
