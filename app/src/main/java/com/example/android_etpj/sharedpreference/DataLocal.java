package com.example.android_etpj.sharedpreference;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataLocal {
    private static final String LOGIN ="LOGIN" ;
    private static final String ROLE ="ROLE" ;
    private static final String LOGIN_DATE ="LOGIN_DATE" ;
    private static final String LOGIN_USER ="LOGIN_USER_4" ;
    private static final String REMEMBER_ME = "REMEMBER_ME";
    private static DataLocal instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context){
        instance=new DataLocal();
        instance.mySharedPreferences=new MySharedPreferences(context);
    }

    public static DataLocal getInstance(){
        if(instance==null)
            instance=new DataLocal();
        return instance;
    }


    /*
    * my adding start here
    * */

    public static void setUserRole(String value){
        DataLocal.getInstance().mySharedPreferences.putStringValue((String) ROLE,value);
    }

    public static String getUserRole(){
        return DataLocal.getInstance().mySharedPreferences.getStringValue(ROLE);
    }

    public static void setUserLogin(String user){
        DataLocal.getInstance().mySharedPreferences.putStringValue(LOGIN_USER,user);
    }

    public static String getUserLogin(){
        return DataLocal.getInstance().mySharedPreferences.getStringValue(LOGIN_USER);
    }

    /*
     * my adding end here
     * */

    public static void setIsLogin(boolean value){
        DataLocal.getInstance().mySharedPreferences.putBooleanValue((String) LOGIN,value);
    }

    public static boolean getIsLogin(){
        return DataLocal.getInstance().mySharedPreferences.getBooleanValue(LOGIN);
    }

    public static void setIsRememberMe(boolean value){
        DataLocal.getInstance().mySharedPreferences.putBooleanValue(REMEMBER_ME,value);
    }

    public static boolean getRememberMe(){
        return DataLocal.getInstance().mySharedPreferences.getBooleanValue(REMEMBER_ME);
    }

    public static void setDateLogin(Date value){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date= formatter.format(value);
        DataLocal.getInstance().mySharedPreferences.putStringValue(LOGIN_DATE,date);
    }

    public static Date getDateLogin(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String text=DataLocal.getInstance().mySharedPreferences.getStringValue(LOGIN_DATE);
        Date date;

        try{
            date=formatter.parse(text);
        }
        catch (Exception ex){
            date= Calendar.getInstance().getTime();
            Log.e("GETDATELOGIN",ex.getMessage());
        }

        return date;
    }



}
