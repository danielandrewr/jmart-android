package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.josephusdanieljmartfa.model.Account;
import com.josephusdanieljmartfa.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static Account getLoggedAccount() {
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText loginEmail = findViewById(R.id.emailInput);
        EditText loginPassword = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.login);
        TextView registerNow = findViewById(R.id.registerNow);
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            if (jObject != null) {
                                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                loggedAccount = gson.fromJson(jObject.toString(), Account.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            Toast.makeText(LoginActivity.this, "Login Tidak Berhasil", Toast.LENGTH_LONG);
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(loginEmail.getText().toString(), loginPassword.getText().toString(), listener, null);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}