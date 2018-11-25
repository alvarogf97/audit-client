package com.example.alvaro.client_audit.listeners;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.alvaro.client_audit.activities.HomeActivity;
import com.example.alvaro.client_audit.entities.DeviceBook;

public class UpdateButtonListener implements View.OnClickListener {

    private class UpdateInBack extends AsyncTask<HomeActivity,Void,HomeActivity>{
            @Override
            protected HomeActivity doInBackground(HomeActivity... homeActivities) {
                DeviceBook.get_instance().update_status_foreground();
                return homeActivities[0];
            }

            @Override
            protected void onPostExecute(HomeActivity activity) {
                Log.e("back","post");
                activity.stop_animation();
            }
    }

    private HomeActivity activity;

    public UpdateButtonListener(HomeActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        this.activity.start_animation();
        new UpdateInBack().execute(this.activity);
    }

}
