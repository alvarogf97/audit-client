package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.InfectedFile;

public class InfectedFileAdapter extends ArrayAdapter<InfectedFile> {

    public InfectedFileAdapter(Context context){
        super(context, R.layout.infected_file_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfectedFileView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.infected_file_item, parent, false);
            holder = new InfectedFileView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (InfectedFileView) convertView.getTag();
        }

        InfectedFile infected = getItem(position);

        holder.filename.setText(infected.getFilename());
        holder.path.setText(infected.getFile_route());
        holder.size.setText(infected.getSize());
        ArrayAdapter<String> rules_adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);
        rules_adapter.addAll(infected.getRules());
        holder.rules.setAdapter(rules_adapter);

        return convertView;
    }

    static class InfectedFileView {
        TextView filename;
        TextView path;
        TextView size;
        ListView rules;

        InfectedFileView(View view) {
            filename = (TextView) view.findViewById(R.id.file_infected_name);
            path = (TextView) view.findViewById(R.id.file_infected_route);
            size = (TextView) view.findViewById(R.id.file_infected_size);
            rules = (ListView) view.findViewById(R.id.file_infected_rules);
        }
    }

}
