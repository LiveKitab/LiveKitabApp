package com.zocarro.myvideobook.BottomNavigation;




import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zocarro.myvideobook.Activity.NotificationsActivity;
import com.zocarro.myvideobook.R;

public class myFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "push notification";
    private static final String CHANNEL_ID = "101";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title,String message){
        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

}



//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.MediaPlayer;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.preference.PreferenceManager;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.zocarro.myvideobook.Activity.MentorChapterActivity;
//import com.zocarro.myvideobook.Activity.NotificationsActivity;
//import com.zocarro.myvideobook.Controller.AppController;
//import com.zocarro.myvideobook.Dashboard.HomeActivity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class myFireBaseMessagingService extends FirebaseMessagingService
//{
//    private Context mCtx = this;
//    private static final String TAG = "MyFirebaseMsgService";
//    @Override
//    public void onNewToken(@NonNull String token)
//    {
//        Log.d( "Gyan","Refreshed token: " + token);
//        final Context ctx = getApplicationContext();
//        // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
//                {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task)
//                    {
//                        if (!task.isSuccessful())
//                        {
//                            Log.w("Gyan", "getInstanceId failed", task.getException());
//                            return;
//                        }
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken(); //return firebase id
//                        sendRegistrationToServer(token);
//
//                        //FirebaseMessaging.getInstance().subscribeToTopic("global");
//                        FirebaseInstanceId.getInstance().getToken();
//
//                        Log.d("Gyan","firebase regid (token) " + token);
//                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("regid",token);
//                        editor.apply();
//                    }
//                });
//    }
//
//
////    @Override
////    public void onNewToken(String token)
////    {
////        Log.d(TAG, "onNewToken: " + token);
////        final Context ctx = this;
////
////
////        // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//////        FirebaseInstanceId.getInstance().getInstanceId()
//////                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>()
//////                {
//////                    @Override
//////                    public void onComplete(@NonNull Task<InstanceIdResult> task)
//////                    {
//////                        if (!task.isSuccessful())
//////                        {
//////                            Log.w("Firebase", "getInstanceId failed", task.getException());
//////                            return;
//////                        }
//////                        // Get new Instance ID token
//////                        String token = Objects.requireNonNull(task.getResult()).getToken(); //return firebase id
//////                        Log.d("token",token);
//////
//////                        //FirebaseMessaging.getInstance().subscribeToTopic("global");
//////                        FirebaseInstanceId.getInstance().getToken();
//////
//////                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
//////                        String oldToken = sharedPreferences.getString("regid", token);
//////                        String u_id = sharedPreferences.getString("u_id", "none");
//////
//////                        SharedPreferences.Editor editor = sharedPreferences.edit();
//////                        if(u_id.equals("none"))
//////                        {
//////                            editor.putString("regid","none");
//////                            editor.commit();
//////                        } else {
//////                            editor.putString("regid",token);
//////                            editor.commit();
//////                            sendRegistrationToServer(token);
//////                        }
//////                    }
//////                });
////
////
////
////            Log.d(TAG, "onNewToken: " + token);
////            // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
////            FirebaseInstanceId.getInstance().getInstanceId()
////                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
////                        @Override
////                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
////                            if (!task.isSuccessful()) {
////                                Log.w("Firebase", "getInstanceId failed", task.getException());
////                                return;
////                            }
////                            // Get new Instance ID token
////                            String token = task.getResult().getToken(); //return firebase id
////
////                            //FirebaseMessaging.getInstance().subscribeToTopic("global");
////                            FirebaseInstanceId.getInstance().getToken();
////
////                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
////                            String oldToken = sharedPreferences.getString("regid", "none");
////                            String sh_id = sharedPreferences.getString("u_id", "none");
////
////                            SharedPreferences.Editor editor = sharedPreferences.edit();
////                            if(sh_id.equals("none"))
////                            {
////                                editor.putString("regid","none");
////                                editor.commit();
////                            } else
////                                {
////                                editor.putString("regid",token);
////                                editor.commit();
////                                sendRegistrationToServer(token);
////                            }
////                        }
////                    });
////    }
//    private void sendRegistrationToServer(final String token)
//    {
//        // TODO: Implement this method to send token to your app server.
//        String Webserviceurl = Common.GetWebServiceURL() + "updateFirebaseToken.php";
//        Log.d("AAA", Webserviceurl);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        final String cust_id = sharedPreferences.getString("u_id","none");
//        StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> data=new HashMap<>();
//                data.put("token",token);
//                data.put("user_id",cust_id);
//                Log.d(TAG, "getParams: " + data);
//                return data;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
//        AppController.getInstance().addToRequestQueue(request);
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage)
//    {
//        if (remoteMessage.getData().size() > 0)
//        {
//            Log.d("Firebase", "Message data payload: " + remoteMessage.getData());
//            JSONObject level1 = new JSONObject(remoteMessage.getData());
//            JSONObject level2 = null;
//            try
//            {
//                level2 = new JSONObject(level1.getString("data"));
//                //Log.d(TAG, "level 2 " + level2.toString());
//                JSONObject level3 = level2.getJSONObject("payload");
//                String title, message, action, order_id, order_status, cancel_status, imageuri;
//                title = level3.getString("title");
//                message = level3.getString("message");
//                action = level3.getString("action");
////                if (!imageuri.equals("NA")){
////                    Bitmap bitmap = getBitmapfromUrl(imageuri);
////                    sendNotification(title,message, action, order_id, order_status, cancel_status, bitmap);
////                } else {
//                    sendNotification(title,message, action);
////                }
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//    private void sendNotification(String messageBody, String message, String action)
//    {
//        Intent intent;
//        switch (action)
//        {
//            case "order":
//                intent = new Intent(this, HomeActivity.class);
//                break;
//            case "Cart":
//                intent = new Intent(this, MentorChapterActivity.class);
//                break;
//            default:
//                intent = new Intent(this, NotificationsActivity.class);
//                break;
//        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        String channelId = getString(R.string.default_notification_channel_id);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/car_lock_sms");
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.drawable.notification_logo)
//                        .setContentTitle(messageBody)
//                        .setContentText(message)
//                        .setAutoCancel(true)
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                        .setSound(Uri.parse("android.resource://" + mCtx.getPackageName() + "/" + R.raw.juntos))
//                        .setContentIntent(pendingIntent);
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        MediaPlayer mp= MediaPlayer.create(mCtx, R.raw.juntos);
//        mp.start();
//        manager.notify(73195, notificationBuilder.build());
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//        //notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
//    //    private void sendNotification(String messageBody,String message,String action)
////    {
////        Intent intent = null;
////        if (action.equals("action"))
////        {
////            intent = new Intent(this, NotificationsActivity.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        }
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
////                PendingIntent.FLAG_ONE_SHOT);
////
////        String channelId = getString(R.string.default_notification_channel_id);
////        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/car_lock_sms");
////        NotificationCompat.Builder notificationBuilder =
////                new NotificationCompat.Builder(this, channelId)
////                        .setSmallIcon(R.drawable.videobook_logo)
////                        .setContentTitle(messageBody)
////                        .setContentText(message)
////                        .setAutoCancel(true)
////                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
////                        .setSound(Uri.parse("android.resource://" + mCtx.getPackageName() + "/" + R.raw.sms_tone))
////                        .setContentIntent(pendingIntent);
////        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////        manager.notify(73195, notificationBuilder.build());
////
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////        // Since android Oreo notification channel is needed.
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
////        {
////            NotificationChannel channel = new NotificationChannel(channelId,
////                    "Channel human readable title",
////                    NotificationManager.IMPORTANCE_DEFAULT);
////            notificationManager.createNotificationChannel(channel);
////        }
////
////        //notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
////    }
//    private void handleNow() {
//        Log.d(TAG, "Short lived task is done.");
//    }
//}
