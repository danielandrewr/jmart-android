package com.josephusdanieljmartfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.josephusdanieljmartfa.model.Payment;
import com.josephusdanieljmartfa.request.RequestFactory;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity yang untuk menampilkan list order pada sebuah store
 * Parameter utama: ProductID yang akan dipassing antar payment ke account
 */
public class CheckOrderActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    public static Map<String, String> activePayment = new HashMap<>();
    private static ArrayList<Integer> idGetter = new ArrayList<>();
    public static ArrayList<Payment> invoiceStore = new ArrayList<>();

    /**
     * Inisialisasi Komponen onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        getSupportActionBar().hide();

        /**
         * Panggil Get Request pada setiap kali activity dimulai
         * Array @invoiceStore dan @idGetter akan dibersihkan setiap kali activity dijalankan
         */
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    invoiceStore.clear();
                    idGetter.clear();
                    JSONArray jArray = new JSONArray(response);
                    Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                    invoiceStore = gson.fromJson(String.valueOf(jArray), type);
                    Toast.makeText(CheckOrderActivity.this, "Berhasil Memuat Order!", Toast.LENGTH_LONG).show();
                    for (Payment payment : invoiceStore) {
                        idGetter.add(payment.id);
                    }
                    System.out.println(jArray);
                    System.out.println(invoiceStore);
                    System.out.println(idGetter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOrderActivity.this, "Gagal memuat Invoice!", Toast.LENGTH_LONG).show();;
            }
        };

        StringRequest request = RequestFactory.getStorePayments(
                LoginActivity.getLoggedAccount().id,
                listener,
                errorListener);
        RequestQueue queue = Volley.newRequestQueue(CheckOrderActivity.this);
        queue.add(request);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                CheckOrderActivity.this,
                android.R.layout.simple_list_item_1,
                idGetter
        );

        /**
         *  Adapter agar listview memiliki nilai Integer @paymendId
         */
        ListView listView = findViewById(R.id.orderView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activePayment.clear();
                Payment selectedPayment = invoiceStore.get(position);
                try {
                    if (selectedPayment != null) {

                        activePayment.put("paymentId", String.valueOf(selectedPayment.id));
                        activePayment.put("status", String.valueOf(selectedPayment.history.get(selectedPayment.history.size()-1).status));

                        startActivity(new Intent(CheckOrderActivity.this, StoreInvoiceActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * onClickEvent untuk Icon Home
     * Kembali ke AboutMe navigation
     * @param view
     */
    public void onHomeClick(View view) {
        startActivity(new Intent(CheckOrderActivity.this, AboutMe.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    public static Map<String, String> getActivePayment() { return activePayment; }
}