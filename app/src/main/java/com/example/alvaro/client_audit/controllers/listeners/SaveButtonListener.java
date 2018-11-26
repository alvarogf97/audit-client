package com.example.alvaro.client_audit.controllers.listeners;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.Validator;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

import java.util.Arrays;

public class SaveButtonListener implements View.OnClickListener {

    private Activity activity;

    public SaveButtonListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        try {
            String device_name = ((TextView) this.activity.findViewById(R.id.device_edit_name)).getText().toString();
            String device_ip = ((TextView) this.activity.findViewById(R.id.device_edit_ip)).getText().toString();
            int device_port = Integer.valueOf(((TextView) this.activity.findViewById(R.id.device_edit_port)).getText().toString());
            if(Validator.validate(device_name,device_ip,device_port)){
                Device device = DeviceBook.get_instance().get_selected_device();
                device.set_name(device_name);
                device.set_ip(device_ip);
                device.set_port(device_port);
                device.check_status();
                DeviceBook.get_instance().update_adapter();
                this.activity.finish();
            }else{
                this.make_toast();
            }
        }catch(Exception e){
            Log.e("Edit", Arrays.toString(e.getStackTrace()));
            this.make_toast();
        }
    }

    private void make_toast(){
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), "Cannot update device", Toast.LENGTH_SHORT);
        toast.show();
    }
}
