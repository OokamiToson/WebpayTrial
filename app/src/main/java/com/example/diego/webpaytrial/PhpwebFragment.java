package com.example.diego.webpaytrial;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class PhpwebFragment extends Fragment {

    //private TextView text;
    private WebView webView;
    private int amount;

    public PhpwebFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PhpwebFragment newInstance() {
        PhpwebFragment fragment = new PhpwebFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phpweb, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.amount = getArguments().getInt("amount");
        initWebpayTransaction();

    }

    /**
    THIS!
    */
    private void initWebpayTransaction(){
        String jsonResponse = "";
        String postData = "";
        try{
            postData = "amount="+URLEncoder.encode(String.valueOf(this.amount),"UTF-8");
        }catch (Exception ex){
            //D:
        }
        //this.text = this.getActivity().findViewById(R.id.text_webpay);
        this.webView = this.getActivity().findViewById(R.id.web_view);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.postUrl("https://daldana9.000webhostapp.com/init.php", postData.getBytes());
        //webView.loadUrl("https://daldana9.000webhostapp.com/init.php");
        //new WebTask().execute();
    }

    //ESTO NO
    private class WebTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("PROBANDO","Server respondio!");
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("message").equals("ok")) {
                                String url = res.getString("url").replaceAll("\\/","/");
                                String data= "token_ws="+res.getString("token");
                                webView.setWebViewClient(new MyWebViewClient());
                                webView.getSettings().setJavaScriptEnabled(true);
                                //webView.postUrl(url, Base64.encode(data.getBytes("UTF-8"), Base64.DEFAULT));
                                webView.loadUrl("http://www.google.cl");
                                Log.d("PROBANDO","Llamando a la url..."+url);
                                //text.setText(res.toString());
                            } else {
                                Log.d("PROBANDO", "Server no respondio ok");
                            }

                        } catch (Exception e) {
                            Log.e("PROBANDO", e.getMessage());
                            //text.setText("me cai :c");
                        }
                    }
                };

                WebpayRequest request = new WebpayRequest(String.valueOf(amount), response);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(request);
                Log.d("PROBANDO", "Request enviada");

            } catch (Exception e) {
                Log.d("PROBANDO", "me cai :c");
                //text.setText("no funciona :c");
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
    THIS TOO
    */
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /**
        FALTA UN METODO AQUI
        */
    }
}


