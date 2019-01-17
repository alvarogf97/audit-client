package com.example.alvaro.client_audit.controllers.listeners.dialogActivityListeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.example.alvaro.client_audit.activities.DialogActivity;
import com.example.alvaro.client_audit.core.entities.Argument;

public class onArgumentClickListener implements AdapterView.OnItemClickListener {

    private DialogActivity activity;

    public onArgumentClickListener(DialogActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Argument selected_argument = (Argument) parent.getItemAtPosition(position);
        this.activity.setSelected_Argument(selected_argument);
        Log.e("selectArgument",selected_argument.getName());
        this.activity.showDialog();
    }

}
