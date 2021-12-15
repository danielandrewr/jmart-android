package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.josephusdanieljmartfa.Interface.LoadInterface;
import com.josephusdanieljmartfa.adapter.PaymentItemAdapter;
import com.josephusdanieljmartfa.model.Payment;
import com.josephusdanieljmartfa.model.Product;
import com.josephusdanieljmartfa.request.PaymentRequest;
import com.josephusdanieljmartfa.request.RequestFactory;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InvoiceHistory extends AppCompatActivity {

    private List<Payment> payments = new ArrayList<>();
    private Gson gson = new Gson();
    private PaymentItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);

        RecyclerView recyclerView = findViewById(R.id.historyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentItemAdapter(recyclerView, this, payments);
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
                    }, 5000);
                } else {
                    Toast.makeText(InvoiceHistory.this, "History Loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                    payments = gson.fromJson(String.valueOf(jArray), type);
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
    }
}