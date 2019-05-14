package com.acker.simplezxing.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Star extends Activity {
    public static final int tonext=1;
    public static final int error=0;
    public static final int neterror=2;

    private EditText pwd;
    private EditText name;
    private Button button;
    private String reuslt;
    private EditText ip;
      Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case tonext:
                    Intent intent = new Intent(Star.this, MainActivity.class);
                    intent.putExtra("name", name.getText().toString().trim());
                    startActivity(intent);
                    break;
                case error:
                    Toast.makeText(Star.this, "用户名或密码错误"+reuslt, Toast.LENGTH_SHORT).show();
                    break;
                case neterror:
                    Toast.makeText(Star.this,"网络连接错误"+reuslt+"null", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        pwd = (EditText) findViewById(R.id.editTextpwd);
        name = (EditText) findViewById(R.id.editText2);
//        ip = (EditText) findViewById(R.id.editText4);
        name.requestFocus();

        button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String sname = name.getText().toString().trim();
                        String spwd = pwd.getText().toString().trim();
                        reuslt = new Conn().wslog(sname, spwd);

//                        Log.e("ss", "http://"+ip.getText().toString().trim()+"/WebService.asmx"+"\t"+reuslt);
                        Message message=new Message();

                        if(reuslt.equals("true")){
                            message.what=tonext;
                            handler.sendMessage(message);
                            Log.e("sss", "ssdfsfsfs");
                        }
                        else if (reuslt ==null) {
                            message.what=neterror;
                            handler.sendMessage(message
                            );
                        } else {

                            message.what = error;
                            handler.sendMessage(message);

                        }

                    }


                });
                thread.start();
            }
        });
    }
}
