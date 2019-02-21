package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.actionActivities.NetworkAnomaliesActivity;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.AddExceptionClickListener;
import com.example.alvaro.client_audit.core.entities.NetworkMeasure;

public class AnomalyAdapter extends ArrayAdapter<NetworkMeasure> {

    private NetworkAnomaliesActivity activity;

    public AnomalyAdapter(Context context, NetworkAnomaliesActivity activity) {
        super(context, R.layout.measure_item);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnomalyView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.measure_item, parent, false);
            holder = new AnomalyView(convertView);

            convertView.setTag(holder);
        } else {
            holder = (AnomalyView) convertView.getTag();
        }

        NetworkMeasure measure = getItem(position);

        holder.port.setText(String.valueOf(measure.getPort()));
        holder.size.setText(String.valueOf(measure.getSize()));
        holder.hour.setText(measure.getHour());
        holder.process.setText(measure.getProcess_name());
        holder.button.setOnClickListener(new AddExceptionClickListener(this.activity, measure));

        return convertView;
    }

    static class AnomalyView {
        TextView port;
        TextView size;
        TextView hour;
        TextView process;
        Button button;

        AnomalyView(View view) {
            port = (TextView) view.findViewById(R.id.measure_port_text);
            size = (TextView) view.findViewById(R.id.measure_size_text);
            hour = (TextView) view.findViewById(R.id.measure_hour_text);
            process = (TextView) view.findViewById(R.id.measure_process_text);
            button = (Button) view.findViewById(R.id.add_exception_button);
        }
    }

}
