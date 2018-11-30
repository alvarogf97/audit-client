package com.example.alvaro.client_audit.controllers.listeners.editDeviceActivityListeners;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.EditDeviceActivity;
import com.example.alvaro.client_audit.core.utils.Validator;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

import java.util.Arrays;

public class SaveButtonListener implements View.OnClickListener {

    private class UpdateInBackground extends AsyncTask<Object,Void,Object []> {

        @Override
        protected Object [] doInBackground(Object... objects) {
            String name = (String) objects[0];
            String ip = (String) objects[1];
            int port = (Integer) objects [2];
            Device device = DeviceBook.get_instance().get_selected_device();
            boolean res = false;
            try {
                device.set_name(name);
                device.set_ip(ip);
                device.set_port(port);
                device.check_status_foreground();
                res = true;
            } catch(Exception e){
                Log.e("Update",Arrays.toString(e.getStackTrace()));
            }
            Object [] result = {objects[3],res};
            return result;
        }

        @Override
        protected void onPostExecute(Object[] objects) {
            boolean res = (boolean) objects[1];
            EditDeviceActivity activity = (EditDeviceActivity) objects[0];
            activity.stop_animation(res);
        }
    }

    private Activity activity;

    public SaveButtonListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        try {
            ((EditDeviceActivity) this.activity).start_animation();
            String device_name = ((TextView) this.activity.findViewById(R.id.device_edit_name)).getText().toString();
            String device_ip = ((TextView) this.activity.findViewById(R.id.device_edit_ip)).getText().toString();
            int device_port = Integer.valueOf(((TextView) this.activity.findViewById(R.id.device_edit_port)).getText().toString());
            if(Validator.validate(device_name,device_ip,device_port)){
                new UpdateInBackground().execute(device_name, device_ip, device_port, this.activity);
            }else{
                ((EditDeviceActivity) this.activity).stop_animation(false);
            }
        }catch(Exception e){
            Log.e("Edit", Arrays.toString(e.getStackTrace()));
            ((EditDeviceActivity) this.activity).stop_animation(false);
        }
    }
}
