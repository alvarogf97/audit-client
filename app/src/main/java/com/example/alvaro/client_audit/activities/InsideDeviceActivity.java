package com.example.alvaro.client_audit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.adapters.MenuAdapter;
import com.example.alvaro.client_audit.core.networks.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsideDeviceActivity extends AppCompatActivity {

    private ListView menu;
    private List<String> menu_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_device);

        Intent intent = this.getIntent();
        String cwd = intent.getExtras().getString("cwd");

        this.menu_items = new ArrayList<>(Arrays.asList(
                getString(R.string.Hardware),
                getString(R.string.Ports),
                getString(R.string.Processes),
                getString(R.string.Network),
                getString(R.string.Upnp),
                getString(R.string.Firewall),
                getString(R.string.Vulnerabilities),
                getString(R.string.Audit)
        ));

        this.menu = (ListView) findViewById(R.id.menu_list);
        MenuAdapter menuAdapter = new MenuAdapter(this);
        this.menu.setAdapter(menuAdapter);
        menuAdapter.addAll(this.menu_items);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy connection","connection close");
        Connection.get_connection().close();
    }
}
