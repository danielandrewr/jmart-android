package com.josephusdanieljmartfa;

import static com.josephusdanieljmartfa.ProductsFragment.getActiveProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.josephusdanieljmartfa.request.PaymentRequest;

import org.json.JSONObject;

import java.io.StringBufferInputStream;
import java.lang.reflect.Array;
import java.util.Locale;

/**
 * Activity untuk Melakukan Payment atas Product tertentu
 */
public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();

        EditText productCount = findViewById(R.id.paymentProductCount);
        EditText productAddress = findViewById(R.id.paymentAddress);
        Spinner productShipmentPlan = findViewById(R.id.paymentShipmentPlan);
        TextView priceToPay = findViewById(R.id.priceToPay);
        TextView currentBal = findViewById(R.id.currentBalance);

        try {
            if (getActiveProduct() != null) {
                double balance = LoginActivity.getLoggedAccount().balance;
                currentBal.setText(String.valueOf(balance));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String[] shipmentPlans = {"INSTANT", "SAME DAY", "NEXT DAY", "REGULER", "KARGO"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, shipmentPlans) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextSize(16);
                return view;
            }

            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setGravity(Gravity.RIGHT);
                return view;
            }
        };

        productShipmentPlan.setAdapter(adapter);

        TextWatcher priceWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (getActiveProduct() != null) {
                        if (!productCount.getText().toString().isEmpty()) {
                            int amount = Integer.parseInt(String.valueOf(productCount.getText().toString()));
                            double total = Double.parseDouble(getActiveProduct().get("price")) * amount;

                            priceToPay.setText(String.valueOf(total));
                        } else {
                            priceToPay.setText("0");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };

        productCount.addTextChangedListener(priceWatcher);

        MaterialButton payButton = findViewById(R.id.buyProduct);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!productCount.getText().toString().isEmpty() && !productAddress.getText().toString().isEmpty()
                        && !productShipmentPlan.getSelectedItem().toString().isEmpty()) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObject = new JSONObject(response);
                                if (jObject != null) {
                                    Toast.makeText(PaymentActivity.this, "Payment Berhasil!", Toast.LENGTH_LONG).show();
                                    System.out.print(jObject);
                                } else {
                                    Toast.makeText(PaymentActivity.this, "Payment Gagal!", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PaymentActivity.this, "Error Mengirim Payment Request ke Server!", Toast.LENGTH_LONG).show();
                        }
                    };

                    StringRequest request = new PaymentRequest(
                            LoginActivity.getLoggedAccount().id,
                            Integer.parseInt(getActiveProduct().get("productId")),
                            productCount.getText().toString(),
                            productAddress.getText().toString(),
                            String.valueOf(convertStringToByte(productShipmentPlan)),
                            listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
                    queue.add(request);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                }
            }
        });

        MaterialButton cancelButton = findViewById(R.id.cancelProduct);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
            }
        });
    }

    private byte convertStringToByte(Spinner productShipmentPlan) {
        if (productShipmentPlan.getSelectedItem().toString().equals("INSTANT")) {
            return (byte)1;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("SAME DAY")) {
            return (byte)2;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("NEXT DAY")) {
            return (byte)4;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("REGULER")) {
            return (byte)8;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("KARGO")) {
            return (byte)16;
        }
        return 0;
    }
}