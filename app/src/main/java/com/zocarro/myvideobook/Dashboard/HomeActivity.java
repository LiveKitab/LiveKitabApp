package com.zocarro.myvideobook.Dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.zocarro.myvideobook.AboutUsActivity;
import com.zocarro.myvideobook.Activity.NotificationsActivity;
import com.zocarro.myvideobook.Activity.SettingActivity;
import com.zocarro.myvideobook.Activity.TrendingMentorActivity;
import com.zocarro.myvideobook.Activity.Wishlist;
import com.zocarro.myvideobook.Adapter.purchasedsubjectAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Feedback.UserCoursesList;
import com.zocarro.myvideobook.Model.purchasedsubject;
import com.zocarro.myvideobook.MyPurchases.MyPurchasesActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.SelectType;
import com.zocarro.myvideobook.courses.AdvertismentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private final ArrayList<Categories> subCatArrayList = new ArrayList<>();
    private final ArrayList<purchasedsubject> purchasedsubject = new ArrayList<>();
    CategoriesAdapter adapter;
    purchasedsubjectAdapter adapter1;
    TextView viewalltrendingmentor;
    TextView userselectedbranch;
    String sub_id,sub_id1;
    RecyclerView categoryRecyclerView;
    private final String[] sliderImageId = new String[3];
    ViewPager viewPager;
    int currentPage = 0;
    Timer timer;
    String p_subid;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    String branch_name,semester_name;
    LinearLayout purchasedcourse;
    RecyclerView userpuchasedcourse;
    RecyclerView topRatedRec;
    List<topRatedCourses> topRatedCoursesList = new ArrayList<>();
    DrawerLayout drawer;
    TextView textname,txtp,coursetext;
    String username, u_email, u_img;
    NavigationView navigationView;
    Toolbar toolbar;
    String stream_id,br_id,pr_id,term_id;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(this, "homeActivity", Toast.LENGTH_SHORT).show();
        Common.progressDialogDismiss(this);
        categoryRecyclerView = findViewById(R.id.recsubject);
        topRatedRec = findViewById(R.id.topRatedRec);
        userpuchasedcourse = findViewById(R.id.userpuchasedcourse);
        purchasedcourse = findViewById(R.id.purchasedcourse);
        viewalltrendingmentor = findViewById(R.id.viewall);
        viewPager = findViewById(R.id.viewPager);
        coursetext = findViewById(R.id.coursetext);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
//        textname = findViewById(R.id.textname);
        txtp = findViewById(R.id.txtp);
        View headerview = navigationView.getHeaderView(0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = preferences.getString("username", "none");
        String u_email = preferences.getString("u_email", "none");
        String user_img = preferences.getString("userImg", "none");
        Log.d("user_img",user_img);
        stream_id = preferences.getString("stream_id","none");
        br_id = preferences.getString("br_id","none");
        pr_id = preferences.getString("pr_id","none");
        term_id = preferences.getString("term_id","none");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = sharedPreferences.getString("regid", "none");
        Log.d("token",token);
        if(token.equals("none"))
        {
//            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>()
//            {
//                @Override
//                public void onSuccess(final String newToken)
//                {
//                    Log.d(TAG, "onSuccess: " + newToken);
////                    String Webserviceurl = Common.GetWebServiceURL() + "updateFirebaseToken.php";
//                    String Webserviceurl ="http://192.168.84.250/videobook/App/ws/updateFirebasetoken.php";
//                    final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    final String user_id = mPrefs.getString("u_id", "none");
//                    StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
//                    {
//                        @Override
//                        public void onResponse(String response)
//                        {
//                            Log.d(TAG, response);
//                        }
//                    }, new Response.ErrorListener()
//                    {
//                        @Override
//                        public void onErrorResponse(VolleyError error)
//                        {
//                            error.printStackTrace();
//                        }
//                    })
//                    {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError
//                        {
//                            Map<String,String> data=new HashMap<>();
//                            data.put("token",newToken);
//                            data.put("user_id",user_id);
//                            Log.d(TAG, "getParams: " + data);
//                            return data;
//                        }
//
//                    };
//                    request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
//                    Volley.newRequestQueue(getApplicationContext()).add(request);
//                }
//            });
        }
        else{
//            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }

        TextView name = headerview.findViewById(R.id.txtUserName);
        userselectedbranch = headerview.findViewById(R.id.userselectedbranch);
//        TextView email = headerview.findViewById(R.id.txtEmail);
        getUserData();
        CircleImageView imageUSER = headerview.findViewById(R.id.imageUSER);
        Button changepredbtn = headerview.findViewById(R.id.changepredbtn);
        changepredbtn.setOnClickListener(v ->
        {
            Intent intent = new Intent(HomeActivity.this, SelectType.class);
            startActivity(intent);
        });
        name.setText(username);
//        email.setText(u_email);
        if (user_img.equals("NOT SET") || user_img.equals("none"))
        {
//            imageUSER.setImageResource(R.drawable.v_lolgo);
            String mgname = username.substring(0, 1);
            Log.d("name", mgname);
            String url = Common.GetBaseImageURL() + "src/user/" + mgname + ".png";
            Log.d("img", url);
            Glide.with(HomeActivity.this).load(url).into(imageUSER);
            Log.d("URL", url);
        }
        else {
            String url = Common.GetBaseImageURL() + "src/user/" + user_img;
            Log.d("user_img",url);
            Glide.with(HomeActivity.this).load(url).into(imageUSER);
            Log.d("URL", url);
        }
        name.setOnClickListener(v ->
        {
            Intent intent = new Intent(HomeActivity.this, Change_Profile.class);
            startActivity(intent);
        });
        imageUSER.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                Intent intent = new Intent(HomeActivity.this, Change_Profile.class);
                startActivity(intent);
            }
        });
