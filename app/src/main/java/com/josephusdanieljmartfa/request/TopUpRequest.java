package com.josephusdanieljmartfa.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TopUpRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8090/account/%d/topUp";
    private static Map<String, String> params = new HashMap<>();

    public TopUpRequest(int id, String balance, Response.Listener<String> listener,
                        Response.ErrorListener errorListener) {
        super(Request.Method.POST, String.format(URL, id), listener, errorListener);
        params.put("balance", balance);
    }

    public Map<String, String> getParams() { return params; }
}
