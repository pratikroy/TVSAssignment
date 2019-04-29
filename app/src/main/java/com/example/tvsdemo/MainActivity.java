package com.example.tvsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mUsername;
    private EditText mUserPassword;
    private Button mLoginBtn;
    private ArrayList<EmpDetails> empDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize views
        mUsername = findViewById(R.id.editUsername);
        mUserPassword = findViewById(R.id.editPassword);
        mLoginBtn = findViewById(R.id.loginButton);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    doLogin();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void doLogin()throws JSONException {
        NetworkUtils networkUtils = new NetworkUtils();
        String name = mUsername.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();
        Log.v(TAG, name + " " + password);
        URL requiredUrl = networkUtils.builtURL();
        String response = networkUtils.getResponse(requiredUrl, name, password);
        if(response.equals("failure")){
            Toast.makeText(this, "Username or Password isn't correct", Toast.LENGTH_SHORT).show();
        }else{
            empDetails = JsonUtils.parseJson(response);
            Log.v(TAG, String.valueOf(empDetails.size()));
            Intent intent = new Intent(MainActivity.this, EmpNames.class);
            intent.putParcelableArrayListExtra("empList", empDetails);
            startActivity(intent);
        }
        // clear the edit texts after successful login
        mUsername.getText().clear();
        mUserPassword.getText().clear();
    }
}
