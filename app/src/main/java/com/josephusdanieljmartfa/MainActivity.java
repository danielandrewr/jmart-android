package com.josephusdanieljmartfa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.josephusdanieljmartfa.model.Product;
import com.josephusdanieljmartfa.request.RequestFactory;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0: {
                        tab.setText("Products");
                        tab.setIcon(getResources().getDrawable(R.drawable.ic_baseline_backpack_24));
                        break;
                    }
                    case 1: {
                        tab.setText("Filter");
                        tab.setIcon(getResources().getDrawable(R.drawable.ic_baseline_filter_alt_24));
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        if (LoginActivity.getLoggedAccount().store == null) {
            menu.findItem(R.id.add).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search:
                // go back here
                break;
            case R.id.add:
                Intent createProductIntent = new Intent(this, CreateProductActivity.class);
                this.startActivity(createProductIntent);
                break;
            case R.id.profile:
                Intent profileIntent = new Intent(this, AboutMe.class);
                this.startActivity(profileIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

//    public void populate(int page) {
//        EditText name = getView().findViewById(R.id.filterName);
//        EditText minPrice = getView().findViewById(R.id.filterLowest);
//        EditText maxPrice = getView().findViewById(R.id.filterHighest);
//        CheckBox checkNew = getView().findViewById(R.id.checkNew);
//        Spinner category = getView().findViewById(R.id.spinner);
//
//        boolean used = !checkNew.isChecked();
//        Response.Listener<String> listener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jArray = new JSONArray(response);
//                    Type type = new TypeToken<ArrayList<Product>>(){}.getType();
//                    products = gson.fromJson(String.valueOf(jArray), type);
//                    for (Product prod : products) {
//                        productNames.add(prod.name.toString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        try {
//            Response.ErrorListener errorListener = new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, "Error Terjadi!", Toast.LENGTH_LONG).show();
//                }
//            };
//            StringRequest request = RequestFactory.getProduct(page, 5, name.getText().toString(),
//                    minPrice.getText().toString(), maxPrice.getText().toString(), String.valueOf(used),
//                    category.getSelectedItem().toString(),
//                    listener, errorListener);
//            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//            queue.add(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "Error Terjadi!", Toast.LENGTH_LONG).show();
//        }
//    }
}