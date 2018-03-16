package com.example.control.lizuoscanner.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.control.lizuoscanner.R;
import com.example.control.lizuoscanner.baseinterface.BaseInterface;

import java.util.ArrayList;

/**
 * Created by LG on 2018/3/16.
 */

public class CargoDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener,BaseInterface {

    private String TAG = "CargoDialogFragment";

    private ListView listView;
    ArrayList<String> list;
    private GetWarehour getWarehour;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cargo_dialog, container, false);
        listView = (ListView) rootView.findViewById(R.id.cargo_fragment_list);
        Bundle bundle = getArguments();
        list= bundle.getStringArrayList(SEND_MESSAGE_ID);
        Log.e(TAG, list.toString());
        //创建数组适配器对象，并且通过参数设置类item项的布局样式和数据源
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        if (getWarehour != null) {
            getWarehour.sendmessage(this.position);
        }
        super.onDestroy();
    }

    public interface GetWarehour{

        void sendmessage(int position);

    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        getWarehour = (GetWarehour) activity;
//
//    }

    public void setOnDialogListener(GetWarehour getWarehour) {
        this.getWarehour = getWarehour;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.position = position;
        dismiss();
    }
}
