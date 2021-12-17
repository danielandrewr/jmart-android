package com.josephusdanieljmartfa;

import static com.josephusdanieljmartfa.ProductsFragment.getActiveProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.josephusdanieljmartfa.model.Product;
import com.josephusdanieljmartfa.request.PhoneTopUpRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity yang memberikan informasi tentang suatu Product
 * Navigasi untuk membeli product terdapat di sini
 */
public class ProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().hide();

        TextView productName = findViewById(R.id.productFilterName);
        TextView productWeight = findViewById(R.id.productFilterWeight);
        TextView productPrice = findViewById(R.id.productFilterPrice);
        TextView productDiscount = findViewById(R.id.productFilterDiscount);
        TextView productCategory = findViewById(R.id.productFilterCategory);
        TextView productCondition = findViewById(R.id.productFilterCondition);

        try {
            if (getActiveProduct() != null) {
                productName.setText(getActiveProduct().get("name"));
                productWeight.setText(getActiveProduct().get("weight"));
                productPrice.setText(getActiveProduct().get("price"));
                productDiscount.setText(getActiveProduct().get("discount"));
                productCategory.setText(getActiveProduct().get("category"));
                if (getActiveProduct().get("condition") == "true") {
                    productCondition.setText("Used");
                } else {
                    productCondition.setText("New");
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        MaterialButton buyButton = findViewById(R.id.buyProduct);
        MaterialButton back = findViewById(R.id.goBack);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetail.this, PaymentActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            }
        });

        EditText phoneNumber = findViewById(R.id.phoneNumber);
        MaterialButton payWithPhone = findViewById(R.id.payWithPhone);
        payWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phoneNumber.getText().toString().isEmpty()) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ProductDetail.this, "Payment Berhasil!", Toast.LENGTH_LONG).show();
                        }
                    };
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ProductDetail.this, "Gagal Mengirimkan Request!", Toast.LENGTH_LONG).show();
                        }
                    };
                    StringRequest request = new PhoneTopUpRequest(
                            LoginActivity.getLoggedAccount().id,
                            Integer.parseInt(getActiveProduct().get("productId")),
                            phoneNumber.getText().toString(),
                            listener,
                            errorListener);
                    RequestQueue queue = Volley.newRequestQueue(ProductDetail.this);
                    queue.add(request);
                } else {
                    Toast.makeText(ProductDetail.this, "Phone Number tidak boleh kosong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}