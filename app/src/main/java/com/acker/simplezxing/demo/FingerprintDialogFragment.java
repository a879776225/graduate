package com.acker.simplezxing.demo;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

import javax.crypto.Cipher;
import javax.crypto.MacSpi;

import static android.content.ContentValues.TAG;

public class FingerprintDialogFragment extends DialogFragment {

    private FingerprintManager fingerprintManager;
    private static final int istrue=1;
    private CancellationSignal mCancellationSignal;

    private Cipher mCipher;

    private MainActivity mActivity;

    private TextView errorMsg;
    String  payresult;
    public static final int UPDATE_TEXT=1;
    /**
     * 标识是否是用户主动取消的认证。
     */
    private boolean isSelfCancelled;
    private Handler handler=new Handler(){

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_TEXT:
                Toast.makeText(mActivity, "完成支付"+payresult, Toast.LENGTH_LONG).show();
                dismiss();
                mActivity.success();
                break;
            default:
                Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    };

    public void setCipher(Cipher cipher) {
        mCipher = cipher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mActivity = (LoginActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        }
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fingerprint_dialog, container, false);
        errorMsg = v.findViewById(R.id.error_msg);
        final TextView pwdpay = v.findViewById(R.id.textView2);
        TextView cancel = v.findViewById(R.id.cancel);
        mActivity=(MainActivity)getActivity();
        pwdpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                pay();
//                transaction.replace(R.id.activity_main, new Pwdpay());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                stopListening();
            }
        });
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        // 开始指纹认证监听
        startListening(mCipher);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止指纹认证监听
        stopListening();
    }

    private void startListening(Cipher cipher) {
        isSelfCancelled = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mCancellationSignal = new CancellationSignal();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    if (!isSelfCancelled) {
                        errorMsg.setText(errString);
                        if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                            Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    errorMsg.setText(helpString);
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    mActivity= (MainActivity)getActivity();

                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                          payresult=mActivity.ws(mActivity.getName());
                            Log.d("Finger", payresult);
                          if (payresult.equals("true")) {
                              Message message = new Message();
                              message.what = 1;
                              handler.sendMessage(message);
                          }
                          else{
                              Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);
                        }}
                    });
                    thread.start();

    //                Log.d(TAG, payresult);
    //                if(payresult.equals("true")){
    //                    mActivity.success();
    //                Toast.makeText(mActivity, "完成支付"+payresult, Toast.LENGTH_SHORT).show();
    //
    //            }else {
    //                    Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT).show();
    //                }
                }

                @Override
                public void onAuthenticationFailed() {
                    errorMsg.setText("指纹认证失败，请再试一次");
                }
            }, null);
        }
    }

    private void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            isSelfCancelled = true;
        }
    }
    private void pay() {
        final PayPassDialog payPassDialog = new PayPassDialog(getContext());
        payPassDialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(final String passContent) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {

                        payresult=new Conn().wspay(mActivity.getName(),passContent,"20");
//                        Log.d("Main",dataname);
//                        Log.d("Main",editText.getText().toString());
////                        Log.d("Main", tvResult.getText().toString());
//                        Log.d("Main", payResult);
                        Message message = new Message();
                        if(payresult.equals("true")){
                            message.what=istrue;
                            handler.sendMessage(message);}
                        else {
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }
                });
                thread.start();
                dismiss();
            }

            @Override
            public void onPayClose() {
                payPassDialog.dismiss();
            }

            @Override
            public void onPayForget() {
                payPassDialog.dismiss();
            }
        });
    }

}