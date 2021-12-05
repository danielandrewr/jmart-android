package com.josephusdanieljmartfa.request;

import android.location.GnssAntennaInfo;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;

public class RequestFactory {

    private static final String URL_FORMAT_ID = "http://10.0.2.2:8090/%s/%d";
    private static final String URL_FORMAT_PAGE = "http://10.0.2.2:8090/%s/page";
    private static final String URL_FORMAT_PRODUCT = "http://10.0.2.2:8090/%d/%d/%s/%s/%s/%s/%s";

    public static StringRequest getById(
            String parentURI,
            int id,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    public static StringRequest getPage (
            String parentURI,
            int page,
            int pageSize,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    public static StringRequest getProduct(
            int page,
            int pageSize,
            String name,
            String minPrice,
            String maxPrice,
            String category,
            String conditionUsed,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        String url = String.format(URL_FORMAT_PRODUCT, page, pageSize, name,
                minPrice, maxPrice, category, conditionUsed);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}
