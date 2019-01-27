package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.entities.Chain;

public class FirewallChainAdapter extends ArrayAdapter<Chain> {

    public FirewallChainAdapter(Context context) {
        super(context, R.layout.chain_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChainView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.chain_item, parent, false);
            holder = new ChainView(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ChainView) convertView.getTag();
        }

        Chain chain = getItem(position);

        holder.chain_name.setText(chain.getName());
        holder.chain_policy.setText(chain.getPolicy());
        holder.imageView.setImageResource(R.drawable.ic_chain);
        if(!chain.isRemovable()){
            holder.delete_chain_button.setVisibility(View.GONE);
        }else{
            holder.delete_chain_button.setOnClickListener(chain.getDelete_listener());
        }
        holder.flush_chain_button.setOnClickListener(chain.getFlush_listener());
        holder.change_policy_button.setOnClickListener(chain.getChange_policy_listener());

        return convertView;
    }

    static class ChainView {
        ImageView imageView;
        TextView chain_name;
        TextView chain_policy;
        Button change_policy_button;
        Button delete_chain_button;
        Button flush_chain_button;

        ChainView(View view) {
            imageView = (ImageView) view.findViewById(R.id.image_chain);
            chain_name = (TextView) view.findViewById(R.id.chain_name);
            chain_policy = (TextView) view.findViewById(R.id.chain_policy);
            change_policy_button = (Button) view.findViewById(R.id.button_change_policy);
            delete_chain_button = (Button) view.findViewById(R.id.button_chain_delete);
            flush_chain_button = (Button) view.findViewById(R.id.button_flush);
        }
    }
}
