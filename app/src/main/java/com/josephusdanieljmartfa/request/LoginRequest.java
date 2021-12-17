package com.josephusdanieljmartfa.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Post Method untuk Login
 */
public class LoginRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8090/account/login";
    private final Map<String, String> params = new HashMap<>();

    /**
     * Constructor LoginRequest
     * @param email
     * @param password
     * @param listener
     * @param errorListener
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener,
                        Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params.put("email", email);
        params.put("password", password);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
