package com.example.control.lizuoscanner.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.baseinterface.BaseInterface;
import com.example.control.lizuoscanner.bean.ProductDocuments;
import com.example.control.lizuoscanner.fragment.QrcodeListFragment;
import com.example.control.lizuoscanner.prefrefrence.McuValuePrefrence;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanoutActivity extends AppCompatActivity implements BaseInterface,View.OnClickListener{

    private static final String nameSpaceAddress =  "http://tempuri.org/";
    private static final String methodNameAddress = "DownloadData";
    private static final String soapActionAddress = "http://tempuri.org/DownloadData";
    private static final String repositoryname = "深圳中宏力仓";
    private static final String repositorycode = "KM1704100009";

    private EditText scanoutEdit, barcodeEdit;
    private Button scanoutButton, barcodebutton,match;
    private TextView warehourText;
    private List<Fragment> mList = new ArrayList<>();
    private TabAdapter adapter;


    private ArrayList<ProductDocuments> prouduct = new  ArrayList<ProductDocuments>();

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
                    DownloadData("深圳中宏力仓","KM1704100009");
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
    }

    private void initView() {
        scanoutButton = (Button) findViewById(R.id.scan_out_number_button);
        barcodebutton = (Button) findViewById(R.id.bar_code_button);
        match = (Button) findViewById(R.id.match);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        warehourText = (TextView)findViewById(R.id.warehours);

        McuValuePrefrence mcuValuePrefrence = McuValuePrefrence.getInstance();
        String LocalRepository = mcuValuePrefrence.getLocalRepository();
        warehourText.setText(LocalRepository);
    }

    private List<String> DownloadData(String name,String code) throws IOException, XmlPullParserException,ClassCastException {
        SoapObject soapObject = new SoapObject(nameSpaceAddress, methodNameAddress);
        soapObject.addProperty(LoginServerAdressKey,LoginServerAdress);
        soapObject.addProperty(LoginDataNameKey, LoginDataName);
        soapObject.addProperty(LoginServerNameKey, LoginServerName);
        soapObject.addProperty(LoginServerPwdKey, LoginServerPwd);
        soapObject.addProperty(CWareHourseName, repositoryname);
        soapObject.addProperty(CWareHoursecode, repositorycode);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(urlAddress);
        httpTransportSE.call(soapActionAddress, envelope);
        SoapObject result =  (SoapObject) envelope.bodyIn;

        prouduct = GetProudctInfo(parseSoapObjectToList(result));
        Log.e("ScanoutActivity", "result" + result.toString());
        return  null;
    }

    ArrayList<ProductDocuments> GetProudctInfo(List<String> list1){
        if(list1.size()%11 != 0)
            return null;
        ArrayList<ProductDocuments> products = new ArrayList<ProductDocuments>();
        for(int i = 0;i < list1.size();i+=11){
            ProductDocuments product = new ProductDocuments();
            product.setDocuments(list1.get(i));
            product.setDocumentsData(list1.get(i+1));
            product.setCustomer(list1.get(i+2));
            product.setRepository(list1.get(i+3));
            product.setDocumentsType(list1.get(i+4));
            product.setProductcode(list1.get(i+5));
            product.setProduct(list1.get(i+6));
            product.setProductVersion(list1.get(i+7));
            product.setNum(list1.get(i+8));
            product.setTopFive(list1.get(i+9));
            product.setLatterFive(list1.get(i+10));
            products.add(product);
        }
        return  products;
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

    @Override
    public void onClick(View v) {

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
