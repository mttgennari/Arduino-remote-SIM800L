package com.gennari.scaldofragno;

import android.content.Context;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    private String statoCaldaia = "Sconosciuto";
    private String ultimaRegistazione;
    private String tempIn;
    private String errore;

    private Context context;
    private Switch sCaldaia;
    private SwipeRefreshLayout refresh;
    MainActivity main;
    private TextView txtStatus, txtTempIn, txtLastRec;

    private RequestQueue queue;

    public RequestHandler(Context context, TextView txtStatus, TextView txtTempIn, TextView txtLastRec, Switch sCaldaia, SwipeRefreshLayout refresh, MainActivity main){
        queue = Volley.newRequestQueue(context);
        this.context = context;
        this.txtStatus = txtStatus;
        this.txtTempIn = txtTempIn;
        this.txtLastRec =txtLastRec;
        this.sCaldaia = sCaldaia;
        this.refresh = refresh;
        this.main = main;
    }

    public void getStatoCaldaia(final boolean refreshing){
        String url = "http://serverteknel.ddns.net:88/FragnoAPI/get_status.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                statoCaldaia = response;
                txtStatus.setText(statoCaldaia);
                sCaldaia.setVisibility(View.VISIBLE);
                if(statoCaldaia.equals("Accesa"))
                    sCaldaia.setChecked(true);
                else
                    sCaldaia.setChecked(false);
                if(!refreshing)
                    main.ShowSnackbar();
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

                float tmp = Float.parseFloat(tempIn);
                tempIn = String.format("%.2f", tmp);
                tempIn = tempIn.replace(".", ",") + "°";

                ultimaRegistazione = response.split("&")[0];
                txtTempIn.setText(tempIn);

                Calendar d = Calendar.getInstance();
                d.setTimeInMillis(Long.parseLong(ultimaRegistazione)*1000);
                SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy HH:mm");

                txtLastRec.setText(sdf.format(d.getTime()));
                refresh.setRefreshing(false);
                sCaldaia.setClickable(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statoCaldaia = "ErroreConnessione";
                refresh.setRefreshing(false);
                sCaldaia.setClickable(true);
            }
        });

        queue.add(stringRequest);
    }

    public void setStatoCaldaia(final String status){
        String url = "http://serverteknel.ddns.net:88/FragnoAPI/insert_status.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0"))
                    response = errore;
                main.autoRefresh(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statoCaldaia = "ErroreConnessione";
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("status", status);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public String getStatoCaldaiaString(){
        return statoCaldaia;
    }

}
