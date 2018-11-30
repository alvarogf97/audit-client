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

public class MenuAdapter extends ArrayAdapter<String> {

    private int [] images = {
            R.drawable.bc_1,
            R.drawable.bc_2,
            R.drawable.bc_3,
            R.drawable.bc_4,
            R.drawable.bc_5,
            R.drawable.bc_6,
            R.drawable.bc_7,
            R.drawable.bc_8,
            R.drawable.bc_9,
            R.drawable.bc_10,
            R.drawable.bc_11,
            R.drawable.bc_12
    };

    public MenuAdapter(Context context) {
        super(context, R.layout.menu_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MenuAdapter.MenuHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.menu_item, parent, false);
            holder = new MenuAdapter.MenuHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (MenuAdapter.MenuHolder) convertView.getTag();
        }

        String menu_name = getItem(position);
        int n = (int) (Math.random() * this.images.length);

        holder.name.setText(menu_name);
        holder.image.setImageResource(this.images[n]);

        return convertView;
    }

    static class MenuHolder {
        ImageView image;
        TextView name;

        MenuHolder(View view) {
            image = (ImageView) view.findViewById(R.id.menu_item_image);
            name = (TextView) view.findViewById(R.id.menu_item_name);
        }
    }

}
