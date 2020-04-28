package com.gennari.scaldofragno;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestHandler {
    private String statoCaldaia = "Sconosciuto";
    private String ultimaRegistazione;
    private String tempIn;
    private Context context;

    private TextView txtStatus, txtTempIn;

    private RequestQueue queue;

    public RequestHandler(Context context, TextView txtStatus, TextView txtTempIn){
        queue = Volley.newRequestQueue(context);
        this.context = context;
        this.txtStatus = txtStatus;
        this.txtTempIn = txtTempIn;
    }

    public void getStatoCaldaia(){
        String url = "http://serverteknel.ddns.net:88/FragnoAPI/get_status.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                statoCaldaia = response;
                txtStatus.setText(statoCaldaia);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statoCaldaia = "ErroreConnessione";
            }
        });

        queue.add(stringRequest);
    }

    public void getTempIn(){
        String url = "http://serverteknel.ddns.net:88/FragnoAPI/get_last_temperature.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tempIn = response;
                txtTempIn.setText(tempIn);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statoCaldaia = "ErroreConnessione";
            }
        });

        queue.add(stringRequest);
    }

}
