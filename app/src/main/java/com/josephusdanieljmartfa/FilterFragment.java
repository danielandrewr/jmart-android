package com.josephusdanieljmartfa;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.josephusdanieljmartfa.model.Account;
import com.josephusdanieljmartfa.model.Product;
import com.josephusdanieljmartfa.request.RequestFactory;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment Filter
 * Required to show additional information that can be filtered
 */
public class FilterFragment extends Fragment {

    private ColorStateList gray = ColorStateList.valueOf(Color.parseColor("#B3B3B3"));
    public static List<Product> products = new ArrayList<>();
    public static List<String> productNames = new ArrayList<>();

    private Gson gson = new Gson();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        EditText name = v.findViewById(R.id.filterName);
        EditText minPrice = v.findViewById(R.id.filterLowest);
        EditText maxPrice = v.findViewById(R.id.filterHighest);
        CheckBox checkNew = v.findViewById(R.id.checkNew);
        CheckBox checkUsed = v.findViewById(R.id.checkUsed);
        Spinner category = v.findViewById(R.id.spinnerCategory);
        Button applyFilter = v.findViewById(R.id.applyFilter);
        Button cancelFilter = v.findViewById(R.id.cancelFilter);

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!name.getText().toString().isEmpty()) && (!minPrice.getText().toString().isEmpty()) &&
                        (!maxPrice.getText().toString().isEmpty()) && (checkNew.isChecked() || checkUsed.isChecked()) &&
                        (!category.getSelectedItem().toString().isEmpty())) {

                    productNames.clear();
                    products.clear();

                    boolean used = !checkNew.isChecked();
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jArray = new JSONArray(response);
                                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                                products = gson.fromJson(String.valueOf(jArray), type);
                                for (Product prod : products) {
                                    productNames.add(prod.name.toString());
                                }
                                Toast.makeText(getActivity(), "Filter berhasil!", Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Filter Gagal!", Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Error Terjadi!", Toast.LENGTH_LONG).show();
                        }
                    };
                    StringRequest request = RequestFactory.getProduct(1, 5,
                            LoginActivity.getLoggedAccount().id,
                            name.getText().toString(),
                            minPrice.getText().toString(),
                            maxPrice.getText().toString(),
                            category.getSelectedItem().toString(),
                            String.valueOf(used),
                            listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(request);
                } else {
                    Toast.makeText(getActivity(), "Input Filter Tidak Boleh Kosong!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                minPrice.setText("");
                maxPrice.setText("");
                checkNew.setChecked(false);
                checkUsed.setChecked(false);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        EditText filterTextName = getView().findViewById(R.id.filterName);
        TextView filterLabelNama = getView().findViewById(R.id.filterNameLabel);
        filterTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelNama.setTextColor(getResources().getColor(R.color.themeColor));
                    filterTextName.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB86FC")));
                    filterLabelNama.setHint("Nama Product");
                } else {
                    filterLabelNama.setHint("");
                    filterLabelNama.setTextColor(getResources().getColor(R.color.gray));
                    filterTextName.setBackgroundTintList(gray);
                }
            }
        });

        EditText filterTextLowest = getView().findViewById(R.id.filterLowest);
        TextView filterLabelLowest = getView().findViewById(R.id.filterLowestLabel);
        filterTextLowest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelLowest.setTextColor(getResources().getColor(R.color.themeColor));
                    filterTextLowest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB86FC")));
                    filterLabelLowest.setHint("Ex: 1000");
                } else {
                    filterLabelLowest.setHint("");
                    filterLabelLowest.setTextColor(getResources().getColor(R.color.gray));
                    filterTextLowest.setBackgroundTintList(gray);
                }
            }
        });

        EditText filterTextHighest = getView().findViewById(R.id.filterHighest);
        TextView filterLabelHighest = getView().findViewById(R.id.filterHighestLabel);
        filterTextHighest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelHighest.setTextColor(getResources().getColor(R.color.themeColor));
                    filterTextHighest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB86FC")));
                    filterLabelHighest.setHint("Ex: 1000");
                } else {
                    filterLabelHighest.setHint("");
                    filterLabelHighest.setTextColor(getResources().getColor(R.color.gray));
                    filterTextHighest.setBackgroundTintList(gray);
                }
            }
        });
    }

    public static List<String> getProductNames() {
        return productNames;
    }

    public static List<Product> getProduct() { return products; }
}