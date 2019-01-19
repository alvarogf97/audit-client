package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.unnamed.b.atv.model.TreeNode;

public class NodeTreeViewAdapter extends TreeNode.BaseNodeViewHolder<NodeTreeViewAdapter.NodeItem> {

    public NodeTreeViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, NodeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.hardware_node, null, false);
        TextView node_name = (TextView) view.findViewById(R.id.node_name);
        TextView node_value = (TextView) view.findViewById(R.id.node_value);
        ImageView node_image = (ImageView) view.findViewById(R.id.node_image);
        RelativeLayout node_card = (RelativeLayout) view.findViewById(R.id.node_card);
        Button erasable_button = (Button) view.findViewById(R.id.erasable_button);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) node_card.getLayoutParams();
        layoutParams.setMarginStart(dpToPx(5+(value.level*20)));
        node_card.requestLayout();

        node_name.setText(value.name);
        node_value.setText(value.value);
        node_image.setImageResource(value.image);
        if(value.is_erasable){
            erasable_button.setVisibility(View.VISIBLE);
            erasable_button.setOnClickListener(value.listener);
        }
        return view;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static class NodeItem {

        public int image;
        public int level;
        public String name;
        public String value;
        public boolean is_erasable;
        public View.OnClickListener listener;

        public NodeItem(String name, String value, int image, int level){
            this.name = name;
            this.value = value;
            this.image = image;
            this.level = level;
            this.is_erasable = false;
        }

        public NodeItem(String name, String value, int image, int level, boolean is_erasable, View.OnClickListener listener){
            this.name = name;
            this.value = value;
            this.image = image;
            this.level = level;
            this.is_erasable = is_erasable;
            this.listener = listener;
        }

    }
}
