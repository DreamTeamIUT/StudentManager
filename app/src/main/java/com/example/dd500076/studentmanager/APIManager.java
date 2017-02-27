package com.example.dd500076.studentmanager;

import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

/**
 * Created by ft503084 on 27/02/17.
 */

public class APIManager {
    public String valueToken;

    public APIManager() {

    }

    public boolean connect(String username, String password) {
        request("http://infort.gautero.fr/connect.php?log='" + username + "'&mdp='" + password + "'", new RequestCallback() {
            @Override
            public void onResult(JSONArray object) {
                try {
                    if (!((JSONObject) object.get(0)).get("jeton").equals("")) {
                        valueToken = ((JSONObject) object.get(0)).getString("jeton");
                    }else{
                        return false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    public JSONObject request(final String url, final RequestCallback callback) {
    /*   new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpRequest = null;
                InputStream in = null;
                Log.d("REQUEST", "request: onEnter");
                try {
                    httpRequest = (HttpURLConnection) url.openConnection();
                    in = new BufferedInputStream(httpRequest.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    httpRequest.disconnect();
                }
                try {
                    callback.onResult(new JSONObject(new JSONTokener(readStream(in))));
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
        */


        RequestFuture<JSONObject> future = RequestFuture.newFuture();
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

        return null;

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
}
