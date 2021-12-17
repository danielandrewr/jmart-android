package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.josephusdanieljmartfa.model.ProductCategory;
import com.josephusdanieljmartfa.request.CreateProductRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity untuk membuat Product Baru
 */
public class CreateProductActivity extends AppCompatActivity {

    private boolean conditionUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        getSupportActionBar().hide();

        EditText productName = findViewById(R.id.productName);
        EditText productWeight = findViewById(R.id.productWeight);
        EditText productPrice = findViewById(R.id.productPrice);
        EditText productDiscount = findViewById(R.id.productDiscount);
        RadioButton radioNew = findViewById(R.id.productConditionNew);
        Spinner productCategory = findViewById(R.id.productCategorySpinner);
        Spinner productShipmentPlans = findViewById(R.id.productShipmentPlan);

        Button productCreate = findViewById(R.id.productCreateButton);
        productCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionUsed = !radioNew.isChecked();

                byte validShipmentPlan = checkShipmentPlan(productShipmentPlans);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObject = new JSONObject(response);
                            if (jObject != null) {
                                Toast.makeText(CreateProductActivity.this, "Produk Berhasil Dibuat!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateProductActivity.this, "Produk Gagal Dibuat!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CreateProductActivity.this, "Produk Gagal Dibuat!", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateProductActivity.this, "Produk Error!", Toast.LENGTH_LONG).show();
                    }
                };
                CreateProductRequest createRequest = new CreateProductRequest(
                        String.valueOf(LoginActivity.getLoggedAccount().id),
                        productName.getText().toString(),
                        productWeight.getText().toString(),
                        String.valueOf(conditionUsed),
                        productPrice.getText().toString(),
                        productDiscount.getText().toString(),
                        productCategory.getSelectedItem().toString(),
                        String.valueOf(validShipmentPlan),
                        listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                queue.add(createRequest);
            }
        });

        Button cancelButton = findViewById(R.id.productCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            }
        });
    }

    private byte checkShipmentPlan(Spinner productShipmentPlan) {
        if (productShipmentPlan.getSelectedItem().toString().equals("INSTANT")) {
            return (byte)00000001;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("SAME DAY")) {
            return (byte)00000010;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("NEXT DAY")) {
            return (byte)00000100;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("REGULER")) {
            return (byte)00001000;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("KARGO")) {
            return (byte)00010000;
        }
        return 0;
    }
}