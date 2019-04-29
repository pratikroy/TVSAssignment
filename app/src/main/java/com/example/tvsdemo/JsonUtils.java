package com.example.tvsdemo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class JsonUtils {

    public static ArrayList<EmpDetails> parseJson(String jsonResponse)throws JSONException {
        ArrayList<EmpDetails> empDetails = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        String jsonData = jsonObject.getString("TABLE_DATA");
        JSONObject jsonObject1 = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject1.getJSONArray("data");
        for(int i = 0; i < jsonArray.length(); i++){
            JSONArray employee = jsonArray.getJSONArray(i);
            empDetails.add(new EmpDetails(employee.getString(0),
                    employee.getString(1),
                    employee.getString(2),
                    employee.getString(3),
                    employee.getString(4),
                    employee.getString(5)));
        }
        return empDetails;
    }
}
