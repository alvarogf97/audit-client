package com.example.alvaro.client_audit.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.bd.BD;
import com.example.alvaro.client_audit.core.Connection;
import com.example.alvaro.client_audit.entities.DeviceBook;
import com.example.alvaro.client_audit.listeners.AddButtonListener;
import com.example.alvaro.client_audit.views.CardsAdapter;

public class HomeActivity extends AppCompatActivity {

    ListView cards;
    FloatingActionButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Connection.set_resources_context(this.getResources());
        BD.setInstanceContext(this.getApplicationContext());

        //String cwd = Connection.get_connection().connect("192.168.1.112",5000);


        this.add_button = (FloatingActionButton) findViewById(R.id.b_add);
        this.add_button.setOnClickListener(new AddButtonListener(this.getApplicationContext()));

        cards = (ListView) findViewById(R.id.list);
        CardsAdapter cardsAdapter = new CardsAdapter(this);
        cards.setAdapter(cardsAdapter);
        DeviceBook.set_adapter(cardsAdapter);

        cardsAdapter.addAll(DeviceBook.get_instance().getDevices());

    }
}
