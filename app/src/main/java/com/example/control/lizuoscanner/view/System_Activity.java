package com.example.control.lizuoscanner.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.prefrefrence.McuValuePrefrence;

public class System_Activity extends AppCompatActivity {

    EditText server_add;
    EditText local_repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        initview();

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
}
