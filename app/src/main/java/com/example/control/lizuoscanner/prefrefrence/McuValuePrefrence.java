package com.example.control.lizuoscanner.prefrefrence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rd0240 on 2017/4/24.
 */

public class McuValuePrefrence {

    //是否注册
    private static final String IS_REGISTE = "IS_REGISTE";
    //注册人名字
    private static final String REGISTE_NMAE = "REGISTE_NMAE";
    //注册人密码
    private static final String REGISTE_PASSWORD = "REGISTE_PASSWORD";
    //注册人账号
    private static final String REGISTE_ID   ="REGISTE_ID";
    //服务群地址
    private static final String SERVR_ADDRESS = "SERVR_ADDRESS";
    //本地仓库
    private static final String LOCAL_REPOSITORY = "LOCAL_REPOSITORY";


    private boolean IsRegiste;
    private String RegisteName;
    private String RegistePassword;
    private String RegisteId;
    private String ServerAddress;
    private String LocalRepository;

    private static String MCU_WARN_PREFERENCE_NAME = "MCU_WARN_NAME";

    private static SharedPreferences mPref;

    private static McuValuePrefrence mcuValuePrefrence;

    private McuValuePrefrence(){

    }

    public static McuValuePrefrence getInstance()
    {
        if (mcuValuePrefrence == null)
        {
            synchronized(McuValuePrefrence.class) {
                mcuValuePrefrence = new McuValuePrefrence();
            }
        }
        return mcuValuePrefrence;
    }
    /**
     * 设置是否注册
     *
     * @param isregiste
     */
    public synchronized void setIsRegiste(boolean isregiste) {
        if (this.IsRegiste != isregiste) {
            this.IsRegiste = isregiste;
            SharedPreferences.Editor editor = getEditor();
            editor.putBoolean(IS_REGISTE, isregiste);
            editor.commit();
        }
    }

    /**
     * 设置是注册人姓名
     *
     * @param RegisteName
     */
    public synchronized void setRegisteName(String RegisteName) {
        if (this.RegisteName != RegisteName) {
            this.RegisteName = RegisteName;
            SharedPreferences.Editor editor = getEditor();
            editor.putString(REGISTE_NMAE, RegisteName);
            editor.commit();
        }
    }

    /**
     * 设置注册人账号
     * @param registeId
     */
    public synchronized void setRegisteId(String registeId) {
        if (this.RegisteId != registeId) {
            this.RegisteId = registeId;
            SharedPreferences.Editor editor = getEditor();
            editor.putString(REGISTE_ID,RegisteId);
            editor.commit();
        }
    }

    /**
     * 设置是注册人密码
     *
     * @param registePassworde
     */
    public synchronized void setRegistePassword(String registePassworde) {
        if (this.RegistePassword != registePassworde) {
            this.RegistePassword = registePassworde;
            SharedPreferences.Editor editor = getEditor();
            editor.putString(REGISTE_PASSWORD, registePassworde);
            editor.commit();
        }
    }

    /**
     * 设置服务器地址
     *
     * @param serverAddress
     */
    public synchronized void setServerAddress(String serverAddress) {
        if (this.ServerAddress != serverAddress) {
            this.ServerAddress = serverAddress;
            SharedPreferences.Editor editor = getEditor();
            editor.putString(SERVR_ADDRESS, serverAddress);
            editor.commit();
        }
    }

    /**
     * 设置本地仓库
     *
     * @param localRepository
     */
    public synchronized void setLocalRepository(String localRepository) {
        if (this.LocalRepository != localRepository) {
            this.LocalRepository = localRepository;
            SharedPreferences.Editor editor = getEditor();
            editor.putString(LOCAL_REPOSITORY, localRepository);
            editor.commit();
        }
    }

    public boolean getIsRegiste() {
        return IsRegiste;
    }

    public String getRegisteName() {
        return RegisteName;
    }

    public String getRegistePassword() {
        return RegistePassword;
    }

    public String getServerAddress() {
        return ServerAddress;
    }

    public String getLocalRepository() {
        return LocalRepository;
    }

    public String getRegisteId() {
        return RegisteId;
    }

    //获取Editor实例
    public static SharedPreferences.Editor getEditor() {
        return mPref.edit();
    }


    public void init(Context context) {
        mPref = context.getSharedPreferences(MCU_WARN_PREFERENCE_NAME, Context.MODE_PRIVATE);
        IsRegiste = mPref.getBoolean(IS_REGISTE, false);
        RegisteName = mPref.getString(REGISTE_NMAE, "空");
        RegistePassword = mPref.getString(REGISTE_PASSWORD, "空");
        ServerAddress = mPref.getString(SERVR_ADDRESS, "http://119.23.36.222:8005/DataSwitchService.asmx");
        LocalRepository = mPref.getString(LOCAL_REPOSITORY, "本地仓库");
        RegisteId   =  mPref.getString(REGISTE_ID,"空");
    }
};

