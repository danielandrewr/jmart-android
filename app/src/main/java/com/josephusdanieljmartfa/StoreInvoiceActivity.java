package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.josephusdanieljmartfa.model.Invoice;
import com.josephusdanieljmartfa.request.AcceptRequest;
import com.josephusdanieljmartfa.request.CancelRequest;
import com.josephusdanieljmartfa.request.RequestFactory;
import com.josephusdanieljmartfa.request.SubmitRequest;

/**
 * Activity untuk melihat aktivitas order yang masuk atas Product yang dibuat
 * Navigasi untuk melakukan Accept, Cancel, atau Submit Payment terdapat di sini
 */
public class StoreInvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_invoice);
        getSupportActionBar().hide();

        TextView productId = findViewById(R.id.invoiceStoreID);
        TextView status = findViewById(R.id.invoiceStoreStatus);
        ImageView accept = findViewById(R.id.storeSubmitButton);
        ImageView cancel = findViewById(R.id.storeCancelButton);
        MaterialButton submit = findViewById(R.id.submitItem);
        productId.setText("Product ID: " + CheckOrderActivity.getActivePayment().get("paymentId"));
        status.setText("Status: " + CheckOrderActivity.getActivePayment().get("status"));

        if (status.getText().toString().equals("Status: " + String.valueOf(Invoice.Status.ON_PROGRESS))) {
            accept.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        } else if (status.getText().toString().equals("Status: " + String.valueOf(Invoice.Status.WAITING_CONFIRMATION))) {
            accept.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        } else {
            accept.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
    }

    /**
     * Lakukan Request Submit
     * @param v
     */
    public void onSubmitClick(View v) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(StoreInvoiceActivity.this, "Order Dikirim!", Toast.LENGTH_LONG).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoreInvoiceActivity.this, "Gagal Mengirimkan Request ke Server!", Toast.LENGTH_LONG).show();
            }
        };

        StringRequest request = new SubmitRequest(Integer.parseInt(CheckOrderActivity.getActivePayment().get("paymentId")), "Null", listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(StoreInvoiceActivity.this);
        queue.add(request);
        startActivity(new Intent(StoreInvoiceActivity.this, CheckOrderActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    /**
     * Lakukan Request Accept
     * @param v
     */
    public void onAcceptButtonClick(View v) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(StoreInvoiceActivity.this, "Order diterima!", Toast.LENGTH_LONG).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoreInvoiceActivity.this, "Gagal Mengirimkan Request ke Server!", Toast.LENGTH_LONG).show();
            }
        };

        StringRequest request = new AcceptRequest(Integer.parseInt(CheckOrderActivity.getActivePayment().get("paymentId")), listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(StoreInvoiceActivity.this);
        queue.add(request);
        startActivity(new Intent(StoreInvoiceActivity.this, CheckOrderActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    /**
     * Lakukan Request Cancel
     * @param v
     */
    public void onCancelButtonClick(View v) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(StoreInvoiceActivity.this, "Order Batalkan!", Toast.LENGTH_LONG).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoreInvoiceActivity.this, "Gagal Mengirimkan Request ke Server!", Toast.LENGTH_LONG).show();
            }
        };

        StringRequest request = new CancelRequest(Integer.parseInt(CheckOrderActivity.getActivePayment().get("paymentId")), listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(StoreInvoiceActivity.this);
        queue.add(request);
        startActivity(new Intent(StoreInvoiceActivity.this, CheckOrderActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }
}