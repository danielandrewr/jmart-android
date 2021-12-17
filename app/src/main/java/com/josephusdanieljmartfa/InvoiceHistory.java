package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.josephusdanieljmartfa.Interface.LoadInterface;
import com.josephusdanieljmartfa.Interface.PaymentItemInterface;
import com.josephusdanieljmartfa.adapter.PaymentItemAdapter;
import com.josephusdanieljmartfa.model.Payment;
import com.josephusdanieljmartfa.model.Product;
import com.josephusdanieljmartfa.request.PaymentRequest;
import com.josephusdanieljmartfa.request.RequestFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity yang menampilkan sejarah Invoice dari setiap Payment yang dilakukan
 */
public class InvoiceHistory extends AppCompatActivity {

    public static List<Payment> payments = new ArrayList<>();
    private Gson gson = new Gson();
    private PaymentItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);
        getSupportActionBar().hide();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    payments.clear();
                    JSONArray jArray = new JSONArray(response);
                    Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                    payments = gson.fromJson(String.valueOf(jArray), type);
                    System.out.print(payments);
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                    overridePendingTransition(0, 0);
                    Toast.makeText(InvoiceHistory.this, "Gagal Memuat History!", Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InvoiceHistory.this, "Gagal Membuat Request!", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest request = RequestFactory.getPayments(LoginActivity.getLoggedAccount().id, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(InvoiceHistory.this);
        queue.add(request);

        RecyclerView recyclerView = findViewById(R.id.historyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentItemAdapter(recyclerView, this, payments, new PaymentItemInterface() {
            @Override
            public void imageViewOnClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        adapter.setLoad(new LoadInterface() {
            @Override
            public void onLoad() {
                if (payments.size() <= 10) {
                    int lastPos = payments.size()-1;
                    payments.add(null);
                    adapter.notifyItemInserted(lastPos);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            payments.remove(lastPos);
                            adapter.notifyItemRemoved(lastPos);

                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    }, 2000);
                } else {
                    Toast.makeText(InvoiceHistory.this, "History Loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onInvoiceHistoryClick(View view) {
        startActivity(new Intent(InvoiceHistory.this, AboutMe.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    public static List<Payment> getPayment() {
        return payments;
    }

//    private List<Product> getProductName(List<Payment> payments) {
//        if (payments != null) {
//            for (Payment payment : payments) {
//                Response.Listener<String> listener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jArray = new JSONArray(response);
//                            if (jArray != null) {
//                                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
//                                productTemp = gson.fromJson(String.valueOf(jArray), type);
//                                productName.add(productTemp);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                Response.ErrorListener errorListener = new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                };
//
//                StringRequest productById = RequestFactory.getProductById(payment.productId, listener, errorListener);
//                RequestQueue queue = Volley.newRequestQueue(InvoiceHistory.this);
//                queue.add(productById);
//            }
//        }
//
//        return productName;
//    }
}