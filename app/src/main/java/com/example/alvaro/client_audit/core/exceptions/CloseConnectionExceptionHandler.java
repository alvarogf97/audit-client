package com.example.alvaro.client_audit.core.exceptions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.alvaro.client_audit.activities.HomeActivity;
import com.example.alvaro.client_audit.core.networks.Connection;

import java.util.Arrays;

public class CloseConnectionExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final String EXTRA_CLOSE_HANDLER = "EXTRA_CLOSE_HANDLER";
    private Context context;
    private Thread.UncaughtExceptionHandler rootHandler;

    public CloseConnectionExceptionHandler(Context context){
        this.context = context;
        rootHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("Uncaught exception", Arrays.toString(e.getStackTrace()));
        Connection.get_connection().close();
        Intent intent = new Intent(this.context,HomeActivity.class);
        intent.putExtra(CloseConnectionExceptionHandler.EXTRA_CLOSE_HANDLER,"Connection closed");
        context.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
