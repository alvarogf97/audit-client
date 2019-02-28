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
import com.example.alvaro.client_audit.core.entities.InfectedProcess;


public class InfectedProcessAdapter extends ArrayAdapter<InfectedProcess> {

    public InfectedProcessAdapter(Context context){
        super(context, R.layout.infected_process_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfectedProcessView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.infected_process_item, parent, false);
            holder = new InfectedProcessView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (InfectedProcessView) convertView.getTag();
        }

        InfectedProcess infected = getItem(position);

        holder.name.setText(infected.getName());
        holder.pid.setText(infected.getPid());
        holder.pid.setText(infected.getLocation());
        ArrayAdapter<String> rules_adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1);
        rules_adapter.addAll(infected.getRules());
        holder.rules.setAdapter(rules_adapter);

        return convertView;
    }

    static class InfectedProcessView {
        TextView name;
        TextView pid;
        TextView location;
        ListView rules;

        InfectedProcessView(View view) {
            name = (TextView) view.findViewById(R.id.process_infected_name);
            pid = (TextView) view.findViewById(R.id.process_infected_pid);
            location = (TextView) view.findViewById(R.id.process_infected_location);
            rules = (ListView) view.findViewById(R.id.process_infected_rules);
        }
    }
}
