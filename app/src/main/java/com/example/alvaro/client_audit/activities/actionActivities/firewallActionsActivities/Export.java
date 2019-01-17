package com.example.alvaro.client_audit.activities.actionActivities.firewallActionsActivities;

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
import com.example.alvaro.client_audit.activities.DialogActivity;
import com.example.alvaro.client_audit.activities.actionActivities.FirewallActivity;
import com.example.alvaro.client_audit.controllers.adapters.ArgumentsAdapter;
import com.example.alvaro.client_audit.controllers.listeners.dialogActivityListeners.ButtonCancelDialogListener;
import com.example.alvaro.client_audit.controllers.listeners.dialogActivityListeners.ButtonSaveDialogListener;
import com.example.alvaro.client_audit.controllers.listeners.dialogActivityListeners.onArgumentClickListener;
import com.example.alvaro.client_audit.controllers.listeners.firewallActivityListeners.ExportButtonClickListener;
import com.example.alvaro.client_audit.core.entities.Argument;
import com.example.alvaro.client_audit.core.entities.FirewallAction;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.example.alvaro.client_audit.core.utils.JsonParsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Export extends AsyncTaskActivity implements DialogActivity {

    private FirewallAction export_action;
    private AlertDialog dialog;
    private List<Argument> argument_list;
    private ArgumentsAdapter adapter;
    private TextView dialog_arg_name;
    private EditText dialog_arg_value;
    private Argument selected_Argument;
    private ListView argument_list_view;
    private Button execute_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        this.export_action = FirewallActivity.getActionByName("export settings");
        this.argument_list = JsonParsers.parse_JSON_firewall_arguments(this.export_action.getArgs());
        this.argument_list_view = (ListView) findViewById(R.id.export_firewall_arguments_list);
        this.execute_button = (Button) findViewById(R.id.button_export_firewall);
        this.adapter = new ArgumentsAdapter(this.getApplicationContext());
        this.adapter.addAll(this.argument_list);
        this.argument_list_view.setAdapter(adapter);
        this.argument_list_view.setOnItemClickListener(new onArgumentClickListener(this));
        this.execute_button.setOnClickListener(new ExportButtonClickListener(this));
        this.create_dialog();
    }

    @Override
    public void start_animation() {

    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            Toast toast;
            if(response.getBoolean("status")){
                toast = Toast.makeText(this.getApplicationContext(), "Firewall exported successfully", Toast.LENGTH_SHORT);
                toast.show();
                this.finish();
            }else{
                toast = Toast.makeText(this.getApplicationContext(), "You do not have privileges", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void create_dialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(Export.this);
        View promptView = layoutInflater.inflate(R.layout.argument_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Export.this);
        alertDialogBuilder.setView(promptView);

        dialog_arg_name = (TextView) promptView.findViewById(R.id.argument_dialog_name);
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

    public void showDialog(){
        dialog_arg_name.setText(selected_Argument.getName());
        dialog_arg_value.setText(selected_Argument.getValue());
        dialog.show();
    }

    public Argument getSelected_Argument(){
        return selected_Argument;
    }

    public void setSelected_Argument(Argument argument){
        this.selected_Argument = argument;
    }

    public void execute_action(){
        JSONObject query = new JSONObject();
        try {
            query.put("command",this.export_action.getCommand());
            query.put("args",JsonParsers.parse_firewall_arguments(this.argument_list));
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("status", Arrays.toString(e.getStackTrace()));
        }
    }


}
