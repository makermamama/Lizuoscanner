package com.example.control.lizuoscanner.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.baseinterface.BaseInterface;
import com.example.control.lizuoscanner.fragment.CargoDialogFragment;
import com.example.control.lizuoscanner.prefrefrence.McuValuePrefrence;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class System_Activity extends AppCompatActivity implements BaseInterface,CargoDialogFragment.GetWarehour{

    private static final String nameSpaceAddress = "http://tempuri.org/";
    private static final String methodNameAddress = "warehouse";
    private static final String soapActionAddress = "http://tempuri.org/warehouse";

    EditText server_add;
    EditText local_repository;
    private ArrayList<String> cargoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        initview();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getCargos();
                    startFragment();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        initdata();
    }
    /**
     * 初始化控件
     */
    void initview() {
        server_add =(EditText)findViewById(R.id.server_add);
        local_repository =(EditText)findViewById(R.id.local_repository);
    }
    /**
     * 初始化数据
     */
    void initdata() {
        McuValuePrefrence mcuValuePrefrence = McuValuePrefrence.getInstance();
        String ServerAddress = mcuValuePrefrence.getServerAddress();
        String LocalRepository = mcuValuePrefrence.getLocalRepository();
        server_add.setText(ServerAddress);
        local_repository.setText(LocalRepository);
    }

    private List getCargos() throws IOException, XmlPullParserException,ClassCastException {
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
        httpTransportSE.call(soapActionAddress, envelope);
        SoapObject result =  (SoapObject) envelope.bodyIn;
        cargoList = parseSoapObjectToList(result);
        Log.e("ScanoutActivity", "cargoList" + cargoList.toString());
        Log.e("ScanoutActivity", "firstString  " + result + "   secondString  " );
        return  cargoList;
    }

    /**
     * 将一串长的字符串正则成字符串数组
     * @param so
     * @return
     */
    private ArrayList<String> parseSoapObjectToList(
            SoapObject so) {
        ArrayList<String> data = new ArrayList<String>();
        String string = so.toString();
        String[] warehours = string.split(";");

        if (warehours.length > 0){
            String[] warehours1 = warehours[0].split("=");
            data.add(warehours1[2]);

            for(int i = 1;i < warehours.length-2;i++){
                String[] warehours2 = new String[2];
                warehours2 = warehours[i].split("=");
                data.add(warehours2[1]);
            }
        }
        return data;
    }
    private void startFragment() {
        CargoDialogFragment fragmeent = new CargoDialogFragment();
        fragmeent.setOnDialogListener(this);
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(SEND_MESSAGE_ID, cargoList);
        Log.e("ScanoutActivity","bundle"+ bundle.toString());
        Log.e("ScanoutActivity","bundle"+cargoList.toString());
        fragmeent.setArguments(bundle);
        fragmeent.show(manager, "CargoDialogFragment");
    }

    @Override
    public void sendmessage(int position) {
        TextView textView = (TextView)findViewById(R.id.local_repository);
        textView.setText(cargoList.get(position).toString());
        McuValuePrefrence mcuValuePrefrence = McuValuePrefrence.getInstance();
        mcuValuePrefrence.setLocalRepository(cargoList.get(position).toString());
    }
}
