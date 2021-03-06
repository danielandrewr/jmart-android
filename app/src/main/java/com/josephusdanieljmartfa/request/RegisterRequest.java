package com.josephusdanieljmartfa.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8090/account/register";
    private final Map<String, String> params = new HashMap<>();

    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, listener, errorListener);
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
