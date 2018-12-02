package com.example.alvaro.client_audit.controllers.listeners.vulnersActivityListener;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.alvaro.client_audit.activities.actionActivities.VulnersActivity;
import com.unnamed.b.atv.model.TreeNode;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterTextListener implements TextWatcher {

    private class GetNodes extends AsyncTask<VulnersActivity,Void,List<Object>> {

        @Override
        protected List<Object> doInBackground(VulnersActivity... vulnersActivities) {
            List<Object> result = new ArrayList<>();
            VulnersActivity activity = vulnersActivities[0];
            List<TreeNode> nodes = new ArrayList<>();
            try {
                nodes = activity.getNodes(activity.getResponse().getJSONArray("data"), activity.getFilter());
            } catch (JSONException e) {
                Log.e("nodes in back", Arrays.toString(e.getStackTrace()));
            }
            result.add(activity);
            result.add(nodes);
            return result;
        }

        @Override
        protected void onPostExecute(List<Object> objects) {
            VulnersActivity activity = (VulnersActivity) objects.get(0);
            List<TreeNode> nodes = (List<TreeNode>) objects.get(1);
            activity.setNodes(nodes);
        }
    }

    private VulnersActivity activity;

    public FilterTextListener(VulnersActivity activity){
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.activity.setFilter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        new GetNodes().execute(this.activity);
    }

}
