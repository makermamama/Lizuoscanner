package com.example.control.lizuoscanner.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.control.lizuoscanner.baseinterface.BaseInterface;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by ma on 2018/3/16.
 */

public class TestActivity extends AppCompatActivity implements BaseInterface{

    private static final String nameSpaceAddress  = "http://tempuri.org/";
    private static final String methodNameAddress = "warehouse ";
    private static final String soapActionAddress = "http://tempuri.org/warehouse";

    private String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getwebservicewarehourse();
            }
        }).start();
    }

    private Object getwebservicewarehourse() {
        SoapObject soapObject = new SoapObject(nameSpaceAddress, methodNameAddress);

        soapObject.addProperty(LoginServerAdressKey,LoginServerAdress);
        soapObject.addProperty(LoginDataNameKey, LoginDataName);
        soapObject.addProperty(LoginServerNameKey, LoginServerName);
        soapObject.addProperty(LoginServerPwdKey, LoginServerPwd);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(urlAddress);
        //调用服务
        try {
            httpTransportSE.call(soapActionAddress, envelope);
            Object result =  (SoapObject)envelope.bodyOut;
            return  result;
        } catch (IOException e) {
            Log.e(TAG,"获取仓库信息错误");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e(TAG,"获取仓库信息错误");
            e.printStackTrace();
        }catch (ClassCastException e){
            e.printStackTrace();
            Log.e(TAG,"获取仓库信息错误");
        }
        return null;
    }
}
