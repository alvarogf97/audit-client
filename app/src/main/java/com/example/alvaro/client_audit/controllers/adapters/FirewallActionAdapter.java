package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.FirewallAction;

public class FirewallActionAdapter extends ArrayAdapter<FirewallAction> {

    public FirewallActionAdapter(Context context) {
        super(context, R.layout.action_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirewallActionView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.action_item, parent, false);
            holder = new FirewallActionView(convertView);

            convertView.setTag(holder);
        } else {
            holder = (FirewallActionView) convertView.getTag();
        }

        FirewallAction action = getItem(position);

        holder.name.setText(action.getName());

        return convertView;
    }

    static class FirewallActionView {
        TextView name;

        FirewallActionView(View view) {
            name = (TextView) view.findViewById(R.id.fire_ac_name);
        }
    }

}
