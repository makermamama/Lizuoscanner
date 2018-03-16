package com.example.control.lizuoscanner.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.prefrefrence.McuValuePrefrence;
import com.example.control.lizuoscanner.baseinterface.BaseInterface;
import com.example.control.lizuoscanner.utils.Internet;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class LaunchActivity extends AppCompatActivity implements View.OnClickListener,BaseInterface {

    private String TAG = LaunchActivity.class.getSimpleName();
    private static final String nameSpaceAddress  ="http://tempuri.org/";
    private static final String methodNameAddress = "CheckLogin";
    private static final String soapActionAddress = "http://tempuri.org/CheckLogin";

    private EditText nameEditText;
    private EditText passEditText;
    private Button loginButton;
    private Button exitButton;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch2);
        Log.i(TAG, "onCreat()");
        initView();
    }

    private void initView() {

        nameEditText = (EditText) findViewById(R.id.uesr_input);
        passEditText = (EditText) findViewById(R.id.passport_input);

        loginButton = (Button) findViewById(R.id.login_button);
        exitButton = (Button) findViewById(R.id.exit_button);
        loginButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        inithandle();


    }

    private SoapObject getwebservice_logininfo(String name, String password) {
        SoapObject soapObject = new SoapObject(nameSpaceAddress, methodNameAddress);

        soapObject.addProperty(LoginServerAdressKey,LoginServerAdress);
        soapObject.addProperty(LoginDataNameKey, LoginDataName);
        soapObject.addProperty(LoginServerNameKey, LoginServerName);
        soapObject.addProperty(LoginServerPwdKey, LoginServerPwd);
        soapObject.addProperty(LoginLogNameKey, name);
        soapObject.addProperty(LoginLogPwdKey, password);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(urlAddress);
            //调用服务
        try {
            httpTransportSE.call(soapActionAddress, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            return  result;
        } catch (IOException e) {
            Log.e(TAG,"登陆出错");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e(TAG,"登陆出错");
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
               if ( Internet.isNetworkAvailable(this) == false ) {
                   Toast.makeText(this, "无网络,请先检查网络连接", Toast.LENGTH_LONG).show();
                   break;
               }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SoapObject result = getwebservice_logininfo(nameEditText.getEditableText().toString(), passEditText.getEditableText().toString());
                        CkeckRegiste(result);
                    }
                }).start();
                break;
            case R.id.exit_button:
                finish();
                break;
            default:
                break;
        }
    }



    /**
     * 检测注册是否成功
     * @param soapObject
     */
    void CkeckRegiste(SoapObject soapObject ){
        String check  = soapObject.getProperty(0).toString();
        boolean usTrue = true;
       if( check.equals(String.valueOf(usTrue)) ){
           //持久保存
           McuValuePrefrence mcuValuePrefrence = McuValuePrefrence.getInstance();
           mcuValuePrefrence.setIsRegiste(true);
           mcuValuePrefrence.setRegistePassword(passEditText.getEditableText().toString());
           mcuValuePrefrence.setRegisteName(nameEditText.getEditableText().toString());
           Log.e(TAG,"注册且保存数据成功");
           Message message =new  Message();
           message.what = 1;
           handler.sendMessage(message);
       }else{
           Message message =new  Message();
           message.what = 2;
           handler.sendMessage(message);
       }
    }

    /**
     * 初始化handle
     */
    void inithandle(){
        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //注册成功
                    case 1:
                        Toast.makeText(LaunchActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        break;
                    //注册失败
                    case 2:
                        Toast.makeText(LaunchActivity.this, "注册失败请检查用户名或密码", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:

                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

}
