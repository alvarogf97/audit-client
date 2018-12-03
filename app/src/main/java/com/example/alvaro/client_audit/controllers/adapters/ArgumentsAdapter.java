package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.Argument;

public class ArgumentsAdapter extends ArrayAdapter<Argument> {

    public ArgumentsAdapter(Context context) {
        super(context, R.layout.card_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.argument_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Argument argument = getItem(position);

        holder.name.setText(argument.getName());
        holder.value.setText(argument.getValue());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView value;

        ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.argument_card_name);
            value = (TextView) view.findViewById(R.id.argument_card_value);
        }
    }
}
