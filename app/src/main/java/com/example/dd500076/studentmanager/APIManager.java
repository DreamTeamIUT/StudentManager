package com.example.dd500076.studentmanager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by ft503084 on 27/02/17.
 */

public class APIManager {
    private static APIManager apiManager;

    public String token;

    private Context context;
    private RequestMessageInterface requestMessageInterface;

    public APIManager(Context context) {
        this.context = context;

        LocalBroadcastManager.getInstance(this.context).registerReceiver(onMessageRequest, new IntentFilter("message-request-event"));
    }

    public static APIManager getInstance(Context context) {
        if (apiManager == null)
            apiManager = new APIManager(context);

        return apiManager;
    }

    public void registerEvents(RequestMessageInterface requestMessageInterface){
        this.requestMessageInterface = requestMessageInterface;
    }

    public void connect(String username, String password) {
        String url = "http://infort.gautero.fr/connect.php?login=" + username + "&mdp=" + password;
        request(url, RequestName.CONNECT);
    }

    public Boolean isConnected() {
        return this.token != null && !this.token.equals("");
    }

    public String getToken() {
        return this.token;
    }
    public void getStudentList() {
        String url = "http://infort.gautero.fr/listEtu.php";
        request(url, RequestName.LISTE);
    }

    private void request(final String urlText, final String requestName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlText);
                    InputStream in = url.openStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    Intent intent = new Intent("message-request-event");
                    intent.putExtra("requestName", requestName);
                    intent.putExtra("message", readStream(in));

                    LocalBroadcastManager.getInstance(APIManager.this.context).sendBroadcast(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /*
       new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpRequest = null;
                InputStream in = null;
                Log.d("REQUEST", "request: onEnter");
                try {
                    httpRequest = (HttpURLConnection) url.;
                    in = new BufferedInputStream(httpRequest.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    httpRequest.disconnect();
                }
                try {
                    callback.onResult(new JSONArray(new JSONTokener(readStream(in))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    URI uri = new URI(url);
                    callback.onResult(new JSONArray(new JSONTokener(readStream(uri.toURL().openStream()))));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
        }).start();



        /*RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = null;

            request = new JsonObjectRequest("https://jsonplaceholder.typicode.com/posts/1", new JSONObject(), future, future);

        ArrayList<JsonObjectRequest> requestQueue = new ArrayList<>();
        requestQueue.add(request);

       /* try {
            JSONObject response = future.get(); // this will block
        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            // exception handling
        }*/

        //return null;
    }

    private String readStream(InputStream is) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private BroadcastReceiver onMessageRequest = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getStringExtra("requestName") + " " + intent.getStringExtra("message"));
            try {
                JSONArray jsonArray = new JSONArray(intent.getStringExtra("message"));
                JSONObject jsonObject = (JSONObject)jsonArray.get(0);

                if (intent.getStringExtra("requestName").equals(RequestName.CONNECT)){
                    Boolean connected = !jsonObject.getString("jeton").equals("");

                    APIManager.this.token = jsonObject.getString("jeton");

                    APIManager.this.requestMessageInterface.onConnect(connected, (connected ? jsonObject.getString("jeton") : null));
                }else if (intent.getStringExtra("requestName").equals(RequestName.LISTE)){

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    public void onTerminate(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(onMessageRequest);
    }

    public class RequestName {
        public static final String CONNECT = "CONNECT";
        public static final String LISTE = "LISTE";
    }
}
