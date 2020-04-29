package com.gennari.scaldofragno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.material.snackbar.Snackbar;

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

        float radius = 4f;

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
                requestHandler.getStatoCaldaia(true);
                requestHandler.getTempIn();
            }
        });

        sCaldaia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Attenzione");
                if(sCaldaia.isChecked())
                    dialog.setMessage("Si desidera accendere la caldiaia?");
                else
                    dialog.setMessage("Si desidera spegnere la caldaia?");
                dialog.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sCaldaia.setChecked(!sCaldaia.isChecked());
                        dialog.dismiss();
                    }
                });

                dialog.setPositiveButton(sCaldaia.isChecked() ? "Accendi" : "Spegni", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refresh.setRefreshing(true);
                        sCaldaia.setClickable(false);
                        if(sCaldaia.isChecked())
                            requestHandler.setStatoCaldaia("Accesa");
                        else
                            requestHandler.setStatoCaldaia("Spenta");
                    }
                });

                dialog.show();
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
        autoRefresh(true);
    }

    public void autoRefresh(final boolean refreshing){
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
                requestHandler.getStatoCaldaia(refreshing);
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

    public void ShowSnackbar(){
        Snackbar sb = Snackbar.make(findViewById(R.id.coordinator), "Caldaia " + requestHandler.getStatoCaldaiaString(), Snackbar.LENGTH_LONG);
        sb.getView().setBackgroundColor(getColor(R.color.colorPrimaryDark));
        sb.show();
    }

}
