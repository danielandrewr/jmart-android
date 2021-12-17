package com.josephusdanieljmartfa.request;

import android.location.GnssAntennaInfo;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestFactory {

//    private static final String URL_FORMAT_ID = "http://10.0.2.2:8090/%s/%d";
//    private static final String URL_FORMAT_PAGE = "http://10.0.2.2:8090/%s/page";
    private static final String URL_FORMAT_PRODUCT = "http://10.0.2.2:8090/product/getFiltered?page=%s&pageSize=%s&accountId=%d&search=%s&minPrice=%s&maxPrice=%s&category=%s&conditionUsed=%s";
    private static final String URL_FORMAT_PAYMENT = "http://10.0.2.2:8090/payment/getPayments?accountId=%d";
    private static final String URL_FORMAT_PRODUCTBYID = "http://10.0.2.2:8090/product/getProductById?productId=%d";
    private static final String URL_FORMAT_PRODUCTBYNAME = "http://10.0.2.2:8090/product/getProductById?search=%s";
    private static final String URL_FORMAT_STOREPAYMENT = "http://10.0.2.2:8090/payment/getStorePayments?accountId=%d";

//    public static StringRequest getById(
//            String parentURI,
//            int id,
//            Response.Listener<String> listener,
//            Response.ErrorListener errorListener
//    ) {
//        String url = String.format(URL_FORMAT_ID, parentURI, id);
//        return new StringRequest(Request.Method.GET, url, listener, errorListener);
//    }

//    public static StringRequest getPage (
//            String parentURI,
//            int page,
//            int pageSize,
//            Response.Listener<String> listener,
//            Response.ErrorListener errorListener
//    ) {
//        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
//        return new StringRequest(Request.Method.GET, url, listener, errorListener);
//    }

    /**
     * Get Method yang mengembalikan List of Filtered Products
     * @param page
     * @param pageSize
     * @param id
     * @param name
     * @param minPrice
     * @param maxPrice
     * @param category
     * @param conditionUsed
     * @param listener
     * @param errorListener
     * @return
     */
    public static StringRequest getProduct(
            int page,
            int pageSize,
            int id,
            String name,
            String minPrice,
            String maxPrice,
            String category,
            String conditionUsed,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        String url = String.format(URL_FORMAT_PRODUCT, page, pageSize, id, name,
                minPrice, maxPrice, category, conditionUsed);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     * Get Method yang Mengembalikan List of Payments
     * @param accountId
     * @param listener
     * @param errorListener
     * @return
     */
    public static StringRequest getPayments(
            int accountId,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener) {
        String url = String.format(URL_FORMAT_PAYMENT, accountId);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

//    public static StringRequest getProductById(
//            int productId,
//            Response.Listener<String> listener,
//            Response.ErrorListener errorListener
//    ) {
//        String url = String.format(URL_FORMAT_PRODUCTBYID, productId);
//        return new StringRequest(Request.Method.GET, url, listener, errorListener);
//    }
//
//    public static StringRequest getProductByName(
//        String search,
//        Response.Listener<String> listener,
//        Response.ErrorListener errorListener
//    ) {
//        String url = String.format(URL_FORMAT_PRODUCTBYID, search);
//        return new StringRequest(Request.Method.GET, url, listener, errorListener);
//    }

    /**
     * Get Method yang mengembalikan List of Payments pada Store
     * @param accountId
     * @param listener
     * @param errorListener
     * @return
     */
    public static StringRequest getStorePayments(
            int accountId,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ) {
        String url = String.format(URL_FORMAT_STOREPAYMENT, accountId);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}
