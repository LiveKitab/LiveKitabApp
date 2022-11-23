package com.zocarro.myvideobook;

import android.util.Log;

public class MyLog
{
    public static final boolean ISDEBUG=true;
    public static final String TAG = "INDIA";
    public static final int DEBUG = 1;
    public static final int ERROR = 2;
    public static final int WARN = 3;
    public static final int INFO = 4;
    public static void p(String msg)
    {
        if(ISDEBUG==true)
            Log.d(TAG,msg);
    }
    public static void p(String msg,int type)
    {
        if(ISDEBUG==true)
        {
            if(type==DEBUG)
                Log.d(TAG,msg);
            else if(type==ERROR)
                Log.e(TAG,msg);
            else if(type==WARN)
                Log.e(TAG,msg);
            else
                Log.i(TAG,msg);
        }
    }
}
