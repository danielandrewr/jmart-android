package com.josephusdanieljmartfa.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Post Method untuk Melakukan Pembayaran Melalui PhoneTopUp
 */
public class PhoneTopUpRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8090/phoneTopUp/create";
    private static Map<String, String> params = new HashMap<>();

    /**
     * Constructor PhoneTopUpRequest
     * @param buyerId
     * @param productId
     * @param phoneNumber
     * @param listener
     * @param errorListener
     */
    public PhoneTopUpRequest(int buyerId, int productId, String phoneNumber, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params.put("buyerId", String.valueOf(buyerId));
        params.put("productId", String.valueOf(productId));
        params.put("phoneNumber", phoneNumber);
    }

    public Map<String, String> getParams() { return params; }
}
