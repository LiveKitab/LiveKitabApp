package com.zocarro.myvideobook.BottomNavigation;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zocarro.myvideobook.AboutUsActivity;
import com.zocarro.myvideobook.Activity.NotificationsActivity;
import com.zocarro.myvideobook.Activity.SettingActivity;
import com.zocarro.myvideobook.Activity.Wishlist;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.HomeActivity;
import com.zocarro.myvideobook.Feedback.UserCoursesList;
import com.zocarro.myvideobook.MyPurchases.MyPurchasesActivity;
import com.zocarro.myvideobook.R;

import java.util.HashMap;
import java.util.Map;

public class BottomNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String CHANNEL_ID = "101";
    LinearLayout linearLayout;
    BottomNavigationView bottomNavigationView;

    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
//    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        Common.progressDialogDismiss(BottomNavigationActivity.this);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        final String token = sharedPreferences.getString("regid", "none");
//        Log.d("token",token);

//            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//                @Override
//                public void onComplete(@NonNull Task<String> task) {
//                    if (!task.isSuccessful()) {
//                        Log.d("push notification", "failed to get token");
//                    }
//                    String token = task.getResult();
//                    Log.d("token", token);
//
//                    String Webserviceurl = Common.GetWebServiceURL() + "updateFirebasetoken.php";
////                    String Webserviceurl ="http://livebookss.com/videobook/App/ws/updateFirebasetoken.php";
//                    final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    final String user_id = mPrefs.getString("u_id", "none");
//                    StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d(TAG, response);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            error.printStackTrace();
//
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> data = new HashMap<>();
//                            data.put("token", token);
//                            data.put("user_id", user_id);
//                            Log.d(TAG, "getParams: " + data);
//                            return data;
//                        }
//
//                    };
//                    request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
//                    Volley.newRequestQueue(getApplicationContext()).add(request);
//                }
//            });


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "firebaseNotificationnChannel";
//            String description = "Received notification channel";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
        toolbar = findViewById(R.id.toolbar);

//...

//        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        setNavigationViewListener();
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        HomeFragmentBottomNav frag = new HomeFragmentBottomNav();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.bottomNavLinear,frag);
        fragmentTransaction.commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){

                    case R.id.nav_bottom_home:
                        fragmentTransaction.replace(R.id.bottomNavLinear,new HomeFragmentBottomNav());

                        break;

                    case R.id.nav_bottom_purchased:
                        fragmentTransaction.replace(R.id.bottomNavLinear,new purchasedCourceFragment());
                        break;

                    case R.id.nav_bottom_learwithlivekkitab:
                        fragmentTransaction.replace(R.id.bottomNavLinear,new LiveKitabFragment() );
                        break;

                    case R.id.nav_feed:
                        fragmentTransaction.replace(R.id.bottomNavLinear,new feedFragment() );
                        break;

                    case R.id.nav_settings:
                        fragmentTransaction.replace(R.id.bottomNavLinear,new profileFragment() );
                        break;

                }


                fragmentTransaction.commit();
                return true;
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            // do something
            case R.id.ic_notification:
                Intent intent = new Intent(BottomNavigationActivity.this, NotificationsActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return super.onContextItemSelected(item);
    }

    private void setNavigationViewListener()
    {
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)
                BottomNavigationActivity.this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        Intent intent;
        Log.d("clicked", String.valueOf(id));
        switch (id)
        {
//            case R.id.nav_profile:
//                intent = new Intent(HomeActivity.this,Update_profile.class);
//                startActivity(intent);
//                break;

            case R.id.nav_wishList:
                intent = new Intent(BottomNavigationActivity.this, Wishlist.class);
                startActivity(intent);
                break;
            case R.id.nav_my_course:
//                if(navigationView.getCheckedItem().getItemId() != R.id.nav_my_course)
//                {
//                    intent = new Intent(HomeActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                }
//                else {
//                    navigationView.setCheckedItem(R.id.nav_my_course);
//                }
                break;
            case R.id.nav_purchase:
                intent = new Intent(BottomNavigationActivity.this, MyPurchasesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_feedback:
                intent = new Intent(BottomNavigationActivity.this, UserCoursesList.class);
                startActivity(intent);
                break;

//            case  R.id.nav_help:
//                intent = new Intent(HomeActivity.this, HelpActivity.class);
//                startActivity(intent);
//                break;

            case  R.id.nav_Abount:
                intent = new Intent(BottomNavigationActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                intent = new Intent(BottomNavigationActivity.this, SettingActivity.class);
                startActivity(intent);
                break;

//            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
//                alertDialogBuilder.setMessage("Are you sure, You wanted to logout?..");
//                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1)
//                    {
//                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.remove("u_id");
//                        editor.remove("status");
//                        editor.remove("stream_id");
//                        editor.remove("br_id");
//                        editor.remove("pr_id");
//                        editor.remove("term_id");
//                        editor.remove("med");
//                        editor.putBoolean("status",false);
//                        //editor.apply();
//                        editor.commit();
//                        startActivity(new Intent(HomeActivity.this, SignInWithEmailActivity.class));
//                        finish();
//                    }
//                });
//                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        alertDialogBuilder.setCancelable(true);
//                    }
//                });
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//                break;
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_my_course);
    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(BottomNavigationActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }





}