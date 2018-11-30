package com.example.alvaro.client_audit.controllers.listeners.homeActivityListeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.activities.DeviceHomeActivity;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;

public class DeviceListItemClickListener implements AdapterView.OnItemClickListener {

    private Context context;

    public DeviceListItemClickListener(Context context){
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Device selected_device = (Device) parent.getItemAtPosition(position);
        Log.e("ItemClick",selected_device.get_name());
        DeviceBook.get_instance().set_selected_device(selected_device);
        Intent intent = new Intent(this.context,DeviceHomeActivity.class);
        this.context.startActivity(intent);
    }

}
