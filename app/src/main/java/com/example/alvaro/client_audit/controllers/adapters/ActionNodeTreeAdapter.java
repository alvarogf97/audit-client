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
import org.json.JSONArray;
import org.json.JSONException;

public class ActionNodeTreeAdapter extends TreeNode.BaseNodeViewHolder<ActionNodeTreeAdapter.Action> {

    public ActionNodeTreeAdapter(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, ActionNodeTreeAdapter.Action value) {
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
        node_image.setImageResource(R.drawable.ic_hardware_default);
        return view;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static class Action{

        public String name;
        public JSONArray args_in;
        public JSONArray args_out;
        public String url;
        public int level;

        public Action(String name, JSONArray args_in, JSONArray args_out, String url, int level) throws JSONException {
            this.name = name;
            this.args_in = args_in;
            this.args_out = args_out;
            this.url = url;
            this.level = level;
        }
    }

}
