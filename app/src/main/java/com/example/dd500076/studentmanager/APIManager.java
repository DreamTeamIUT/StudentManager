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

    public void addStudent(String name, String surname, String formation, String year) {
        String jeton = getToken();
        String url = "http://infort.gautero.fr/add.php?jeton=" + jeton + "&nom=" + name +
                "&prenom=" + surname + "&formation=" + formation + "&annee=" + year;
        request(url, RequestName.ADD);
    }
    public void delStudent(String idUser) {
        String jeton = getToken();
        String url = "http://infort.gautero.fr/supp.php?jeton=" + jeton + "id=" + idUser;
    }

    private void request(final String urlText, final String requestName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlText);
                    InputStream in = url.openStream();
                    Intent intent = new Intent("message-request-event");
                    intent.putExtra("requestName", requestName);
                    intent.putExtra("message", readStream(in));

                    LocalBroadcastManager.getInstance(APIManager.this.context).sendBroadcast(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

                if (intent.getStringExtra("requestName").equals(RequestName.CONNECT)) {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(0);

                    Boolean connected = !jsonObject.getString("jeton").equals("");

                    APIManager.this.token = jsonObject.getString("jeton");

                    APIManager.this.requestMessageInterface.onConnect(connected, (connected ? jsonObject.getString("jeton") : null));
                }else if (intent.getStringExtra("requestName").equals(RequestName.LISTE)) {
                    //APIManager.this.requestMessageInterface.onStudentList();

                    ArrayList<User> users = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        users.add(new User(jsonObject.getString("idEtu"), jsonObject.getString("nom"), jsonObject.getString("prenom"), jsonObject.getString("formation"), jsonObject.getInt("annee")));
                    }

                    APIManager.this.requestMessageInterface.onStudentList(users);
                }else if (intent.getStringExtra("requestName").equals(RequestName.ADD)){
                    Log.d(TAG, "onReceive: pomme de terre ");
                    APIManager.this.requestMessageInterface.onAddStudent(true);
                }else if (intent.getStringExtra("requestName").equals(RequestName.DEL)){
                    APIManager.this.requestMessageInterface.onDeleteStudent(true);
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
        public static final String ADD = "ADD";
        public static final String DEL = "DEL";
    }
}
