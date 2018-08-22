package com.example.diego.webpaytrial;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WebpayRequest extends StringRequest {

    final private static String link = "https://daldana9.000webhostapp.com/init.php";

    private HashMap<String, String> parameters;

    public WebpayRequest(String amount, Response.Listener<String> listener) {
        super(Request.Method.POST, link, listener, null);
        this.parameters = new HashMap<>();
        this.parameters.put("amount", amount);
    }

    protected Map<String, String> getParams() {
        return this.parameters;
    }

}
