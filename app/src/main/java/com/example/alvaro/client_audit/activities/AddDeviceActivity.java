package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.listeners.CreateDeviceButton;
import com.example.alvaro.client_audit.core.entities.DeviceBook;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.w3c.dom.Text;

public class AddDeviceActivity extends AppCompatActivity {

    Button b_create;
    private TextView device_name;
    private TextView device_ip;
    private TextView device_port;
    private TextView device_name_text;
    private TextView device_ip_text;
    private TextView device_port_text;
    private SpinKitView loader;
    private ThreeBounce w = new ThreeBounce();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device_activity);

        b_create = (Button) findViewById(R.id.button_create);
        device_name = (TextView) findViewById(R.id.device_name);
        device_ip = (TextView) findViewById(R.id.device_ip);
        device_port = (TextView) findViewById(R.id.device_port);
        device_name_text = (TextView) findViewById(R.id.device_name_text);
        device_ip_text = (TextView) findViewById(R.id.device_ip_text);
        device_port_text = (TextView) findViewById(R.id.device_port_text);
        this.loader = (SpinKitView) findViewById(R.id.create_anim_load);

        b_create.setEnabled(true);
        b_create.setOnClickListener(new CreateDeviceButton(this,device_name,device_ip,device_port));
    }

    public void start_animation(){
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(w);
        b_create.setEnabled(false);
    }

    public void stop_animation(boolean res){
        this.loader.setVisibility(View.GONE);
        if(!res){
            this.make_toast();
            b_create.setEnabled(true);
        }else{
            DeviceBook.get_instance().update_adapter();
            this.finish();
        }
    }

    public void make_toast(){
        Toast toast = Toast.makeText(this.getApplicationContext(), "Cannot create device", Toast.LENGTH_SHORT);
        toast.show();
    }

}
