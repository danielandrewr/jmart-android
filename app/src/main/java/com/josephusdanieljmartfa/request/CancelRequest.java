package com.josephusdanieljmartfa.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request Method untuk Cancel Payment jika Payment sudah dibuat
 */
public class CancelRequest extends StringRequest {

    private static final String URL_CANCEL = "http://10.0.2.2:8090/payment/%d/cancel";

    /**
     * Constructor CancelRequest
     * @param productId
     * @param listener
     * @param errorListener
     */
    public CancelRequest(int productId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL_CANCEL, productId), listener, errorListener);
    }
}
