package com.gennari.scaldofragno;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RequestHandler {
    private String statoCaldaia = "Sconosciuto";
    private String ultimaRegistazione;
    private String tempIn;
    private Context context;

    private TextView txtStatus, txtTempIn, txtLastRec;

    private RequestQueue queue;

    public RequestHandler(Context context, TextView txtStatus, TextView txtTempIn, TextView txtLastRec){
        queue = Volley.newRequestQueue(context);
        this.context = context;
        this.txtStatus = txtStatus;
        this.txtTempIn = txtTempIn;
        this.txtLastRec =txtLastRec;
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
                tempIn = response.split("&")[1];
                ultimaRegistazione = response.split("&")[0];
                txtTempIn.setText(tempIn);


                Calendar d = Calendar.getInstance();
                d.setTimeInMillis(Long.parseLong(ultimaRegistazione)*1000);
                SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy HH:mm");
                txtLastRec.setText(sdf.format(d.getTime()));
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
