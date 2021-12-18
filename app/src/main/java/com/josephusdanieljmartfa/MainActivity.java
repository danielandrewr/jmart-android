package com.josephusdanieljmartfa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.josephusdanieljmartfa.model.ProductCategory;
import com.josephusdanieljmartfa.request.RequestFactory;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity Awal untuk Tampilan Menu, Products, dan Filter
 */
public class MainActivity extends AppCompatActivity {

    // Login Session Requeired Values
    private static final String SHARED_PREFS = "shared_prefs";

    public static final String EMAIL_KEY = "email_key";

    public static final String PASSWORD_KEY = "password_key";

    private SharedPreferences sharedPreferences;

    private String email;

    /**
     * Shows the main activity that contains filtered products, menus, and navigation
     * Login session is saved and will load this page whenever the app closes and the user hasn't logged out
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(EMAIL_KEY, null);

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

    /**
     * Inflates menu activity in action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        if (LoginActivity.getLoggedAccount().store == null) {
            menu.findItem(R.id.add).setVisible(false);
        }
        return true;
    }

    /**
     * Menu Click Event
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search:
                // unused
                break;
            case R.id.add:
                Intent createProductIntent = new Intent(this, CreateProductActivity.class);
                this.startActivity(createProductIntent);
                break;
            case R.id.profile:
                Intent profileIntent = new Intent(this, AboutMe.class);
                this.startActivity(profileIntent);
                break;
            case R.id.logout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                this.startActivity(logoutIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}