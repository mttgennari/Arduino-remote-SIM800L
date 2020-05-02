package com.gennari.scaldofragno.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.gennari.scaldofragno.Adapter.ErrorAdapter;
import com.gennari.scaldofragno.R;
import com.gennari.scaldofragno.data.ErrorLog;
import com.gennari.scaldofragno.data.RequestHandler;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class ErrorActivity extends AppCompatActivity {

    private ErrorLog[] logs;
    private RequestHandler request;
    private ErrorAdapter adapter;
    private SwipeRefreshLayout errRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        Toolbar toolbar = findViewById(R.id.err_toolbar);
        errRefresh = findViewById(R.id.err_refresh);
        errRefresh.setRefreshing(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setBlur();

        logs = new ErrorLog[0];
        adapter = new ErrorAdapter(logs);

        RecyclerView listError = findViewById(R.id.list_error);
        listError.setHasFixedSize(true);
        listError.setLayoutManager(new LinearLayoutManager(this));
        listError.setAdapter(adapter);

        request = new RequestHandler(this, null, null, null, null, null, null);
        request.getErrors(adapter, errRefresh);

        errRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request.getErrors(adapter, errRefresh);
            }
        });

    }

    public void setBlur(){
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
    }
}
