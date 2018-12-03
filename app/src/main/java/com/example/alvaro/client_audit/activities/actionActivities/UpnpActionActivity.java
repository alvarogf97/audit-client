package com.example.alvaro.client_audit.activities.actionActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.ArgumentsAdapter;
import com.example.alvaro.client_audit.core.entities.Argument;
import com.example.alvaro.client_audit.core.utils.JsonParsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpnpActionActivity extends AsyncTaskActivity {

    private JSONObject args;
    private JSONObject response;
    private ListView inputs;
    private ListView outputs;
    private TextView action_name;
    private List<Argument> args_in;
    private List<Argument> args_out;
    private ArgumentsAdapter adapter_in;
    private ArgumentsAdapter adapter_out;
    private String location;
    private String service;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upnp_action);

        args = JsonParsers.parse_string(this.getIntent().getExtras().getString("args"));
        action_name = (TextView) findViewById(R.id.action_name);
        inputs = (ListView) findViewById(R.id.input_list);
        outputs = (ListView) findViewById(R.id.output_list);
        adapter_in = new ArgumentsAdapter(this.getApplicationContext());
        adapter_out = new ArgumentsAdapter(this.getApplicationContext());
        this.inputs.setAdapter(adapter_in);

        this.outputs.setAdapter(adapter_out);

        try {
            action = args.getString("action");
            args_in = parseJSON_arguments(args.getJSONArray("args_in"));
            args_out = parseJSON_arguments(args.getJSONArray("args_out"));
            service = args.getString("service");
            location = args.getString("locations");

            adapter_in.addAll(args_in);
            adapter_out.addAll(args_out);
            action_name.setText(action);

        } catch (JSONException e) {
            Log.e("upnpAction", Arrays.toString(e.getStackTrace()));
        }

    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {
        response = (JSONObject) objects[0];
    }

    private void execute_query() throws JSONException {
        JSONObject query = new JSONObject();
        query.put("location",location);
        query.put("service",service);
        query.put("action",action);
        query.put("args_in",parseArguments(this.args_in));
    }

    private List<Argument> parseJSON_arguments(JSONArray arguments_data) throws JSONException {
        List<Argument> arguments = new ArrayList<>();
        for(int i = 0; i<arguments_data.length(); i++){
            JSONObject arg = arguments_data.getJSONObject(i);
            arguments.add(new Argument(arg.getString("name"),arg.getString("datatype")));
        }
        return arguments;
    }

    private JSONObject parseArguments(List<Argument> arguments_data) throws JSONException {
        JSONObject arguments = new JSONObject();
        for(Argument arg : arguments_data){
            arguments.put(arg.getName(),arg.getValue());
        }
        return arguments;
    }
}
