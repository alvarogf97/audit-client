package com.example.alvaro.client_audit.controllers.listeners.insideDeviceActivityListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.activities.actionActivities.HardwareActivity;
import com.example.alvaro.client_audit.activities.actionActivities.PortsActivity;
import com.example.alvaro.client_audit.activities.actionActivities.ProcessesActivity;
import com.example.alvaro.client_audit.activities.actionActivities.UpnpActivity;
import com.example.alvaro.client_audit.activities.actionActivities.VulnersActivity;

public class ClickItemListListener implements AdapterView.OnItemClickListener {

    private Activity activity;

    public ClickItemListListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected_menu_option = (String) parent.getItemAtPosition(position);

        if(this.activity.getString(R.string.Hardware).equals(selected_menu_option)){
            Intent intent = new Intent(this.activity.getApplicationContext(),HardwareActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(intent);
        }else if(this.activity.getString(R.string.Ports).equals(selected_menu_option)){
            Intent intent = new Intent(this.activity.getApplicationContext(),PortsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(intent);
        }else if(this.activity.getString(R.string.Processes).equals(selected_menu_option)){
            Intent intent = new Intent(this.activity.getApplicationContext(),ProcessesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(intent);
        }else if(this.activity.getString(R.string.Network).equals(selected_menu_option)){
            this.make_toast();
        }else if(this.activity.getString(R.string.Upnp).equals(selected_menu_option)){
            Intent intent = new Intent(this.activity.getApplicationContext(),UpnpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(intent);
        }else if(this.activity.getString(R.string.Firewall).equals(selected_menu_option)){
            Intent intent = new Intent(this.activity.getApplicationContext(),FirewallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(intent);
        }else if(this.activity.getString(R.string.Vulnerabilities).equals(selected_menu_option)){
            Intent intent = new Intent(this.activity.getApplicationContext(),VulnersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.activity.startActivity(intent);
        }else if(this.activity.getString(R.string.Audit).equals(selected_menu_option)){
            this.make_toast();
        }else{
            this.make_toast();
        }
    }

    private void make_toast(){
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT);
        toast.show();
    }

}
