package com.josephusdanieljmartfa.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request Method untuk Accept Payment jika Payment sudah dibuat
 */
public class AcceptRequest extends StringRequest {

    private static final String URL_ACCEPT = "http://10.0.2.2:8090/payment/%d/accept";

    /**
     * Constructor AcceptRequest
     * @param paymentId
     * @param listener
     * @param errorListener
     */
    public AcceptRequest(int paymentId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL_ACCEPT, paymentId), listener, errorListener);
    }

}
