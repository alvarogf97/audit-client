package com.example.alvaro.client_audit.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners.ConnectButtonListener;
import com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners.DeleteDeviceButtonListener;
import com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners.EditButtonListener;
import com.example.alvaro.client_audit.controllers.listeners.deviceHomeActivityListeners.UpdateSingleDeviceButtonListener;
import com.example.alvaro.client_audit.core.entities.Device;
import com.example.alvaro.client_audit.core.entities.DeviceBook;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class DeviceHomeActivity extends AppCompatActivity {

    private FloatingActionButton delete_button;
    private TextView device_name;
    private TextView device_ip;
    private TextView device_port;
    private TextView device_status;
    private Button b_connect;
    private FloatingActionButton edit_button;
    private FloatingActionButton update_button;
    private Device device;
    private SpinKitView loader;
    private ThreeBounce w = new ThreeBounce();

    /*
        On create
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_home);

        this.delete_button = (FloatingActionButton) findViewById(R.id.delete_button);
        this.edit_button = (FloatingActionButton) findViewById(R.id.button_edit);
        this.b_connect = (Button) findViewById(R.id.button_connect);
        this.device_name = (TextView) findViewById(R.id.device_device_name);
        this.device_ip = (TextView) findViewById(R.id.device_device_ip);
        this.device_port = (TextView) findViewById(R.id.device_device_port);
        this.device_status = (TextView) findViewById(R.id.device_device_status);
        this.update_button = (FloatingActionButton) findViewById(R.id.button_update);
        this.loader = (SpinKitView) findViewById(R.id.u_anim_load);

        this.delete_button.setOnClickListener(new DeleteDeviceButtonListener(this));
        this.edit_button.setOnClickListener(new EditButtonListener(this.getApplicationContext()));
        this.b_connect.setOnClickListener(new ConnectButtonListener(this));
        this.update_button.setOnClickListener(new UpdateSingleDeviceButtonListener(this));

        this.device = DeviceBook.get_instance().get_selected_device();

        this.update_device_info();

    }


    /*
        change view to show device info
     */
    public void update_device_info(){
        this.device_name.setText(this.device.get_name());
        this.device_ip.setText(this.device.get_ip());
        this.device_port.setText(String.valueOf(this.device.get_port()));

        if(this.device.get_status()){
            this.device_status.setText("Online");
        }else{
            this.device_status.setText("Offline");
        }

        if(!this.device.get_status()){
            this.b_connect.setVisibility(View.GONE);
        }else{
            this.b_connect.setVisibility(View.VISIBLE);
        }
    }

    /*
        On resume
     */
    public void onResume(){
        super.onResume();
        this.update_device_info();
    }

    /*
        start animation while asyncTask
    */
    public void start_animation(){
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(w);
        b_connect.setVisibility(View.GONE);
    }

    /*
        called by asyncTask when it terminates
     */
    public void stop_animation(){
        DeviceBook.get_instance().update_adapter();
        b_connect.setVisibility(View.VISIBLE);
        this.update_device_info();
        this.loader.setVisibility(View.GONE);
    }
}
