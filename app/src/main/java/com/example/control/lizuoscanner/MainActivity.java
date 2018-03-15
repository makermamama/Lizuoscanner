package com.example.control.lizuoscanner;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.control.lizuoscanner.prefrefrence.McuValuePrefrence;
import com.example.control.lizuoscanner.view.LaunchActivity;
import com.example.control.lizuoscanner.view.ScanningReturnActivity;
import com.example.control.lizuoscanner.view.ScanoutActivity;
import com.example.control.lizuoscanner.view.System_Activity;


public class MainActivity extends AppCompatActivity {

    //数据持久存储管理类
    McuValuePrefrence mcuValuePrefrence;
    //按钮
    Button Shipments_scan;
    Button Return_scan;
    Button system_set;
    Button register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);

        initview();

        initdata();

        initListen();
    }

    /**
     * 初始化控件
     */
    void initview() {
        Shipments_scan = (Button) findViewById(R.id.ShipmentsScan_btn);
        Return_scan = (Button) findViewById(R.id.ReturnScan_btn);
        system_set = (Button) findViewById(R.id.SystemSet_btn);
        register = (Button) findViewById(R.id.register_btn);
    }

    /**
     * 初始化数据
     */
    void initdata() {
        mcuValuePrefrence = McuValuePrefrence.getInstance();
    }

    /**
     * 初始化监听
     */
    void initListen() {
        Shipments_scan.setOnClickListener(listener);
        Return_scan.setOnClickListener(listener);
        system_set.setOnClickListener(listener);
        register.setOnClickListener(listener);
    }

    /**
     * 点击事件
     */
    View.OnClickListener listener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ShipmentsScan_btn:
                    Shipments_scan();
                    break;

                case R.id.ReturnScan_btn:
                    Return_scan();
                    break;

                case R.id.SystemSet_btn:
                    SystemSet();
                    break;

                case R.id.register_btn:
                    register();
                    break;
            }
        }
    };

    /**
     *  出货扫描点击事件
     */
    void  Shipments_scan(){
        //判断本机是否已经注册
       if( mcuValuePrefrence.getIsRegiste() ){
           Intent intent = new Intent(MainActivity.this ,ScanoutActivity.class);
           startActivity(intent);
       }else{
           Toast.makeText(MainActivity.this, "请先进行终端注册", Toast.LENGTH_SHORT).show();
       }
    }

    /**
     *  退货扫描点击事件
     */
    void  Return_scan(){
        //判断本机是否已经注册
        if( mcuValuePrefrence.getIsRegiste() ){
            Intent intent = new Intent(MainActivity.this ,ScanningReturnActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "请先进行终端注册", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *  系统设置点击事件
     */
    void  SystemSet(){
        Intent intent = new Intent(MainActivity.this ,System_Activity.class);
        startActivity(intent);
    }

    /**
     *  终端注册点击事件
     */
    void  register(){
        Intent intent = new Intent(MainActivity.this ,LaunchActivity.class);
        startActivity(intent);
    }

}
