package com.example.alvaro.client_audit.core.networks;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.example.alvaro.client_audit.R;
import com.example.alvaro.client_audit.core.exceptions.ConnectionException;
import com.example.alvaro.client_audit.core.utils.JsonParsers;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocketFactory;

public class Connection{

    private static class ConnectionHandler extends AsyncTask<Object,Void,List<Object>>{

        @Override
        protected List<Object> doInBackground(Object... objects) {
            List<Object> result = new ArrayList<>();
            String command = (String) objects[0];
            if(command.equals("create")){
                try{
                    SSLSocketFactory factory = (SSLSocketFactory) objects[1];
                    String host = (String) objects[2];
                    int port = (int) objects[3];
                    Socket socket = factory.createSocket();
                    Log.e("connectHandler",host + ":" + String.valueOf(port));
                    socket.connect(new InetSocketAddress(host,port),1000);
                    Log.e("connectHandler","after connections");
                    DataInputStream dIn = new DataInputStream(socket.getInputStream());
                    String response = recv_msg(dIn);
                    dIn.close();
                    result.add(socket);
                    result.add(response);
                }catch(SocketTimeoutException e){
                    Log.d("Conn::Handler::timeout",Arrays.toString(e.getStackTrace()));
                }catch(Exception e){
                    Log.d("Conn::Handler::ex",Arrays.toString(e.getStackTrace()));
                }
            }else{
                DataInputStream dIn = (DataInputStream) objects[1];
                DataOutputStream dOut = (DataOutputStream) objects[2];
                String msg = (String) objects[3];
                send_msg(dOut,msg);
                String res = recv_msg(dIn);
                result.add(res);
            }
            Log.e("Handler","finish back");
            return result;
        }

        /*
        private utils functions
        */

        private static byte [] combine(byte [] array1, byte [] array2){
            byte [] result = new byte[array1.length+array2.length];
            for(int i = 0; i<array1.length; i++){
                result[i] = array1[i];
            }

            for(int i = array1.length; i<array1.length+array2.length; i++){
                result[i] = array2[i-array1.length];
            }
            return result;
        }

        private static void send_msg(DataOutputStream dOut, String msg){
            try {
                byte [] msg_bytes = msg.getBytes("UTF-8");
                byte [] msg_tam = ByteBuffer.allocate(4).putInt(msg_bytes.length).array();
                byte [] all_msg = combine(msg_tam,msg_bytes);
                dOut.write(all_msg);
                dOut.flush();
            } catch (IOException e) {
                Log.v("Conn::send_msg()",Arrays.toString(e.getStackTrace()));
            }
        }

        private static String recv_msg(DataInputStream dIn){
            String result = "";
            try {
                int msg_len = ByteBuffer.wrap(recvall(dIn,4)).getInt();
                result = new String(recvall(dIn,msg_len),"UTF-8");
            } catch (IOException e) {
                Log.v("Conn::recv_msg()",Arrays.toString(e.getStackTrace()));
            }
            return result;
        }

        private static byte[] recvall(DataInputStream dIn, int tam) throws IOException {
            int position = 0;
            byte [] data = new byte[tam];
            while(position < tam){
                data[position] = dIn.readByte();
                position++;
            }
            return data;
        }

    }

    /*
        static variables
     */

    private static Resources res = null;
    private static Connection instance = null;

    /*
        private class variables
     */

    private SSLSocketFactory factory;
    private Socket connection;
    private DataInputStream dIn;
    private DataOutputStream dOut;

    /*
        static functions
     */

    public static Connection get_connection(){
        if(res == null){
            throw new ConnectionException("No resources context available");
        }else if(instance == null){
            instance = new Connection();
        }
        return instance;
    }

    public static void set_resources_context(Resources resources){
        res = resources;
        instance = new Connection();
    }

    /*
        private Constructor
     */

    private Connection(){
        InputStream ca = res.openRawResource(R.raw.ca_cert);
        InputStream client_cert = res.openRawResource(R.raw.client_cert);
        InputStream client_cert_key = res.openRawResource(R.raw.client_key);
        factory = SslUtil.getSocketFactory(ca,client_cert,client_cert_key,"TFG");

        try {
            ca.close();
            client_cert.close();
            client_cert_key.close();
        } catch (IOException e) {
            Log.v("Conn::constructor", Arrays.toString(e.getStackTrace()));
        }

    }

    /*
        public functions
     */

    public JSONObject connect(String host, int port){

        String result = null;

        if(this.connection != null) {
            try {
                this.connection.close();
            } catch (IOException e) {
                Log.e("Conn::connect()", Arrays.toString(e.getStackTrace()));
            }
        }

        try {
            Log.e("connect","trying to connect");
            List<Object> res = new ConnectionHandler().execute("create",factory,host,port).get();
            Log.e("length",String.valueOf(res.size()));
            this.connection = (Socket)res.get(0);
            result = (String)res.get(1);
            this.dIn = new DataInputStream(this.connection.getInputStream());
            this.dOut = new DataOutputStream(this.connection.getOutputStream());
        } catch (Exception e) {
            Log.e("Conn::connect()",Arrays.toString(e.getStackTrace()));
        }

        return JsonParsers.parse_string(result);
    }

    public void close(){
        try {
            this.dIn.close();
            this.dOut.close();
            this.connection.close();
        } catch (Exception e) {
            Log.v("Conn::close()", Arrays.toString(e.getStackTrace()));
        }
    }

    public JSONObject execute_command(String msg){

        JSONObject result = null;

        try {
            result = JsonParsers.parse_string((String) new ConnectionHandler().execute("command",dIn,dOut,msg).get().get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean check_device(String ip, int port){
        boolean status = false;
        JSONObject response = this.connect(ip, port);
        if(response != null){
            this.close();
            status = true;
        }
        return status;
    }

    public boolean check_device_foreground(String ip, int port){
        boolean status = false;
        try{
            Socket socket = factory.createSocket();
            socket.connect(new InetSocketAddress(ip,port),1000);
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            JSONObject response = JsonParsers.parse_string(ConnectionHandler.recv_msg(dIn));
            if(response != null){
                status = true;
                dIn.close();
                socket.close();
            }
        }catch(SocketTimeoutException e){
            Log.e("Conn::Handler::timeout",Arrays.toString(e.getStackTrace()));
        }catch(Exception e){
            Log.e("Conn::Handler::ex",Arrays.toString(e.getStackTrace()));
        }
        return status;
    }

}
