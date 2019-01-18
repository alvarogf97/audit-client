package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.File;

public class FileAdapter extends ArrayAdapter<File> {

    public FileAdapter(Context context) {
        super(context, R.layout.file_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.file_item, parent, false);
            holder = new FileView(convertView);

            convertView.setTag(holder);
        } else {
            holder = (FileView) convertView.getTag();
        }

        File file = getItem(position);

        holder.name.setText(file.getName());

        return convertView;
    }

    static class FileView {
        TextView name;

        FileView(View view) {
            name = (TextView) view.findViewById(R.id.file_fw_name);
        }
    }
}