//        email.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(HomeActivity.this, Update_profile.class);
//                startActivity(intent);
//                finish();
//            }
//         });
        viewalltrendingmentor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HomeActivity.this, TrendingMentorActivity.class);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        getCategories();
        getpurchasedcourse();
        loadBanner();
//        getTopCourses();
        setNavigationViewListener();
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getTopCourses();
    } //end of onCereat()
    private void getTopCourses()
    {
        Common.progressDialogShow(HomeActivity.this);
        String topcourseurl = Common.GetWebServiceURL()+"topRatedCourse.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, response ->
        {
            Common.progressDialogDismiss(HomeActivity.this);
            Log.d("!!!", response);
            try
            {
                Common.progressDialogDismiss(HomeActivity.this);
                JSONArray res = new JSONArray(response);
                String message = res.getJSONObject(0).getString("message");
                if (message.equalsIgnoreCase("No mentor available"))
                {
                    viewalltrendingmentor.setVisibility(View.GONE);
                    coursetext.setVisibility(View.GONE);
                }
                topRatedCoursesList.clear();
                for (int i=1;i<res.length();i++)
                {
                    JSONObject object = res.getJSONObject(i);
                    topRatedCoursesList.add(new topRatedCourses(
                            object.getString("c_id"),
                            object.getString("enrolled"),
                            object.getString("rating"),
                            object.getString("m_name"),
                            object.getString("c_img")));
                }
                topCoursesAdapter courseAdapter=new topCoursesAdapter(HomeActivity.this, topRatedCoursesList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                topRatedRec.setLayoutManager(layoutManager);
                topRatedRec.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(HomeActivity.this);
                Toast.makeText(HomeActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        },error ->
        {
            Common.progressDialogDismiss(HomeActivity.this);
            Toast.makeText(HomeActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String, String> getParams()

            {
                return new HashMap<>();
            }
        };
        Volley.newRequestQueue(HomeActivity.this).add(request);
    }
    private void getpurchasedcourse()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String url = Common.GetBaseURL() +"homepurchasedcourse.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Common.progressDialogDismiss(HomeActivity.this);
                try
                {
                    Log.d("***", response);
                    purchasedsubject.clear();
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(0).getString("message");
                    if(message.equalsIgnoreCase("Enrolled"))
                    {
                        userpuchasedcourse.setVisibility(View.VISIBLE);
                        purchasedcourse.setVisibility(View.VISIBLE);
                        txtp.setVisibility(View.VISIBLE);
                    }
//                    String message = array.getJSONObject(1).getString("message");

//                    if(total>0) {
                    for (int i = 1; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        sub_id = object.getString("sub_id");
                        purchasedsubject.add(new purchasedsubject(object.getString("sub_id"),
                                object.getString("sub_bg"),
                                object.getString("sub_name"),
                               object.getString("cid"),object.getString("c_name"),object.getString("durability")));
                        p_subid = object.getString("sub_id");
                    }
                    adapter1 = new purchasedsubjectAdapter(getApplicationContext(), purchasedsubject);
                    userpuchasedcourse.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    userpuchasedcourse.setAdapter(adapter1);
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id",uid);
                Log.d("data1",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }
    private void getCategories()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");

        String url = Common.GetBaseURL() +"fetch_subjects.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Common.progressDialogDismiss(HomeActivity.this);
                subCatArrayList.clear();
//                Toast.makeText(getApplicationContext() , response , Toast.LENGTH_SHORT).show();

                try
                {
                    Log.d("***", response);
                    JSONArray array = new JSONArray(response);
//                    int total = array.getJSONObject(0).getInt("total");
//                    String message = array.getJSONObject(1).getString("message");
//                    if(total>0) {
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        subCatArrayList.add(new Categories(object.getString("sub_id"),
                                object.getString("sub_bg"),
                                object.getString("sub_name"),
                                object.getString("sub_code")));
                        sub_id1 = object.getString("sub_id");
                    }
                    adapter = new CategoriesAdapter(getApplicationContext(), subCatArrayList);
                    categoryRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                    categoryRecyclerView.setAdapter(adapter);
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id",stream_id);
                data.put("branch_id",br_id);
                data.put("pr_id",pr_id);
                data.put("term_id",term_id);
                data.put("user_id",uid);
                Log.d("data1",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }
    private void loadBanner()
    {
        String webserviceurl = Common.GetWebServiceURL() + "bannerAds.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Common.progressDialogDismiss(HomeActivity.this);
                Log.d("&&&", response);
                try
                {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        sliderImageId[i] = object.getString("banner_img");
                    }
                    AdvertismentAdapter adapterView = new AdvertismentAdapter(HomeActivity.this, sliderImageId);
                    viewPager.setAdapter(adapterView);

                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable()
                    {
                        public void run() {
                            if (currentPage == array.length())
                            {
                                currentPage = 0;
                            }
                            viewPager.setCurrentItem(currentPage++, true);
                        }
                    };
                    timer = new Timer(); // This will create a new Thread
                    timer.schedule(new TimerTask() { // task to be scheduled
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        })
         {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(HomeActivity.this).add(request);

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
                Intent intent = new Intent(HomeActivity.this, NotificationsActivity.class);
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
                HomeActivity.this);
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
                intent = new Intent(HomeActivity.this, Wishlist.class);
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
                intent = new Intent(HomeActivity.this, MyPurchasesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_feedback:
                intent = new Intent(HomeActivity.this, UserCoursesList.class);
                startActivity(intent);
                break;

//            case  R.id.nav_help:
//                intent = new Intent(HomeActivity.this, HelpActivity.class);
//                startActivity(intent);
//                break;

            case  R.id.nav_Abount:
                intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                intent = new Intent(HomeActivity.this, SettingActivity.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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
    private void getUserData()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String userUrl = Common.GetWebServiceURL() + "studentProfile.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, userUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Common.progressDialogDismiss(HomeActivity.this);
                Log.d("userrespo", response);
                try
                {
    //                    Common.progressDialogDismiss(DashboardMain.this);
                    JSONArray array = new JSONArray(response);
                    for (int i = 2; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        username = object.getString("username");
                        u_email = object.getString("u_email");
                        u_img = object.getString("u_img");
                        branch_name = object.getString("b_name");
                        semester_name = object.getString("term_name");
    //                        textname.setText(branch_name +"("+ semester_name +")");
                        userselectedbranch.setText(branch_name +"("+ semester_name +")");
                    }
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("u_email", u_email);
                    editor.putString("userImg", u_img);
                    editor.commit();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
    //                    Common.progressDialogDismiss(DashboardMain.this);
                    Toast.makeText(HomeActivity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
    //                Common.progressDialogDismiss(DashboardMain.this);
                Toast.makeText(HomeActivity.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("u_id", uid);
                Log.d("+++", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(HomeActivity.this).add(stringRequest);
    }
}
