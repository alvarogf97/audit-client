package com.example.alvaro.client_audit.controllers.listeners;

import android.os.AsyncTask;
import android.view.View;

import com.example.alvaro.client_audit.activities.DeviceHomeActivity;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

public class UpdateSingleDeviceButtonListener implements View.OnClickListener {

    private DeviceHomeActivity activity;

    public UpdateSingleDeviceButtonListener(DeviceHomeActivity activity){
        this.activity = activity;
    }

    private class UpdateInBack extends AsyncTask<DeviceHomeActivity,Void,DeviceHomeActivity> {
        @Override
        protected DeviceHomeActivity doInBackground(DeviceHomeActivity... deviceHomeActivities) {
            DeviceBook.get_instance().get_selected_device().check_status_foreground();
            return deviceHomeActivities[0];
        }

        @Override
        protected void onPostExecute(DeviceHomeActivity activity) {
            activity.stop_animation();
        }
    }

    @Override
    public void onClick(View v) {
        activity.start_animation();
        new UpdateInBack().execute(this.activity);
    }

}
