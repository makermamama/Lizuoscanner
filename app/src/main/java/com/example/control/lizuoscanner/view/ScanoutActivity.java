package com.example.control.lizuoscanner.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.baseinterface.BaseInterface;
import com.example.control.lizuoscanner.fragment.CargoDialogFragment;
import com.example.control.lizuoscanner.fragment.QrcodeListFragment;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanoutActivity extends AppCompatActivity implements BaseInterface,CargoDialogFragment.GetWarehour {
    private static final String nameSpaceAddress = "http://tempuri.org/";
    private static final String methodNameAddress = "warehouse";
    private static final String soapActionAddress = "http://tempuri.org/warehouse";

    private EditText scanoutEdit, barcodeEdit;
    private Button scanoutButton, barcodebutton,match;

    private List<Fragment> mList = new ArrayList<>();
    private TabAdapter adapter;

    private ArrayList<String> cargoList = new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] title = {"条码清单", "货品清单", "以扫描数"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanout);
        initView();
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
        mList.add(new QrcodeListFragment());
        mList.add(new QrcodeListFragment());
        mList.add(new QrcodeListFragment());
        adapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        startFragment();

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

    private List getCargos() throws IOException, XmlPullParserException,ClassCastException {
        SoapObject soapObject = new SoapObject(nameSpaceAddress, methodNameAddress);
        soapObject.addProperty("ServerAdress", "EDZ-20171212KZO");
        soapObject.addProperty("DataName", "XRD_001");
        soapObject.addProperty("ServerNmae", "sa");
        soapObject.addProperty("ServerPwd", "ASDFasdf1234");
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


    private void initView() {
        scanoutButton = (Button) findViewById(R.id.scan_out_number_button);
        barcodebutton = (Button) findViewById(R.id.bar_code_button);
        match = (Button) findViewById(R.id.match);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
    }

    @Override
    public void sendmessage(int position) {
        TextView textView = (TextView)findViewById(R.id.warehours);
        textView.setText(cargoList.get(position).toString());
    }



    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
