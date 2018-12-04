package com.example.alvaro.client_audit.activities.actionActivities;

import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.ArgumentsAdapter;
import com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener.ButtonCancelDialogListener;
import com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener.ButtonExecuteQueryListener;
import com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener.ButtonSaveDialogListener;
import com.example.alvaro.client_audit.controllers.listeners.upnpActionActivityListener.onArgumentClickListener;
import com.example.alvaro.client_audit.core.entities.Argument;
import com.example.alvaro.client_audit.core.networks.Connection;
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
    private AlertDialog dialog;
    private String location;
    private String service;
    private String action;
    private Argument selected_Argument;

    private TextView dialog_arg_name;
    private TextView dialog_arg_type;
    private EditText dialog_arg_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upnp_action);

        args = JsonParsers.parse_string(this.getIntent().getExtras().getString("args"));
        action_name = (TextView) findViewById(R.id.action_name);
        inputs = (ListView) findViewById(R.id.input_list);
        outputs = (ListView) findViewById(R.id.output_list);
        Button execute = (Button) findViewById(R.id.button_execute);
        adapter_in = new ArgumentsAdapter(this.getApplicationContext());
        adapter_out = new ArgumentsAdapter(this.getApplicationContext());
        this.create_dialog();
        this.inputs.setAdapter(adapter_in);
        this.inputs.setOnItemClickListener(new onArgumentClickListener(this));
        execute.setOnClickListener(new ButtonExecuteQueryListener(this));

        this.outputs.setAdapter(adapter_out);

        try {
            action = args.getString("action");
            args_in = parseJSON_arguments(args.getJSONArray("args_in"));
            args_out = parseJSON_arguments(args.getJSONArray("args_out"));
            service = args.getString("service");
            location = args.getString("location");

            adapter_in.addAll(args_in);
            adapter_out.addAll(args_out);
            action_name.setText(action);

        } catch (JSONException e) {
            Log.e("upnpAction", Arrays.toString(e.getStackTrace()));
        }

    }

    public void create_dialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(UpnpActionActivity.this);
        View promptView = layoutInflater.inflate(R.layout.argument_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpnpActionActivity.this);
        alertDialogBuilder.setView(promptView);

        dialog_arg_name = (TextView) promptView.findViewById(R.id.argument_dialog_name);
        dialog_arg_type = (TextView) promptView.findViewById(R.id.argument_dialog_type);
        dialog_arg_value = (EditText) promptView.findViewById(R.id.argument_dialog_value);
        Button cancel_button = (Button) promptView.findViewById(R.id.argument_dialog_cancel_button);
        Button save_button = (Button) promptView.findViewById(R.id.argument_dialog_save_button);
        save_button.setOnClickListener(new ButtonSaveDialogListener(this, dialog_arg_value));
        cancel_button.setOnClickListener(new ButtonCancelDialogListener(this));

        alertDialogBuilder.setCancelable(true);
        dialog = alertDialogBuilder.create();
    }

    public void hide_dialog(){
        dialog.hide();
    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {
        response = (JSONObject) objects[0];
        Log.d("DFDSFA",String.valueOf(objects.length));
        try {
            if(response.getBoolean("status")){
                get_otuputs_response(response.getJSONObject("data"));
                this.make_toast("invoke success");
            }else{
                this.make_toast("invoke fail");
            }
        } catch (Exception e) {
            Log.e("stopanimationUpnp",Arrays.toString(e.getStackTrace()));
        }
    }

    private void get_otuputs_response(JSONObject response) throws JSONException {
        for(Argument arg : this.args_out){
            arg.setValue(response.get(arg.getName()).toString());
        }
        adapter_out.clear();
        adapter_out.addAll(args_out);
    }

    public void execute_query() throws JSONException {
        JSONObject query = new JSONObject();
        query.put("command","upnp exec");
        JSONObject args = new JSONObject();
        args.put("location",location);
        args.put("service",service);
        args.put("action",action);
        args.put("args_in",parseArguments(this.args_in));
        query.put("args",args);
        Connection.get_connection().execute_command(query, this);
    }

    private List<Argument> parseJSON_arguments(JSONArray arguments_data) throws JSONException {
        List<Argument> arguments = new ArrayList<>();
        for(int i = 0; i<arguments_data.length(); i++){
            JSONObject arg = arguments_data.getJSONObject(i);
            arguments.add(new Argument(arg.getString("name"),arg.getString("datatype")));
        }
        return arguments;
    }

    private void make_toast(String text){
        Toast toast = Toast.makeText(this.getApplicationContext(),text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private JSONObject parseArguments(List<Argument> arguments_data) throws JSONException {
        JSONObject arguments = new JSONObject();
        for(Argument arg : arguments_data){
            arguments.put(arg.getName(),arg.getValue());
        }
        return arguments;
    }

    public void showDialog(){
        dialog_arg_name.setText(selected_Argument.getName());
        dialog_arg_type.setText(selected_Argument.getDatatype());
        dialog_arg_value.setText(selected_Argument.getValue());
        dialog.show();
    }

    public Argument getSelected_Argument(){
        return selected_Argument;
    }

    public void setSelected_Argument(Argument argument){
        this.selected_Argument = argument;
    }
}
