package com.zocarro.myvideobook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;


public class Common
{
//   public final static String MAIN_URL = "https://www.videobooks.zocarro.com/videobook/App";
//   public final static String MAIN_URL = "https://livekitab.com/videobook/App";
   public final static String MAIN_URL = "http://videobooks.zocarro.com/videobook/App";
    public final static String DEFAULT_ERROR_MESSAGE = "OOPS, Something went wrong we are trying to findout problem, " + "please come after sometime";
    public static ProgressDialog p1;
    public static String GetBaseURL()
    {
        return MAIN_URL+ "/ws/";
    }
    public static String GetWebServiceURL()
    {
        return GetBaseURL();
    }
    public static String GetTimeoutMessage()
    {
        return  "Attention!, your server is not responding. check following things and retry" +
                "\n 1) check wamp/ampp/xampp is running in background" +
                "\n 2) check your laptop/pc and mobile is in same network" +
                "\n 3) check ip address of your computer and match webservice url ip address, both ip must be same" +
                "\n 4) check your web service spelling, may be it is incorrect";
    }
    public static void ShowError(Context ctx, String error)
    {
        MyLog.p(error);
        AlertDialog.Builder b1 = new AlertDialog.Builder(ctx);
        b1.setTitle("Error ");
        if(MyLog.ISDEBUG==true)
            b1.setMessage(error);
        else
            b1.setMessage(DEFAULT_ERROR_MESSAGE);
        b1.setPositiveButton("Retry", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }

        });
        b1.create().show();
    }

  /*  public static String GetBaseImageURL()
    {
        return IP+"/school/images/";
    }*/
   public static String GetBaseImageURL()
   {
//       return "https://www.videobooks.zocarro.com/videobook/Web/";
       return "http://livebookss.com/videobook/Web/";
   }
    public static void progressDialogShow(Context context)
    {

        p1 = new ProgressDialog(context);
        p1.setTitle("Processing, please wait");
        p1.setCancelable(false);
        p1.show();
    }
    public static void progressDialogDismiss(Context context)
    {
        // p1.setTitle("Processing, please wait");
        if (p1 != null) {
            p1.dismiss();
        }
    }

}
