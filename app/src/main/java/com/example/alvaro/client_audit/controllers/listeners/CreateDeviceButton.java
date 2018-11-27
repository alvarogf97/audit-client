package com.example.alvaro.client_audit.controllers.listeners;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.activities.AddDeviceActivity;
import com.example.alvaro.client_audit.core.entities.DeviceBook;
import java.util.Arrays;

public class CreateDeviceButton implements View.OnClickListener {

    private class CreateInBackground extends AsyncTask<Object,Void,Object []>{

        @Override
        protected Object [] doInBackground(Object... objects) {
            String name = (String) objects[0];
            String ip = (String) objects[1];
            int port = (Integer) objects [2];
            Boolean res = DeviceBook.get_instance().add_device(name,ip,port);
            Object [] result = {objects[3],res};
            return result;
        }

        @Override
        protected void onPostExecute(Object[] objects) {
            boolean res = (boolean) objects[1];
            AddDeviceActivity activity = (AddDeviceActivity) objects[0];
            activity.stop_animation(res);
        }
    }

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
            ((AddDeviceActivity) this.activity).start_animation();
            String d_name = this.device_name.getText().toString();
            String d_ip = this.device_ip.getText().toString();
            int d_port = Integer.parseInt(this.device_port.getText().toString());
            new CreateInBackground().execute(d_name, d_ip, d_port, this.activity);
        }catch(Exception e){
            Log.e("CreateDevice::onClick", Arrays.toString(e.getStackTrace()));
            ((AddDeviceActivity) this.activity).stop_animation(false);
        }

    }


}
