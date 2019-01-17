package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.StatusFirewall;

public class StatusFirewallItemAdapter extends ArrayAdapter<StatusFirewall> {

    public StatusFirewallItemAdapter(Context context) {
        super(context, R.layout.status_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatusFirewallView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.status_item, parent, false);
            holder = new StatusFirewallView(convertView);

            convertView.setTag(holder);
        } else {
            holder = (StatusFirewallView) convertView.getTag();
        }

        StatusFirewall status_item = getItem(position);

        holder.name.setText(status_item.getName());
        if(status_item.is_active()){
            holder.image.setImageResource(R.drawable.ic_on);
        }else{
            holder.image.setImageResource(R.drawable.ic_off);
        }

        return convertView;
    }

    static class StatusFirewallView {

        TextView name;
        ImageView image;

        StatusFirewallView(View view) {
            name = (TextView) view.findViewById(R.id.st_name);
            image = (ImageView) view.findViewById(R.id.st_img);
        }
    }

}
