package com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener;

import android.view.View;
import android.widget.EditText;

import com.example.alvaro.client_audit.activities.actionActivities.UpnpActionActivity;

public class ButtonSaveDialogListener implements View.OnClickListener {

    private UpnpActionActivity activity;
    private EditText editText;

    public ButtonSaveDialogListener(UpnpActionActivity activity, EditText editText){
        this.activity = activity;
        this.editText = editText;
    }

    @Override
    public void onClick(View v) {
        this.activity.getSelected_Argument().setValue(editText.getText().toString());
        this.activity.hide_dialog();
    }
}
