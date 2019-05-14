package com.acker.simplezxing.demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Infomation extends AppCompatActivity {
    private String information;
    private EditText editText;
    private Button call;
    private String tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        call = findViewById(R.id.call);

        Intent intent = getIntent();
        information = intent.getStringExtra("username");
        tel=intent.getStringExtra("tel");

        if (intent.getStringExtra("releaseType").equals("8")) {

            call.setVisibility(View.INVISIBLE);
        }
        editText = findViewById(R.id.editText);
        editText.setText(information);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Infomation.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Infomation.this,new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else{



                    call("tel:"+tel);}
            }
        });

    }
    public static void actionStart(Context context, String data, String releaseType,String tel) {
        Intent intent = new Intent(context, Infomation.class);
        intent.putExtra("username",data);
        intent.putExtra("releaseType", releaseType);
        intent.putExtra("tel",tel);
        context.startActivity(intent);
    }

    private void call(String num) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(num));
            Log.e("Infom", num );
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
