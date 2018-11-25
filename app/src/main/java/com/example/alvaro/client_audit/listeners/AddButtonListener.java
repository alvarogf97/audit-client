package com.example.alvaro.client_audit.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.example.alvaro.client_audit.activities.AddDeviceActivity;

public class AddButtonListener implements View.OnClickListener {

    private Context context;

    public AddButtonListener(Context context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.context,AddDeviceActivity.class);
        this.context.startActivity(intent);
    }

}
