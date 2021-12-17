package com.josephusdanieljmartfa.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Post Method untuk Membuat Payment Baru
 */
public class PaymentRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8090/payment/create";
    private static Map<String, String> params = new HashMap<>();

    /**
     * Constructor PaymentRequest
     * @param buyerId
     * @param productId
     * @param productCount
     * @param shipmentAddress
     * @param shipmentPlan
     * @param listener
     * @param errorListener
     */
    public PaymentRequest(int buyerId, int productId, String productCount, String shipmentAddress, String shipmentPlan,
                          Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params.put("buyerId", String.valueOf(buyerId));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", productCount);
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan", shipmentPlan);
    }

    public Map<String, String> getParams() { return params; }
}
