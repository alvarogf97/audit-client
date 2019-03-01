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
import com.example.alvaro.client_audit.core.entities.Document;

public class DocumentAdapter extends ArrayAdapter<Document> {

    public DocumentAdapter(Context context) {
        super(context, R.layout.document_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DocumentView holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.document_item, parent, false);
            holder = new DocumentView(convertView);

            convertView.setTag(holder);
        } else {
            holder = (DocumentView) convertView.getTag();
        }

        Document document = getItem(position);

        holder.name.setText(document.getName());
        if(document.isIs_file()){
            holder.image.setImageResource(R.drawable.ic_file);
        }else{
            holder.image.setImageResource(R.drawable.ic_folder);
        }

        return convertView;
    }

    static class DocumentView {
        TextView name;
        ImageView image;

        DocumentView(View view) {
            name = (TextView) view.findViewById(R.id.document_name);
            image = (ImageView) view.findViewById(R.id.document_image);
        }
    }

}
