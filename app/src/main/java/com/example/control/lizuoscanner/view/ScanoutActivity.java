package com.example.control.lizuoscanner.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.fragment.QrcodeListFragment;


import java.util.ArrayList;
import java.util.List;

public class ScanoutActivity extends AppCompatActivity {

    private EditText scanoutEdit, barcodeEdit;
    private Button scanoutButton, barcodebutton,match;

    private List<Fragment> mList = new ArrayList<>();
    private TabAdapter adapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] title = {"条码清单", "货品清单", "以扫描数"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanout);
        initView();
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
