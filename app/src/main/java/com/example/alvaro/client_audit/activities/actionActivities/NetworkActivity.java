package com.example.alvaro.client_audit.activities.actionActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.GraphTypeOnClickListener;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.OpenDialogClickListener;
import com.example.alvaro.client_audit.core.entities.NetworkMeasure;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class NetworkActivity extends AsyncTaskActivity {

    private String[] graph_options_string ;

    private boolean is_analysing;
    private boolean is_adding_exception;
    private GraphView graph;
    private ListView anomalies_list;
    private Button change_button;
    private SpinKitView loader;
    private TextView info;
    private TextView n_t_1;
    private TextView n_t_2;
    private ListView graph_options;
    private AlertDialog dialog;

    private List<NetworkMeasure> input_measures;
    private List<NetworkMeasure> output_measures;
    private List<NetworkMeasure> abnormal_measures;

    private LineGraphSeries<DataPoint> input_size_time_graph;
    private LineGraphSeries<DataPoint> output_size_time_graph;
    private BarGraphSeries<DataPoint> input_size_port_graph;
    private BarGraphSeries<DataPoint> output_size_port_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        this.graph_options_string = new String[4];
        this.graph_options_string[0] = this.getString(R.string.graph_input_size_time);
        this.graph_options_string[1] = this.getString(R.string.graph_output_size_time);
        this.graph_options_string[2] = this.getString(R.string.graph_input_size_port);
        this.graph_options_string[3] = this.getString(R.string.graph_output_size_port);
        this.is_analysing = false;
        this.is_adding_exception = false;
        this.loader = (SpinKitView) findViewById(R.id.anim_load_network);
        this.change_button = (Button) findViewById(R.id.button_change_graph);
        this.anomalies_list = (ListView) findViewById(R.id.abnormal_measure_list);
        this.graph = (GraphView) findViewById(R.id.graph);
        this.info = (TextView) findViewById(R.id.info_n_text);
        this.n_t_1 = (TextView) findViewById(R.id.n_t_1);
        this.n_t_2 = (TextView) findViewById(R.id.n_t_2);
        this.change_button.setOnClickListener(new OpenDialogClickListener(this));
        this.create_dialog();
        this.start_animation();
    }

    public void create_dialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(NetworkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.graph_menu, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NetworkActivity.this);
        alertDialogBuilder.setView(promptView);

        this.graph_options = (ListView) promptView.findViewById(R.id.option_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,this.graph_options_string);
        this.graph_options.setAdapter(adapter);
        this.graph_options.setOnItemClickListener(new GraphTypeOnClickListener(this));

        alertDialogBuilder.setCancelable(true);
        dialog = alertDialogBuilder.create();
    }

    public void hide_dialog(){
        dialog.hide();
    }

    public void showDialog(){
        dialog.show();
    }

    public void change_graph(int gr){
        this.graph.removeAllSeries();
        if(gr == 0){
            this.graph.addSeries(this.input_size_time_graph);
        }else if(gr == 1){
            this.graph.addSeries(this.output_size_time_graph);
        }else if(gr == 2){
            this.graph.addSeries(this.input_size_port_graph);
        }else if(gr == 3) {
            this.graph.addSeries(this.output_size_port_graph);
        }
    }
    @Override
    public void start_animation() {
        this.loader.setIndeterminateDrawable(this.w);
        this.loader.setVisibility(View.VISIBLE);
        this.info.setVisibility(View.VISIBLE);
        this.anomalies_list.setVisibility(View.GONE);
        this.change_button.setVisibility(View.GONE);
        this.graph.setVisibility(View.GONE);
        this.n_t_1.setVisibility(View.GONE);
        this.n_t_2.setVisibility(View.GONE);
        this.start_analysis(false);
    }

    private void show_all(){
        this.loader.setVisibility(View.GONE);
        this.info.setVisibility(View.GONE);
        this.anomalies_list.setVisibility(View.VISIBLE);
        this.change_button.setVisibility(View.VISIBLE);
        this.graph.setVisibility(View.VISIBLE);
        this.n_t_1.setVisibility(View.VISIBLE);
        this.n_t_2.setVisibility(View.VISIBLE);
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            if(this.is_analysing){
                int response_code = response.getInt("code");
                this.is_analysing = false;
                if(response_code == -1){
                    this.info.setText(response.getString("PCAP cannot be installed"));
                }else if(response_code == 1){
                    JSONObject data = response.getJSONObject("data");
                    this.input_measures = NetworkMeasure.get_list_from_json(data.getJSONArray("input"));
                    this.output_measures = NetworkMeasure.get_list_from_json(data.getJSONArray("output"));
                    this.abnormal_measures = NetworkMeasure.get_list_from_json(data.getJSONArray("abnormal_input"));
                    generate_graphs();
                    this.graph.addSeries(this.input_size_time_graph);
                    this.show_all();
                }else if(response_code == 2){
                    this.info.setText("no data found");
                }else{
                    this.info.setText(response.getString("data"));
                }
            }else if(this.is_adding_exception){
                //TODO
            }
        } catch (JSONException e) {
            Log.e("get network r", Arrays.toString(e.getStackTrace()));
        }
    }

    public void start_analysis(boolean is_new){
        JSONObject query = new JSONObject();
        try {
            String command = "network analysis";
            if(is_new){
                command += " new";
            }
            query.put("command",command);
            this.is_analysing = true;
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("network analysis", Arrays.toString(e.getStackTrace()));
        }
    }

    public void add_measure_exception(NetworkMeasure measure){
        JSONObject query = new JSONObject();
        try {
            String command = "add network measure exception";
            query.put("command",command);
            query.put("args", measure.to_json());
            this.is_adding_exception = true;
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("adding m exception", Arrays.toString(e.getStackTrace()));
        }
    }

    private void generate_graphs(){

        Log.e("sdfdsf",Arrays.toString(NetworkMeasure.to_size_port(this.input_measures)));
        this.input_size_time_graph = new LineGraphSeries<>(NetworkMeasure.to_size_time(this.input_measures));
        this.output_size_time_graph = new LineGraphSeries<>(NetworkMeasure.to_size_time(this.output_measures));
        this.input_size_port_graph = new BarGraphSeries<>(NetworkMeasure.to_size_port(this.input_measures));
        this.output_size_port_graph = new BarGraphSeries<>(NetworkMeasure.to_size_port(this.output_measures));


        this.input_size_time_graph.setTitle("Input | size/time");
        this.input_size_time_graph.setAnimated(true);

        this.output_size_time_graph.setTitle("Output | size/time");
        this.output_size_time_graph.setAnimated(true);

        this.input_size_port_graph.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        this.input_size_port_graph.setDrawValuesOnTop(true);
        this.input_size_port_graph.setValuesOnTopColor(Color.RED);
        this.input_size_port_graph.setSpacing(50);
        this.input_size_port_graph.setTitle("Input | size/port");
        this.input_size_port_graph.setAnimated(true);

        this.output_size_port_graph.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        this.output_size_port_graph.setDrawValuesOnTop(true);
        this.output_size_port_graph.setValuesOnTopColor(Color.RED);
        this.output_size_port_graph.setSpacing(50);
        this.output_size_port_graph.setTitle("Output | size/port");
        this.output_size_port_graph.setAnimated(true);
    }
}
