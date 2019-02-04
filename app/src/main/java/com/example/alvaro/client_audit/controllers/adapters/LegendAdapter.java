package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.LegendItem;

import java.util.Map;

public class LegendAdapter extends ArrayAdapter<LegendItem> {

    public LegendAdapter(Context context){
        super(context, R.layout.legend_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LegendView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.legend_item, parent, false);
            holder = new LegendView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LegendView) convertView.getTag();
        }

        LegendItem argument = getItem(position);

        holder.index.setText((String.valueOf(argument.getIndex()) + ":"));
        holder.port.setText(("Port-> " + String.valueOf(argument.getPort())));

        return convertView;
    }

    static class LegendView {
        TextView index;
        TextView port;

        LegendView(View view) {
            index = (TextView) view.findViewById(R.id.legend_index);
            port = (TextView) view.findViewById(R.id.legend_port);
        }
    }
}
