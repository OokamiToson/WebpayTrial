package com.example.diego.webpaytrial;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.configuration.Configuration;

public class WebpayFragment extends Fragment {

    public WebpayFragment() {
        // Required empty public constructor
    }


    public static WebpayFragment newInstance() {
        WebpayFragment fragment = new WebpayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_webpay, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        TextView text = getActivity().findViewById(R.id.webpaytext);
        int amount = getArguments().getInt("amount");
        text.setText("Bienvenido a webpay tu total por pagar es "+amount);
        */

        int amount = getArguments().getInt("amount");
        initWebpayTransaction(amount);
    }

    private void initWebpayTransaction(int amount) {
        AssetManager am = getActivity().getAssets();
        String privateKey = "";
        String publicKey = "";
        String webpayCert = "";

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(am.open("597020000541.key")));
            String line;
            while((line = bf.readLine()) != null){
                privateKey += line;
            }

            bf = new BufferedReader(new InputStreamReader(am.open("597020000541.crt")));
            while((line = bf.readLine()) != null){
                publicKey += line;
            }

            bf = new BufferedReader(new InputStreamReader(am.open("tbk.pem")));
            while((line = bf.readLine()) != null){
                webpayCert += line;
            }
        }
        catch (Exception ex) {}

        Configuration conf = new Configuration();
        conf.setCommerceCode("597020000541");
        conf.setEnvironment("INTEGRACION");
        conf.setPrivateKey(privateKey);
        conf.setPublicCert(publicKey);
        conf.setWebpayCert(webpayCert);

        Webpay webpay = new Webpay(conf);
        String idSession = "aj2h4kj2";
        String buyOrder = "160987676";
        String urlReturn = "";
        String urlFinal = "";

        TextView text = getActivity().findViewById(R.id.webpaytext);
        try {
            WsInitTransactionOutput initTransResult = webpay.getNormalTransaction().
                    initTransaction(amount, idSession, buyOrder, urlReturn, urlFinal );
            text.setText("Result: "+ initTransResult.toString());
        }
        catch (Exception ex) {
            text.setText("Me cai "+ex);
        }
    }
}
