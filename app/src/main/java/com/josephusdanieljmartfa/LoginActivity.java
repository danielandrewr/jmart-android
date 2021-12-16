package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    private static final String SHARED_PREFS = "shared_prefs";

    public static final String EMAIL_KEY = "email_key";

    public static final String PASSWORD_KEY = "password_key";

    private SharedPreferences sharedPreferences;
    private String EMAIL_SESSION, PASSWORD_SESSION;

    @Override
    protected void onStart() {
        super.onStart();
        if (EMAIL_SESSION != null && PASSWORD_SESSION != null) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObject = new JSONObject(response);
                        if (jObject != null) {
                            loggedAccount = gson.fromJson(jObject.toString(), Account.class);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Gagal Memuat Session", Toast.LENGTH_LONG).show();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan pada Server!", Toast.LENGTH_LONG).show();
                }
            };
            LoginRequest request = new LoginRequest(EMAIL_SESSION.toString(), PASSWORD_SESSION.toString(), listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(request);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initStatusBar();
        EditText loginEmail = findViewById(R.id.emailInput);
        EditText loginPassword = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.login);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        EMAIL_SESSION = sharedPreferences.getString(EMAIL_KEY, null);
        PASSWORD_SESSION = sharedPreferences.getString(PASSWORD_KEY, null);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            if (jObject != null) {
                                // Preferences Setting to Apply Inputted Email and Password
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(EMAIL_KEY, loginEmail.getText().toString());
                                editor.putString(PASSWORD_KEY, loginPassword.getText().toString());
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                loggedAccount = gson.fromJson(jObject.toString(), Account.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login Tidak Berhasil", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error Membuat Request!", Toast.LENGTH_SHORT).show();
                    }
                };
                LoginRequest loginRequest = new LoginRequest(
                        loginEmail.getText().toString(),
                        loginPassword.getText().toString(),
                        listener,
                        errorListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}