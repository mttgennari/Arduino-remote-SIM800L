package com.gennari.scaldofragno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {

    private RequestHandler requestHandler;
    private Switch sCaldaia;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sCaldaia = findViewById(R.id.switchCaldaia);
        refresh = findViewById(R.id.refresh);
        BlurView blurView = findViewById(R.id.blurView);

        float radius = 25f;

        View decorView = getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);

        requestHandler = new RequestHandler(this, (TextView) findViewById(R.id.txtStatus),
                (TextView) findViewById(R.id.txtTempIn),
                (TextView) findViewById(R.id.txtLastRec),
                sCaldaia, refresh, this);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sCaldaia.setClickable(false);
                requestHandler.getStatoCaldaia();
                requestHandler.getTempIn();
            }
        });

        sCaldaia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setRefreshing(true);
                sCaldaia.setClickable(false);
                if(sCaldaia.isChecked())
                    requestHandler.setStatoCaldaia("Accesa");
                else
                    requestHandler.setStatoCaldaia("Spenta");
            }
        });

        Button btStorico = findViewById(R.id.btStorico);
        btStorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStorico();
            }
        });
        sCaldaia.setClickable(true);
        autoRefresh();
    }

    public void autoRefresh(){
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
                requestHandler.getStatoCaldaia();
                requestHandler.getTempIn();
            }
        });
    }

    private void showStorico(){
        String url = "http://serverteknel.ddns.net:3000/d/M5L7K3qWk/fragno?orgId=1&refresh=5s&from=now-12h&to=now";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getColor(R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
    }

}
