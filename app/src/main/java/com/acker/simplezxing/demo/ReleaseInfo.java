package com.acker.simplezxing.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

public class ReleaseInfo extends AppCompatActivity {
    public static final int SUCCESS =1;

    public  int truck=0;

    private String url = "http://2108776x3j.imwork.net/WebService.asmx";
    private String NameSpace = "http://tempuri.org/";
    private String MethodName = "releaseCargo";

    private String MethodName1 = "releaseTruck";
    private String username;

    private String soapAction = NameSpace + MethodName;
    private String soapAction1 = NameSpace + MethodName1;
    private Button reseted;
    private EditText name;
    private EditText weight;
    private EditText price;
    private EditText stardate;
    private EditText starlocation;
    private EditText destination;
    private EditText linkman;
    private EditText tel;
    private EditText vliad;
    private EditText bewrite;
    private Button   release;

    private TextView tvCargoname;
    private TextView tvvalid;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(ReleaseInfo.this, "发布成功", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(ReleaseInfo.this, "发布失败", Toast.LENGTH_LONG).show();

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_info);
        release = findViewById(R.id.rebu);

        name = findViewById(R.id.cargoname);
        weight = findViewById(R.id.weight);
        price = findViewById(R.id.price);
        stardate = findViewById(R.id.date);
        starlocation = findViewById(R.id.starlo);
        destination = findViewById(R.id.destination);
        linkman = findViewById(R.id.linkman);
        tel = findViewById(R.id.linktel);
        vliad = findViewById(R.id.vliaddate);
        bewrite = findViewById(R.id.bewrite);
        reseted = findViewById(R.id.reseted);
        tvCargoname = findViewById(R.id.tvcar);
        tvvalid = findViewById(R.id.tvvalid);
        Intent intent=getIntent();
        if(intent.getStringExtra("releaseType").equals("发布车源")){
            tvCargoname.setText("车辆类型");
            tvvalid.setText("车牌号");
            truck=1;
        }
        username=intent.getStringExtra("username");
        reseted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        name.setText("");
                      weight.setText("");
                       price.setText("");
                    stardate.setText("");
                starlocation.setText("");
                 destination.setText("");
                     linkman.setText("");
                         tel.setText("");
                       vliad.setText("");
                     bewrite.setText("");
            }
        });
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(truck!=1){
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int a=Integer.parseInt( price.getText().toString());
                        String re= release(soapAction, MethodName1, name.getText().toString(), weight.getText().toString(),
                                a,
                                stardate.getText().toString(), starlocation.getText().toString(), destination.getText().toString(),
                                linkman.getText().toString(), tel.getText().toString(), vliad.getText().toString(),
                                bewrite.getText().toString(),username);
                        Message message = new Message();
                        Log.e("Re", re);
                        if(re.equals("true")){

                            message.what=SUCCESS;
                            handler.sendMessage(message);}
                        else {

                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }
                });
                thread.start();}
                else {
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int a=Integer.parseInt( price.getText().toString());
                            String re= release2(soapAction1, MethodName, name.getText().toString(),vliad.getText().toString(), weight.getText().toString(),
                                    a,
                                    stardate.getText().toString(), starlocation.getText().toString(), destination.getText().toString(),
                                    linkman.getText().toString(), tel.getText().toString(),
                                    bewrite.getText().toString(),username);
                            Message message = new Message();
                            Log.e("Re", re);
                            if(re.equals("true")){

                                message.what=SUCCESS;
                                handler.sendMessage(message);}
                            else {

                                message.what = 0;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    thread.start();
                }


            }
        });
    }public static void actionStart(Context context, String data,String releaseType) {
        Intent intent = new Intent(context, ReleaseInfo.class);
        intent.putExtra("username",data);
        intent.putExtra("releaseType", releaseType);
        context.startActivity(intent);
    }
    public String release(String soapAction,String methodName,
                     String CargoName, String CargoWeight, int CargoPrice, String CargoDate,
                     String Start, String Whither, String Linkman, String Tel, String EffectDate,
                     String Bewrite, String Username) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, methodName);
            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("TruckType",CargoName);
            request.addProperty("TruckNumber",CargoWeight);
            request.addProperty("TruckWeight", CargoPrice);
            request.addProperty("TruckPrice", CargoDate);
            request.addProperty("TruckDate", Start);
            request.addProperty("Start", Whither);
            request.addProperty("Whither", Linkman);
            request.addProperty("Linkman", Tel);
            request.addProperty("Tel", EffectDate);
            request.addProperty("Bewrite", Bewrite);
            request.addProperty("username", Username);
            //step3 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            //设置是否调用的是dotNet下的WebService
            envelope.dotNet = true;
            envelope.bodyOut = request;
            envelope.setOutputSoapObject(request);
            //step4 创建HttpTransportSE对象
            HttpTransportSE ht = new HttpTransportSE(url);
            //step5 调用WebService
            ht.call(soapAction, envelope);
            Log.i("data","用truck服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if(envelope.getResponse()!=null){
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
                Log.d("Re", response.toString());
            }
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
    public String release2(String soapAction,String methodName,
                          String CargoName,String EffectDate, String CargoWeight, int CargoPrice, String CargoDate,
                          String Start, String Whither, String Linkman, String Tel,
                          String Bewrite, String Username) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, methodName);
            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("CargoName",CargoName);
            request.addProperty("CargoWeight",CargoWeight);
            request.addProperty("CargoPrice", CargoPrice);
            request.addProperty("CargoDate", CargoDate);
            request.addProperty("Start", Start);
            request.addProperty("Whither", Whither);
            request.addProperty("Linkman", Linkman);
            request.addProperty("Tel", Tel);
            request.addProperty("EffectDate", EffectDate);
            request.addProperty("Bewrite", Bewrite);
            request.addProperty("Username", Username);
            //step3 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            //设置是否调用的是dotNet下的WebService
            envelope.dotNet = true;
            envelope.bodyOut = request;
            envelope.setOutputSoapObject(request);
            //step4 创建HttpTransportSE对象
            HttpTransportSE ht = new HttpTransportSE(url);
            //step5 调用WebService
            ht.call(soapAction, envelope);
            Log.i("data","用truck服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if(envelope.getResponse()!=null){
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
                Log.d("Re", response.toString());
            }
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
