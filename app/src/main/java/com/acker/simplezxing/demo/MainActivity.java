package com.acker.simplezxing.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.DrmInitData;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;;

import com.acker.simplezxing.activity.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class MainActivity extends AppCompatActivity {
    private static final int istrue = 1;
    private static final int REQ_CODE_PERMISSION = 0x1111;
    //    private TextView tvResult;
    private ArrayList<Group> groups;
    private ArrayList<ArrayList<Truck>> item;
    private ArrayList<Truck> lData = null;

    private String NameSpace = "http://tempuri.org/";
    private String MethodName = "linkdata";
    private String cargoJson = "cargoJson";
    private String truckJson = "truckJson";
    private String userinfo = "userinfo";
    private Context mContext;
//    private String url = "http://2108776x3j.imwork.net/WebService.asmx";
    private String url = "http://192.168.43.169/WebService.asmx";
    private String soapAction = NameSpace + MethodName;
    private String soapAction1 = NameSpace + cargoJson;
    private String soapAction2 = NameSpace + truckJson;
    private String soapAction3 = NameSpace +userinfo;

    private Truck allinfo;
    private DrawerLayout drawerLayout;
    private Button reCargo;
    private Button reTruck;
    private TextView tv;
    private ExpandableListView expandableListView;
    private String scanresult;
    private Button button;
    private EditText editText;
    private String dataname;
    private static String payResult;
    private EditText editTextpwd;
    private Button buttonpay;
    private String name;
    private User downuserinfo;
    private TextView usedname;
    private TextView headtextview;
    private String  resolverUser;
    private MyBaseExpandableListAdapter myBaseExpandableListAdapter;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case istrue:
                    reset();
                    success();

                    break;
                case 0:
                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    fullexpand();
                default:
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tvResult = (TextView) findViewById(R.id.tv_result);
//        Button btn = (Button) findViewById(R.id.btn_sm);
        final Intent intent = getIntent();
        dataname = intent.getStringExtra("name");
        groups = new ArrayList<>();
        item = new ArrayList<ArrayList<Truck>>();
        groups.add(new Group("货源信息"));
        groups.add(new Group("车源信息"));
        mContext = MainActivity.this;
        expandableListView = findViewById(R.id.expend_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headtextview = findViewById(R.id.mail);
//        headtextview.setText(dataname);

        usedname = findViewById(R.id.username);
//        usedname.setText(downuserinfo.getUsername());
        ActivityCollector.addActivity(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        if(headtextview==null)
        Log.d("main33",     "sdfsfd");
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                getInfo(dataname);
                Log.e("Mainwe" , dataname);
            }
        });
        thread1.start();
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
//第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;


            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               allinfo= item.get(groupPosition).get(childPosition);

               String toinfo=allinfo.getAllInfo(groups.get(groupPosition).getgName());
               Infomation.actionStart(MainActivity.this,toinfo,"1",allinfo.getTel());
                Log.d("Ao", allinfo.getTel());
                return false;
            }
        });

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_threeline_fill);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case   R.id.info:
                        Infomation.actionStart(MainActivity.this,resolverUser,"8","");
                        break;
                    case  R.id.about:
                        Aboutour.actionStart(MainActivity.this);
                        break;
                    case R.id.exit:
                        ActivityCollector.finishAll();
                        break;
                    default:
                        break;

                }



                return true;
            }
        });
        reCargo = findViewById(R.id.cargo);
        reTruck = findViewById(R.id.truck);
        reCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseInfo.actionStart(MainActivity.this, dataname, reCargo.getText().toString());

            }
        });
        reTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseInfo.actionStart(MainActivity.this, dataname, reTruck.getText().toString());
            }
        });




        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ws(soapAction1, cargoJson);

                item = new ArrayList<ArrayList<Truck>>();
                item.add(lData);
                ws(soapAction2, truckJson);
                item.add(lData);
                Message message = new Message();
                if (lData != null) {
                    message.what = 2;
                    handler.sendMessage(message);
                } else {
                    message.what = 0;
                    handler.sendMessage(message);
                }

            }
        });
        thread.start();


    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(this, "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        scanresult = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);  //or do sth
                        FingerprintDialogFragment fingerprintDialogFragment = new FingerprintDialogFragment();
                        fingerprintDialogFragment.show(getSupportFragmentManager(), "ss");


                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
//                            tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }
    }

    public String ws(String soapAction, String methodName) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, methodName);

            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致


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
            Log.i("data", "用truck服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if (envelope.getResponse() != null) {
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
                Gson gson = new Gson();
                Log.d("Main123", result);
                lData = gson.fromJson(result, new TypeToken<List<Truck>>() {
                }.getType());
            }

        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    private String showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(MainActivity.this);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setHint("请输入支付密码");
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("pay").setView(editText);

        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    final String pew = editText.getText().toString();

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                payResult = new Conn().wspay(dataname, pew, scanresult);
                                Toast.makeText(MainActivity.this, dataname + scanresult, Toast.LENGTH_LONG).show();
                                Log.d("pew", pew);
                                Log.d("payre", payResult);
                            }
                        });
                        thread.start();

                        if (payResult.equals("true")) {
                            Toast.makeText(MainActivity.this, "充值成功", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(MainActivity.this, "充值失败", Toast.LENGTH_LONG).show();
                    }
                }).show();
        return editText.getText().toString();
    }

    public void success() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.scan);
        normalDialog.setTitle("pay");
        normalDialog.setMessage("充值成功");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void reset() {
        editText.setText("");
//        tvResult.setText("");
    }

    public String getName() {
        return dataname;
    }

    public String ws(String name) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace, MethodName);

            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("username", name);
            request.addProperty("money", 20);

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
            Log.i("Main", "用WebService服务成功");
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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:

        }
        return true;
    }

    private void fullexpand() {
        myBaseExpandableListAdapter = new MyBaseExpandableListAdapter(groups, item, mContext);
        expandableListView.setAdapter(myBaseExpandableListAdapter);
    }


    public String getInfo(String username) {
        String result = "";
        try {
            //step1 指定WebService的命名空间和调用的方法名
            SoapObject request = new SoapObject(NameSpace,userinfo );

            //step2 设置调用方法的参数值,这里的参数名称最好和WebService一致
            request.addProperty("username", username);
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
            ht.call(soapAction3, envelope);
            Log.i("Main", "用WebService服务成功");
            //step6 使用getResponse方法获得WebService方法的返回结果
            if (envelope.getResponse() != null) {
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                result = response.toString();
                Gson gson=new Gson();
                downuserinfo = gson.fromJson(result, User.class);
                resolverUser=downuserinfo.getall();
                Log.e("Mainwe" ,resolverUser+result +dataname);
            }

        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}