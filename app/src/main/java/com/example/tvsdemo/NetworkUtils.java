package com.example.tvsdemo;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String baseUrl = "http://tvsfit.mytvs.in";
    private int responseCode = 0;
    private String jsonResponse = null;

    public URL builtURL(){
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendPath("reporting").appendPath("vrm").appendPath("api")
                .appendPath("test_new").appendPath("int").appendPath("gettabledata.php").build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
            Log.v(TAG, "URL: " + url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public String getResponse(final URL loginUrl, final String name, final String password){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setRequestProperty("Accept","application/json");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    // Built the Json to post data
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("username", name);
                    jsonObject.put("password", password);
                    Log.v(TAG, jsonObject.toString());
                    // Make data output stream
                    DataOutputStream os = new DataOutputStream(connection.getOutputStream());
                    os.writeBytes(jsonObject.toString());
                    os.flush();
                    os.close();
                    //build the json response
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line);
                    }
                    connection.disconnect();
                    //check response code and response body
                    responseCode = connection.getResponseCode();
                    jsonResponse = stringBuilder.toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.v(TAG, "Response code: " + String.valueOf(responseCode));
        if(responseCode == 200){
            return jsonResponse;
        }else{
            return "failure";
        }
    }
}
