package com.josephusdanieljmartfa;

import static com.josephusdanieljmartfa.ProductsFragment.getActiveProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.josephusdanieljmartfa.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }
}