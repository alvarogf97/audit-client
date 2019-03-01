package com.example.alvaro.client_audit.controllers.listeners.FileSearchActivityListeners;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.activities.actionActivities.yaraActivities.FileSearchActivity;
import com.example.alvaro.client_audit.core.entities.Document;

public class OnDocumentLongClickListener implements AdapterView.OnItemLongClickListener {

    private FileSearchActivity activity;

    public OnDocumentLongClickListener(FileSearchActivity activity){
        this.activity = activity;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Document document = (Document) parent.getItemAtPosition(position);
        this.activity.launch_scanner(document);
        return true;
    }
}
