package com.josephusdanieljmartfa.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateStoreRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8090/account/%d/registerStore";
    private static Map<String, String> params;

    public CreateStoreRequest(int id, String name, String address, String phoneNumber, Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL, id), listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    public Map<String, String> getParams() { return params; }
}
