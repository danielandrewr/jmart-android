package com.josephusdanieljmartfa.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Post Method untuk Submit Payment dari Store
 */
public class SubmitRequest extends StringRequest {

    private static final String URL_SUBMIT = "http://10.0.2.2:8090/payment/%d/submit";
    private Map<String, String> params = new HashMap<>();

    /**
     * Constructor SubmitRequest
     * @param id
     * @param receipt
     * @param listener
     * @param errorListener
     */
    public SubmitRequest(int id, String receipt,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL_SUBMIT, id), listener, errorListener);
        params.put("receipt", receipt);
    }

    public Map<String, String> getParams() { return params; }
}
