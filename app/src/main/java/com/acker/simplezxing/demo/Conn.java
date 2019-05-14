package com.acker.simplezxing.demo;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Conn {
    private String NameSpace = "http://tempuri.org/";
    private String MethodName = "logpay";
    private String MethodNamelog = "log";
    private String url = "http://2108776x3j.imwork.net/WebService.asmx";
    private String soapAction = NameSpace + MethodName;
    private String soapActionlog=NameSpace+MethodNamelog;

    public String wspay(String name,Object paypwd, Object money) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, MethodName);

            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("username", name);

            request.addProperty("paypwd",paypwd);
            request.addProperty("money", money);

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
//            Log.i("data", "用WebService服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if (envelope.getResponse() != null) {
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
            }
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }
    public String wslog(String name, Object pwd) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, MethodNamelog);

            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("username", name);
            request.addProperty("pwd", pwd);

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
            ht.call(soapActionlog, envelope);
            Log.i("data", "用登录WebService服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if (envelope.getResponse() != null) {
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
            }
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    public String ws(String name,String money,String url) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, MethodNamelog);

            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("username", name);
            request.addProperty("money",money);

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
            ht.call(soapActionlog, envelope);
            Log.i("data","用WebService服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if(envelope.getResponse()!=null){
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
            }

        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    }