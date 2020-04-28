package com.gennari.scaldofragno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RequestHandler requestHandler;
    private Switch sCaldaia;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sCaldaia = findViewById(R.id.switchCaldaia);

        requestHandler = new RequestHandler(this, (TextView) findViewById(R.id.txtStatus),
                (TextView) findViewById(R.id.txtTempIn),
                (TextView) findViewById(R.id.txtLastRec),
                sCaldaia);
        requestHandler.getStatoCaldaia();
        requestHandler.getTempIn();

        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestHandler.getStatoCaldaia();
                requestHandler.getTempIn();
                refresh.setRefreshing(false);
            }
        });

        sCaldaia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    requestHandler.setStatoCaldaia("Accesa");
                    autoRefresh();
                    return;
                }else{
                    requestHandler.setStatoCaldaia("Spenta");
                    autoRefresh();
                    return;
                }
            }
        });

        Button btStorico = findViewById(R.id.btStorico);
        btStorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://serverteknel.ddns.net:3000/d/M5L7K3qWk/fragno?orgId=1&refresh=5s&from=now-12h&to=now";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getColor(R.color.colorPrimary));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });
    }

    private void autoRefresh(){
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                requestHandler.getStatoCaldaia();
                requestHandler.getTempIn();
                refresh.setRefreshing(false);
            }
        });
    }
}
