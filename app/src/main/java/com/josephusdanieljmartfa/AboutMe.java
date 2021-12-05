package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.josephusdanieljmartfa.model.Account;
import com.josephusdanieljmartfa.request.CreateStoreRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        getSupportActionBar().hide();

        Account loggedAccount = LoginActivity.getLoggedAccount();

        TextView nameValue = findViewById(R.id.nameValue);
        nameValue.setText(loggedAccount.name);
        TextView emailValue = findViewById(R.id.emailValue);
        emailValue.setText(loggedAccount.email);
        TextView balanceValue = findViewById(R.id.balanceValue);
        balanceValue.setText(String.valueOf(loggedAccount.balance));

        //CardView components
        CardView detailsCard = findViewById(R.id.card_view_store_details);
        CardView registerCard = findViewById(R.id.card_view_register_store);

        Button registerStoreButton = findViewById(R.id.registerStoreButton);
        if ((loggedAccount.store == null)) {
            registerStoreButton.setVisibility(View.VISIBLE);
        } else {
            detailsCard.setVisibility(View.VISIBLE);
            TextView storeName = findViewById(R.id.storeName);
            storeName.setText(LoginActivity.getLoggedAccount().store.name);
            TextView storeAddress = findViewById(R.id.storeAddress);
            storeAddress.setText(LoginActivity.getLoggedAccount().store.address);
            TextView storePhoneNumber = findViewById(R.id.storePhoneNumber);
            storePhoneNumber.setText(LoginActivity.getLoggedAccount().store.phoneNumber);
        }
        registerStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStoreButton.setVisibility(View.INVISIBLE);
                registerCard.setVisibility(View.VISIBLE);
            }
        });

        EditText storeName = findViewById(R.id.registerStoreName);
        EditText storeAddress = findViewById(R.id.registerStoreAddress);
        EditText storePhoneNumber = findViewById(R.id.registerStorePhoneNumber);

        Button createStoreButton = findViewById(R.id.createStoreButton);
        createStoreButton.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                if ((!storeName.getText().toString().isEmpty()) || (!storeAddress.getText().toString().isEmpty()) ||
                        (!storePhoneNumber.getText().toString().isEmpty())) {
                    try {
                        JSONObject jObject = new JSONObject(response);
                        if (jObject != null)
                            Toast.makeText(AboutMe.this, "Store Berhasil Dibuat!", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(AboutMe.this, "Store Gagal Dibuat!", Toast.LENGTH_LONG).show();
                    }
                }
            };
            Response.ErrorListener errorListener = response -> {
                Toast.makeText(AboutMe.this, "Request Error!", Toast.LENGTH_LONG).show();
            };
            CreateStoreRequest newStoreRequest = new CreateStoreRequest(loggedAccount.id, storeName.getText().toString(), storeAddress.getText().toString(),
                    storePhoneNumber.getText().toString(), listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(AboutMe.this);
            queue.add(newStoreRequest);
            Toast.makeText(AboutMe.this, "Silahkan login ulang!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AboutMe.this, LoginActivity.class);
            startActivity(intent);
        });


        Button cancelButton = findViewById(R.id.cancelStoreButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}