package com.gennari.scaldofragno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestHandler = new RequestHandler(this, (TextView) findViewById(R.id.txtStatus), (TextView) findViewById(R.id.txtTempIn));
        requestHandler.getStatoCaldaia();
        requestHandler.getTempIn();
    }
}
