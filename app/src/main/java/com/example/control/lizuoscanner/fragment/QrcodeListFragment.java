package com.example.control.lizuoscanner.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.control.lizuoscanner.R;


/**
 * Created by LG on 2018/3/13.
 */

public class QrcodeListFragment extends ListFragment {

    private QrcodeAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new QrcodeAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qrcode, container, false);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        System.out.println("Click On List Item!!!");
        super.onListItemClick(l, v, position, id);
    }

    class QrcodeAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        public QrcodeAdapter(Context context) {
            super();
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.fragment_qrcode_item, null);
                holder.qrcode_number = (TextView) convertView.findViewById(R.id.qrcode_number);
                holder.delete = (Button) convertView.findViewById(R.id.delete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }

    class ViewHolder {
        public TextView qrcode_number;
        public Button delete;
    }


}
