package com.example.alvaro.client_audit.controllers.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.unnamed.b.atv.model.TreeNode;

public class HardwareTreeViewAdapter extends TreeNode.BaseNodeViewHolder<HardwareTreeViewAdapter.HardwareNodeItem> {

    public HardwareTreeViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, HardwareNodeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.hardware_node, null, false);
        TextView node_name = (TextView) view.findViewById(R.id.node_name);
        TextView node_value = (TextView) view.findViewById(R.id.node_value);
        ImageView node_image = (ImageView) view.findViewById(R.id.node_image);
        RelativeLayout node_card = (RelativeLayout) view.findViewById(R.id.node_card);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) node_card.getLayoutParams();
        layoutParams.setMarginStart(dpToPx(5+(value.level*20)));
        node_card.requestLayout();

        node_name.setText(value.name);
        node_value.setText(value.value);
        node_image.setImageResource(value.image);
        return view;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static class HardwareNodeItem{

        public int image;
        public int level;
        public String name;
        public String value;

        public HardwareNodeItem(String name, String value, int image, int level){
            this.name = name;
            this.value = value;
            this.image = image;
            this.level = level;
        }

    }
}
