package com.example.alvaro.client_audit.activities;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.controllers.listeners.homeActivityListeners.DeviceListItemClickListener;
import com.example.alvaro.client_audit.core.bd.BD;
import com.example.alvaro.client_audit.core.exceptions.CloseConnectionExceptionHandler;
import com.example.alvaro.client_audit.core.networks.Connection;
import com.example.alvaro.client_audit.core.entities.DeviceBook;
import com.example.alvaro.client_audit.controllers.listeners.homeActivityListeners.AddButtonListener;
import com.example.alvaro.client_audit.controllers.listeners.homeActivityListeners.UpdateButtonListener;
import com.example.alvaro.client_audit.controllers.adapters.CardsAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class HomeActivity extends AppCompatActivity {


    /*
        Async task to check devices connection
     */
    private static class GetInBack extends AsyncTask<Object,Void,HomeActivity> {
        @Override
        protected HomeActivity doInBackground(Object... objects) {
            DeviceBook.set_adapter((CardsAdapter) objects[1]);
            return (HomeActivity) objects[0];
        }

        @Override
        protected void onPostExecute(HomeActivity activity) {
            Log.e("back","post");
            activity.stop_animation();
        }
    }

    private ListView cards;
    private FloatingActionButton add_button;
    private FloatingActionButton uptade_button;
    private SpinKitView loader;
    private ThreeBounce w = new ThreeBounce();
    private CardsAdapter cardsAdapter;

    @Override
    /*
        On create
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //new CloseConnectionExceptionHandler(this.getApplicationContext());
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            String toast_text = bundle.getString(CloseConnectionExceptionHandler.EXTRA_CLOSE_HANDLER);
            if(toast_text != null){
                Toast toast = Toast.makeText(this.getApplicationContext(),toast_text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }


        this.loader = (SpinKitView) findViewById(R.id.anim_load);
        this.cards = (ListView) findViewById(R.id.list);
        this.add_button = (FloatingActionButton) findViewById(R.id.b_add);
        this.uptade_button = (FloatingActionButton) findViewById(R.id.b_update);

        this.start_animation();

        Connection.set_resources_context(this.getResources());
        BD.setInstanceContext(this.getApplicationContext());
        this.add_button.setOnClickListener(new AddButtonListener(this.getApplicationContext()));
        this.uptade_button.setOnClickListener(new UpdateButtonListener(this));
        this.cards.setOnItemClickListener(new DeviceListItemClickListener(this.getApplicationContext()));
        cardsAdapter = new CardsAdapter(this);
        cards.setAdapter(cardsAdapter);
        new GetInBack().execute(this,cardsAdapter);

    }

    /*
        start animation while asyncTask
     */
    public void start_animation(){
        loader.setVisibility(View.VISIBLE);
        loader.setIndeterminateDrawable(w);
        this.cards.setVisibility(View.GONE);
        this.add_button.setEnabled(false);
        this.uptade_button.setEnabled(false);
        Log.e("startAnim","started");
    }

    /*
        called by asyncTask when it terminates
     */
    public void stop_animation(){
        DeviceBook.get_instance().update_adapter();
        this.cards.setVisibility(View.VISIBLE);
        this.loader.setVisibility(View.GONE);
        this.add_button.setEnabled(true);
        this.uptade_button.setEnabled(true);
        Log.e("stopAnim","stoped");
    }
}
