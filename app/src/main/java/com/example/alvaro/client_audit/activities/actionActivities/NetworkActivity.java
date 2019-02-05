package com.example.alvaro.client_audit.activities.actionActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.activities.AsyncTaskActivity;
import com.example.alvaro.client_audit.controllers.adapters.LegendAdapter;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.GraphTypeOnClickListener;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.OpenDialogClickListener;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.ReStartOnClickListener;
import com.example.alvaro.client_audit.controllers.listeners.networkActivityListeners.ShowAnomaliesClickListener;
import com.example.alvaro.client_audit.core.entities.LegendItem;
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
import java.util.Map;

public class NetworkActivity extends AsyncTaskActivity {

    private String[] graph_options_string ;

    private GraphView graph;
    private ListView graph_legend;
    private Button change_button;
    private SpinKitView loader;
    private TextView info;
    private TextView n_t_1;
    private TextView n_t_2;
    private ListView graph_options;
    private AlertDialog dialog;
    private Button re_scan;
    private Button anomalies_button;
    private TextView not_found_text;
    private ImageView not_found_image;

    private List<NetworkMeasure> input_measures;
    private List<NetworkMeasure> output_measures;
    public static List<NetworkMeasure> abnormal_measures;

    private LineGraphSeries<DataPoint> input_size_time_graph;
    private LineGraphSeries<DataPoint> output_size_time_graph;
    private BarGraphSeries<DataPoint> input_size_port_graph;
    private BarGraphSeries<DataPoint> output_size_port_graph;

    private List<LegendItem> input_legend;
    private List<LegendItem> output_legend;
    private LegendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        this.graph_options_string = new String[4];
        this.graph_options_string[0] = this.getString(R.string.graph_input_size_time);
        this.graph_options_string[1] = this.getString(R.string.graph_output_size_time);
        this.graph_options_string[2] = this.getString(R.string.graph_input_size_port);
        this.graph_options_string[3] = this.getString(R.string.graph_output_size_port);
        this.not_found_image = (ImageView) findViewById(R.id.not_found_image);
        this.not_found_text = (TextView) findViewById(R.id.n_f_n);
        this.loader = (SpinKitView) findViewById(R.id.anim_load_network);
        this.change_button = (Button) findViewById(R.id.button_change_graph);
        this.graph_legend = (ListView) findViewById(R.id.legend_list);
        this.graph = (GraphView) findViewById(R.id.graph);
        this.re_scan= (Button) findViewById(R.id.re_start_analysis_button);
        this.anomalies_button = (Button) findViewById(R.id.button_n_anomalies);
        this.info = (TextView) findViewById(R.id.info_n_text);
        this.n_t_1 = (TextView) findViewById(R.id.n_t_1);
        this.n_t_2 = (TextView) findViewById(R.id.n_t_2);
        this.change_button.setOnClickListener(new OpenDialogClickListener(this));
        graph.getViewport().setScalable(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1);
        this.re_scan.setOnClickListener(new ReStartOnClickListener(this));
        this.anomalies_button.setOnClickListener(new ShowAnomaliesClickListener(this));
        this.adapter = new LegendAdapter(this);
        this.graph_legend.setAdapter(adapter);
        this.create_dialog();
        this.start_animation();
    }

    public void show_no(){
        this.hide_all();
        this.not_found_text.setVisibility(View.VISIBLE);
        this.not_found_image.setVisibility(View.VISIBLE);
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
            adapter.clear();
            adapter.addAll(this.input_legend);
        }else if(gr == 3) {
            this.graph.addSeries(this.output_size_port_graph);
            adapter.clear();
            adapter.addAll(this.output_legend);
        }
    }
    @Override
    public void start_animation() {
        this.hide_all();
        this.start_analysis(false);
    }

    private void hide_all(){
        this.loader.setIndeterminateDrawable(this.w);
        this.loader.setVisibility(View.VISIBLE);
        this.info.setVisibility(View.VISIBLE);
        this.graph_legend.setVisibility(View.GONE);
        this.change_button.setVisibility(View.GONE);
        this.graph.setVisibility(View.GONE);
        this.n_t_1.setVisibility(View.GONE);
        this.n_t_2.setVisibility(View.GONE);
        this.re_scan.setVisibility(View.GONE);
        this.anomalies_button.setVisibility(View.GONE);
    }

    private void show_all(){
        this.loader.setVisibility(View.GONE);
        this.info.setVisibility(View.GONE);
        this.graph_legend.setVisibility(View.VISIBLE);
        this.change_button.setVisibility(View.VISIBLE);
        this.graph.setVisibility(View.VISIBLE);
        this.n_t_1.setVisibility(View.VISIBLE);
        this.n_t_2.setVisibility(View.VISIBLE);
        this.re_scan.setVisibility(View.VISIBLE);
        this.anomalies_button.setVisibility(View.VISIBLE);
    }

    @Override
    public void stop_animation(Object... objects) {
        JSONObject response = (JSONObject) objects[0];
        try {
            int response_code = response.getInt("code");
            if(response_code == -1){
                this.not_found_text.setText("PCAP cannot be installed");
                this.show_no();
            }else if(response_code == 1){
                JSONObject data = response.getJSONObject("data");
                this.input_measures = NetworkMeasure.get_list_from_json(data.getJSONArray("input"));
                this.output_measures = NetworkMeasure.get_list_from_json(data.getJSONArray("output"));
                abnormal_measures = NetworkMeasure.get_list_from_json(data.getJSONArray("abnormal_input"));
                abnormal_measures.addAll(NetworkMeasure.get_list_from_json(data.getJSONArray("abnormal_output")));
                generate_graphs();
                this.graph.addSeries(this.input_size_time_graph);
                this.show_all();
            }else if(response_code == 2){
                this.not_found_text.setText("No network data found!");
                this.show_no();
            }else{
                this.info.setText(response.getString("data"));
                this.start_analysis(false);
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
            Connection.get_connection().execute_command(query, this);
        } catch (JSONException e) {
            Log.e("network analysis", Arrays.toString(e.getStackTrace()));
        }
    }

    private void generate_graphs(){

        this.input_size_time_graph = new LineGraphSeries<>(NetworkMeasure.to_size_time(this.input_measures));
        this.output_size_time_graph = new LineGraphSeries<>(NetworkMeasure.to_size_time(this.output_measures));

        List<Object> result_input = NetworkMeasure.to_size_port(this.input_measures);
        List<Object> result_output = NetworkMeasure.to_size_port(this.output_measures);

        this.input_size_port_graph = new BarGraphSeries<>((DataPoint[]) result_input.get(0));
        this.output_size_port_graph = new BarGraphSeries<>((DataPoint[]) result_output.get(0));

        this.input_legend = LegendItem.getItemsFromMap( (Map<Integer,Integer>)result_input.get(1));
        this.output_legend = LegendItem.getItemsFromMap( (Map<Integer,Integer>)result_output.get(1));

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

        this.input_size_port_graph.setSpacing(50);
        this.input_size_port_graph.setTitle("Input | size/port");
        this.input_size_port_graph.setAnimated(true);


        this.output_size_port_graph.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        this.output_size_port_graph.setSpacing(50);
        this.output_size_port_graph.setTitle("Output | size/port");
        this.output_size_port_graph.setAnimated(true);
    }
}
