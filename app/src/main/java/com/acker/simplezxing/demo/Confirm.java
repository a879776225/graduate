package com.acker.simplezxing.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Confirm extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String name;
    private String money;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        editText = (EditText) findViewById(R.id.editText3);
        button = (Button) findViewById(R.id.buttonpay);
        Intent intent = new Intent();

        name=intent.getStringExtra("name");
        money=intent.getStringExtra("money");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result=new Conn().wspay(name,editText.getText().toString(), money);
                        Log.e("re", result);
                    }
                });
                thread.start();
                if(result.equals("true"))
                    Toast.makeText(Confirm.this,"支付成功请刷新页面",Toast.LENGTH_LONG).show();


            }
        });
    }
}
