package com.example.alvaro.client_audit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.Connection;

public class HomeActivity extends AppCompatActivity {

    TextView h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        h = (TextView) findViewById(R.id.hello);

        Connection.set_resources_context(this.getResources());
        String cwd = Connection.get_connection().connect("192.168.1.112",5000);

        if(cwd != null){
            String ipconfig = Connection.get_connection().execute_command("ipconfig");
            Log.e("L",ipconfig);
            h.setText(ipconfig);
            Connection.get_connection().close();
        }else{
            h.setText("Could not connect to server");
        }

    }
}
