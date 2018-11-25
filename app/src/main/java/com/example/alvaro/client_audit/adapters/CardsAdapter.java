package com.example.alvaro.client_audit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.Device;


public class CardsAdapter extends ArrayAdapter<Device> {

    public CardsAdapter(Context context) {
        super(context, R.layout.card_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.card_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Device device = getItem(position);

        if(device.get_status()){
            holder.imageView.setImageResource(R.drawable.online);
        }else{
            holder.imageView.setImageResource(R.drawable.offline);
        }

        holder.tvName.setText(device.get_name());
        holder.tvIp.setText(device.get_ip());
        holder.tvPort.setText(String.valueOf(device.get_port()));

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvIp;
        TextView tvPort;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.image);
            tvName = (TextView) view.findViewById(R.id.text_name);
            tvIp = (TextView) view.findViewById(R.id.text_ip);
            tvPort = (TextView) view.findViewById(R.id.text_port);
        }
    }

}