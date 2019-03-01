package com.example.alvaro.client_audit.controllers.listeners.FileSearchActivityListeners;

import android.view.View;
import android.widget.AdapterView;

import com.example.alvaro.client_audit.activities.actionActivities.yaraActivities.FileSearchActivity;
import com.example.alvaro.client_audit.core.entities.Document;

public class OnDocumentClickListener implements AdapterView.OnItemClickListener {

    private FileSearchActivity activity;

    public OnDocumentClickListener(FileSearchActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Document document = (Document) parent.getItemAtPosition(position);
        if(!document.isIs_file()){
            this.activity.get_folder_content(document.getAbs_path());
        }
    }
}
