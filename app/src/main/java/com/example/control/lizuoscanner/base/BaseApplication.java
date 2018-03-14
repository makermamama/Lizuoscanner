package com.example.control.lizuoscanner.base;

import android.app.Application;
import android.os.Looper;
import android.util.Log;

import com.example.control.lizuoscanner.prefrefrence.McuValuePrefrence;
import com.example.control.lizuoscanner.utils.ActivityHelper;


/**
 * Created by LG on 2018/3/10.
 */

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";
    private static BaseApplication mApp;
    private static McuValuePrefrence mcuValuePrefrence;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        if (getMainLooper() != Looper.myLooper()) {
            Log.d(TAG, "not main thread,return");
            return;
        }
        mcuValuePrefrence = McuValuePrefrence.getInstance();

        ActivityHelper.getInstance().init(this);

        mcuValuePrefrence.init(this);
    }
}
