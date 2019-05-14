package com.acker.simplezxing.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Aboutour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutour);
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Aboutour.class);

        context.startActivity(intent);
    }
}
