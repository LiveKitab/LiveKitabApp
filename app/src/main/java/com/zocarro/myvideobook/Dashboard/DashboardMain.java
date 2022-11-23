package com.zocarro.myvideobook.Dashboard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.zocarro.myvideobook.AboutUsActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Feedback.UserCoursesList;
import com.zocarro.myvideobook.HelpActivity;
import com.zocarro.myvideobook.MainActivity;
import com.zocarro.myvideobook.MyPurchases.MyPurchasesActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.Registeration.UpdateProfile;
import com.zocarro.myvideobook.SelectType;
import com.zocarro.myvideobook.SemesterPackage.PackageActivity;
import com.zocarro.myvideobook.Test.TestActivity;
import com.zocarro.myvideobook.Test.TestResult;
import com.zocarro.myvideobook.UserCourses.Mycourse;
import com.zocarro.myvideobook.WishList.MyWishList;
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

import static java.lang.Integer.parseInt;

public class DashboardMain extends AppCompatActivity
{
    private static final int REQUEST_CODE = 111;
    private String[] sliderImageId = new String[3];
    ViewPager viewPager;
    private String mParam1;
    private String mParam2;
    RecyclerView recSemester;
    static RecyclerView recsubject;
    ArrayList<SemesterModel> semesterModelArrayList = new ArrayList<>();
    ArrayList<SubjectSemester> subjectSemesterArrayList = new ArrayList<>();
    static String term = "";
    ChipGroup chipGroup;
    ArrayList<String> semester = new ArrayList<>();
    String str_id, Pro_id;
    List<Integer> color;
    RecyclerView topRatedRec;
    List<String> colorName;
    int chipNumber, size, lastpos = 0;
    ProgressBar progressBar;

    /* RecyclerView topRatedRec,topCreatorRec,lastViewRec,categoriesRec;
    List<topRatedCreators> topRatedCreatorsList = new ArrayList<>();
    List<MyCourses> myCoursesList = new ArrayList<>();
    List<courseCategories> courseCategoriesList = new ArrayList<>();*/
    List<topRatedCourses> topRatedCoursesList = new ArrayList<>();
    DrawerLayout drawer;
    NavigationView navigationView;
    Context ctx = this;
    String u_id, username, u_email, u_img;
    MaterialTextView viewAllMyCourses, viewAllTopCourses;
    BottomNavigationView bottomNavView;
    Toolbar toolbar;
    Boolean isFirstTimeUser;
    static String s_id, pr_id, br_id, t_id,medium;
    MaterialCardView cardPackage;
    TextView Package, txtPackageName, packagePrice, packageoldPrice, txtpkgdetail, txtNoCourseLabel;
    ImageView imgpackage;
    RelativeLayout relcardPackage;
    String pk_id, pk_title, pk_desc, pk_price, pk_dis, pk_oldPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Subject Videos");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
        allocateMemory();
        FirstTimeUser();
        loadBanner();
        s_id=getIntent().getStringExtra("stream_id");
        pr_id=getIntent().getStringExtra("pr_id");
        br_id=getIntent().getStringExtra("br_id");
        t_id=getIntent().getStringExtra("term_id");
        medium=getIntent().getStringExtra("med");

        color = new ArrayList<>();
        color.add(Color.RED);
        color.add(Color.GREEN);
        color.add(Color.BLUE);

        colorName = new ArrayList<>();
        colorName.add("RED");
        colorName.add("GREEN");
        colorName.add("BLUE");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 6000);
        //Log.d("aaa", s_id);

//        getUserData();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
//        View headerview = navigationView.getHeaderView(0);
        String username = preferences.getString("username", "none");
        String u_email = preferences.getString("u_email", "none");
        String user_img = preferences.getString("userImg", "none");

//        TextView name = headerview.findViewById(R.id.txtUserName);
//        TextView email = headerview.findViewById(R.id.txtEmail);
//        CircleImageView imageUSER = headerview.findViewById(R.id.imageUSER);

//        name.setText(username);
//        email.setText(u_email);
//        if (user_img.equals("NOT SET") || user_img.equals("none"))
//        {
//            imageUSER.setImageResource(R.drawable.v_lolgo);
//        }
//        else {
//            String url = Common.GetBaseImageURL() + "src/user/" + user_img;
//            Glide.with(DashboardMain.this).load(url).into(imageUSER);
//            Log.d("URL", url);
//        }
//        name.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(DashboardMain.this, Update_profile.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        email.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(DashboardMain.this, Update_profile.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        navigationView.setItemIconTintList(null);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        ActivityCompat.requestPermissions(DashboardMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE);

        relcardPackage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardMain.this, PackageActivity.class);
                intent.putExtra("pk_id", pk_id);
                intent.putExtra("pk_title", pk_title);
                intent.putExtra("pk_desc", pk_desc);
                intent.putExtra("pk_dis", pk_dis);
                intent.putExtra("pk_oldPrice", pk_oldPrice);
                intent.putExtra("pk_Price", pk_price);
                startActivity(intent);

            }
        });


     /*   topRatedRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topRatedRec.setLayoutManager(layoutManager);
        topCreatorRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topCreatorRec.setLayoutManager(layoutManager1);
        categoriesRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoriesRec.setLayoutManager(layoutManager2);
        lastViewRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lastViewRec.setLayoutManager(layoutManager3);


        topRatedCreatorsList.add(new topRatedCreators("The Android oreo development course","3.5",
                "3500"));
        topRatedCreatorsList.add(new topRatedCreators("The Android oreo development course","3.5",
                "3500"));
        topRatedCreatorsList.add(new topRatedCreators("The Android oreo development course","3.5",
                "3500"));



        getCat();
        getMyCourses();
        getTopCourses();


        
        topCreatorsAdapter adapter1 = new topCreatorsAdapter(DashboardMain.this,topRatedCreatorsList);
        topCreatorRec.setAdapter(adapter1);
        courseCategoriesAdapter adapter2 = new courseCategoriesAdapter(DashboardMain.this,courseCategoriesList);
        categoriesRec.setAdapter(adapter2);
        MyCoursesAdapter adapter3 = new MyCoursesAdapter(DashboardMain.this,myCoursesList);
        lastViewRec.setAdapter(adapter3);
        viewAllMyCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardMain.this,Mycourse.class);
                startActivity(i);
            }
        });
        viewAllTopCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardMain.this, AllTrendingCourses.class);
                startActivity(i);
            }
        });*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            finish();// close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPackageAvailable()
    {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id", "none");
        final String pr_id = mPrefs.getString("pr_id", "none");
        String term_id = mPrefs.getString("term_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "isPackageAvailable.php";
        final StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    progressBar.setVisibility(View.GONE);
                    Log.d("Packege View", response);
                    JSONObject object = new JSONObject(response);
                    String Message = object.getString("message");

                    if (Message.equalsIgnoreCase("YES")) {
                        cardPackage.setVisibility(View.VISIBLE);
                        Package.setVisibility(View.VISIBLE);
                        getPackageDetails();
                    } else {
                        cardPackage.setVisibility(View.GONE);
                        Package.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id", stream_id);
                data.put("pr_id",pr_id);
                data.put("b_id", b_id);
                String term_id = term;
                data.put("term_id", term_id);
                Log.d("Pacakge data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ctx).add(request);

    }

    private void getPackageDetails()
    {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id", "none");
        final String pr_id = mPrefs.getString("pr_id", "none");
        String term_id = mPrefs.getString("term_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "packageDetails.php";
        final StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try {
                    Log.d("Packege View", response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);

                        int price, dis, discountedPrice;
                        price = parseInt(object.getString("price"));
                        dis = parseInt(object.getString("dis"));
                        discountedPrice = price - ((price * dis) / 100);

                        packagePrice.setText(discountedPrice + " ₹");
                        txtPackageName.setText(object.getString("pkg_title"));
                        packageoldPrice.setPaintFlags(packageoldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        packageoldPrice.setText(object.getString("price") + " ₹");
                        txtpkgdetail.setText(object.getString("dis") + " % off");
                        String img_name = Common.GetBaseImageURL() + "package/" + object.getString("pkg_img");
                        Glide.with(DashboardMain.this).load(img_name).into(imgpackage);
                        pk_id = object.getString("pkg_id");
                        pk_title = object.getString("pkg_title");
                        pk_oldPrice = object.getString("price");
                        pk_price = "" + discountedPrice;
                        pk_dis = object.getString("dis");
                        pk_desc = object.getString("descr");
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id", stream_id);
                data.put("pr_id", pr_id);
                data.put("b_id", b_id);
                String term_id = term;
                data.put("term_id", term_id);
                Log.d("Pacakge data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ctx).add(request);
    }

    private void loadBanner() {
        String webserviceurl = Common.GetWebServiceURL() + "bannerAds.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("&&&", response);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        sliderImageId[i] = object.getString("banner_img");
                    }
                    AdvertismentAdapter adapterView = new AdvertismentAdapter(DashboardMain.this, sliderImageId);
                    viewPager.setAdapter(adapterView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(DashboardMain.this).add(request);

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < color.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }

    private void defaultChip(Integer integer, Chip chip) {
        if (integer == 0) {
            chip.performClick();
            final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            String term_id = mPrefs.getString("term_id", "none");
            term = semesterModelArrayList.get(0).getTerm_id();

            getCoursesSemester();
        }

    }

    private void FirstTimeUser()
    {
        //Common.progressDialogShow(ctx);
        String url = Common.GetWebServiceURL() + "TermFind.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");

        StringRequest request = new StringRequest(StringRequest.Method.POST, url, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    //Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    Log.d("resfirst", response);
                    String stream = array.getJSONObject(0).getString("stream");
                    String program = array.getJSONObject(0).getString("program");
                    String department = array.getJSONObject(0).getString("department");
                    String term_id = array.getJSONObject(0).getString("term_id");
                    String med = array.getJSONObject(0).getString("med");

                    if (stream.equalsIgnoreCase("STREAM NOT SET") ||
                            program.equalsIgnoreCase("PROGRAM NOT SET") || department.equalsIgnoreCase("DEPARTMENT NOT SET") || term_id.equalsIgnoreCase("TERM NOT SET"))
                    {
                        isFirstTimeUser = true;
                    }
                    else {
                        isFirstTimeUser = false;
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashboardMain.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("stream_id", stream);
                        editor.putString("pr_id", program);
                        editor.putString("br_id", department);
                        editor.putString("term_id", term_id);
                        editor.putString("med", med);
                        editor.commit();
                        s_id = stream;
                        pr_id = program;
                        br_id = department;
                        t_id = term_id;
                        medium=med;
                        SendRequest();
                    }
                    if (isFirstTimeUser)
                    {
                        Intent go = new Intent(DashboardMain.this, SelectType.class);
                        startActivity(go);
                        finish();
                    }
                    else {
                       // SendRequest();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(DashboardMain.this);
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Common.progressDialogDismiss(DashboardMain.this);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("u_id", uid);
                Log.d("selctType", data.toString());
                return data;
            }
        };
        //Volley.newRequestQueue(DashboardMain.this).add(request);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(request);
    }

    private void allocateMemory() {
       /* topCreatorRec = findViewById(R.id.creatorRec);
        categoriesRec = findViewById(R.id.catRec);
        lastViewRec = findViewById(R.id.viewRec);
        viewAllMyCourses = findViewById(R.id.viewAllMyCourses);
        viewAllTopCourses = findViewById(R.id.viewAllTopCourses);*/
        topRatedRec = findViewById(R.id.topRatedRec);
        recsubject = findViewById(R.id.recsubject);
        chipGroup = findViewById(R.id.semester);
        cardPackage = findViewById(R.id.cardPackage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        recsubject.setLayoutManager(layoutManager);
        viewPager = findViewById(R.id.viewPager);
        progressBar = findViewById(R.id.progressBar);
        Package = findViewById(R.id.pacakage);
        txtPackageName = findViewById(R.id.txtPackageName);
        Package = findViewById(R.id.pacakage);
        packagePrice = findViewById(R.id.packagePrice);
        packageoldPrice = findViewById(R.id.packageoldPrice);
        txtpkgdetail = findViewById(R.id.txtpkgdis);
        txtNoCourseLabel = findViewById(R.id.txtNoCourse);
        imgpackage = findViewById(R.id.imgpackage);
        relcardPackage = findViewById(R.id.relcardPackage);


    }

    /*  private void getCat() {
          Common.progressDialogShow(DashboardMain.this);
          String caturl = Common.GetWebServiceURL()+"branchCategories.php";
          StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, new com.android.volley.Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {

                      JSONArray res = new JSONArray(response);
                      Log.d("!!!", response);
                      courseCategoriesList.clear();
                      for (int i=0;i<res.length();i++)
                      {
                          JSONObject object=res.getJSONObject(i);
                          courseCategoriesList.add(new courseCategories(object.getString("b_id"),
                                  object.getString("b_name")));
                      }
                      Collections.reverse(courseCategoriesList);
                      courseCategoriesAdapter catAdapter=new courseCategoriesAdapter(DashboardMain.this,courseCategoriesList);
                      categoriesRec.setAdapter(catAdapter);
                      catAdapter.notifyDataSetChanged();


                  } catch (JSONException e) {
                      e.printStackTrace();
                      Common.progressDialogDismiss(DashboardMain.this);
                      Toast.makeText(DashboardMain.this, "something went wrong", Toast.LENGTH_LONG).show();
                  }
              }
          }, new com.android.volley.Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Common.progressDialogDismiss(DashboardMain.this);
                  Toast.makeText(DashboardMain.this, "something went wrong", Toast.LENGTH_LONG).show();
              }
          }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> data = new HashMap<>();
  //                data.put("cid", cid);
  //                Log.d("###", data.toString());

                  return data;
              }
          };
          Volley.newRequestQueue(DashboardMain.this).add(request);
          RequestQueue requestQueue = Volley.newRequestQueue(this);
          requestQueue.add(request);
      }

      */
      /*
      private void getTopCreators() {
          Common.progressDialogShow(DashboardMain.this);
          String topcourseurl = Common.GetWebServiceURL()+"topRatedCreators.php";
          StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, new com.android.volley.Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {
                      Common.progressDialogDismiss(DashboardMain.this);
                      JSONArray res = new JSONArray(response);
                      Log.d("!!!", response);
                      topRatedCreatorsList.clear();
                      for (int i=0;i<res.length();i++)
                      {
                          JSONObject object=res.getJSONObject(i);
                          topRatedCreatorsList.add(new topRatedCreators(object.getString("course_name"),
                                  object.getString("rating"),object.getString("username")));
                      }
                      topCreatorsAdapter creatorAdapter=new topCreatorsAdapter(DashboardMain.this,topRatedCreatorsList);
                      topCreatorRec.setAdapter(creatorAdapter);
                      creatorAdapter.notifyDataSetChanged();
                  } catch (JSONException e) {
                      e.printStackTrace();
                      Common.progressDialogDismiss(DashboardMain.this);
                  }
              }
          }, new com.android.volley.Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Common.progressDialogDismiss(DashboardMain.this);
              }
          }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> data = new HashMap<>();

                  return data;
              }
          };
          Volley.newRequestQueue(DashboardMain.this).add(request);
          RequestQueue requestQueue = Volley.newRequestQueue(this);
          requestQueue.add(request);
      }
      private void getMyCourses() {

          String mycourseurl = Common.GetWebServiceURL()+"viewMyCourses.php";
          SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
          final String uid = mPrefs.getString("u_id", "none");

          StringRequest request = new StringRequest(StringRequest.Method.POST, mycourseurl, new com.android.volley.Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  try {
                    //  Common.progressDialogDismiss(DashboardMain.this);
                      JSONArray res = new JSONArray(response);
                      Log.d("!!!", response);
                      myCoursesList.clear();
                      for (int i=0;i<res.length();i++)
                      {
                          JSONObject object=res.getJSONObject(i);
                          myCoursesList.add(new MyCourses(object.getString("course_id"),
                                  object.getString("user_id"),object.getString("course_name"),
                                  object.getString("course_bg"),object.getString("rating"),
                                  object.getString("enrolled")));
                      }
                      MyCoursesAdapter creatorAdapter=new MyCoursesAdapter(DashboardMain.this,myCoursesList);
                      lastViewRec.setAdapter(creatorAdapter);
                      creatorAdapter.notifyDataSetChanged();
                  } catch (JSONException e) {
                      e.printStackTrace();
                      Common.progressDialogDismiss(DashboardMain.this);
                      Toast.makeText(DashboardMain.this, "something went wrong", Toast.LENGTH_LONG).show();
                  }
              }
          }, new com.android.volley.Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Common.progressDialogDismiss(DashboardMain.this);
                  Toast.makeText(DashboardMain.this, "something went wrong", Toast.LENGTH_LONG).show();
              }
          }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> data = new HashMap<>();
                  data.put("user_id", uid);
                  Log.d("###", data.toString());

                  return data;
              }
          };
          Volley.newRequestQueue(DashboardMain.this).add(request);
          RequestQueue requestQueue = Volley.newRequestQueue(this);
          requestQueue.add(request);
      }*/

//    private void getUserData()
//    {
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        final String uid = mPrefs.getString("u_id", "none");
//        String userUrl = Common.GetWebServiceURL() + "studentProfile.php";
//        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, userUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response)
//            {
//                try
//                {
////                    Common.progressDialogDismiss(DashboardMain.this);
//                    Log.d("///", response);
//                    JSONArray array = new JSONArray(response);
//                    for (int i = 2; i < array.length(); i++)
//                    {
//                        JSONObject object = array.getJSONObject(i);
//                        username = object.getString("username");
//                        u_email = object.getString("u_email");
//                        u_img = object.getString("u_img");
//                    }
//                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashboardMain.this);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("username", username);
//                    editor.putString("u_email", u_email);
//                    editor.putString("userImg", u_img);
//
//                    editor.commit();
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
////                    Common.progressDialogDismiss(DashboardMain.this);
//                    Toast.makeText(ctx, "Something Went wrong", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Common.progressDialogDismiss(DashboardMain.this);
//                Toast.makeText(ctx, "Something Went wrong", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<>();
//                data.put("u_id", uid);
//                Log.d("+++", data.toString());
//
//                return data;
//
//            }
//        };
//
//        Volley.newRequestQueue(DashboardMain.this).add(stringRequest);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

    private void getCoursesSemester() {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id", "none");
        final String pr_id = mPrefs.getString("pr_id", "none");
        String term_id = mPrefs.getString("term_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "course_semester_wise.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, response -> {
            progressBar.setVisibility(View.GONE);
            try {
                Log.d("---", response);
                JSONArray array = new JSONArray(response);
                subjectSemesterArrayList.clear();
                int total = array.getJSONObject(0).getInt("total");
                if (total == 0) {
                    txtNoCourseLabel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    CourseAdapter adapter = new CourseAdapter(DashboardMain.this, subjectSemesterArrayList);
                    recsubject.setAdapter(adapter);
                }
                else {
                    txtNoCourseLabel.setVisibility(View.GONE);
                    for (int i = 1; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        subjectSemesterArrayList.add(new SubjectSemester(object.getString("c_id"),
                                object.getString("course_id"), object.getString("course_name"),
                                object.getString("course_bg"), object.getString("rating"),
                                object.getString("enrolled")));
                    }
                    CourseAdapter adapter = new CourseAdapter(ctx, subjectSemesterArrayList);
                    recsubject.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("stream_id", stream_id);
                data.put("pr_id", pr_id);
                data.put("b_id", b_id);
                String term_id = term;
                data.put("term_id", term_id);
                Log.d("III", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ctx).add(request);
    }

    private void SendRequest()
    {
      /*  final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id", "none");
        final String pr_id = mPrefs.getString("pr_id", "none");
        final String medium = mPrefs.getString("med", "none");*/

        String Webserviceurl = Common.GetWebServiceURL() + "getTerm.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("response", response);
                    JSONArray array = new JSONArray(response);
                    semesterModelArrayList.clear();
                    semester.clear();
                    chipGroup.clearCheck();
                    chipGroup.removeView(DashboardMain.this.chipGroup.getRootView());

                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        semesterModelArrayList.add(new SemesterModel(object.getString("term_id"),
                                object.getString("term_name"), false));
                        semester.add(object.getString("term_name"));

                    }
                    Log.d("size", String.valueOf(semesterModelArrayList.size()));
                    size = semesterModelArrayList.size();
                    // setCategoryChips(semester);
                    for (int inc = 0; inc < size; inc++)
                    {
                        ChipMaking(inc);
                    }
                    chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(ChipGroup chipGroup, @IdRes int i)
                        {
                            Chip chip = chipGroup.findViewById(i);
                            //Toast.makeText(ctx, "Last Pos:" + lastpos + "\n Chip Pos:" + i, Toast.LENGTH_SHORT).show();

                            if (i != lastpos)
                            {
                                chip.setChecked(true);
                                chip.setCheckedIcon(getResources().getDrawable(R.drawable.ic_check_black));
                                Chip chiplast = (Chip) chipGroup.getChildAt(lastpos);
                                chiplast.setEnabled(true);
                                chip.setEnabled(false);
                                lastpos = i;
                                term = semesterModelArrayList.get(i).getTerm_id();
                                progressBar.setVisibility(View.VISIBLE);
                                getCoursesSemester();
                                getPackageAvailable();
                            }
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                if(!s_id.equalsIgnoreCase(null))
                {

                }
                else
                {
                    //data.put("stream_id", stream_id);
                }
                data.put("stream_id", s_id);
                data.put("pr_id", pr_id);
                data.put("b_id", br_id);
                data.put("medium", medium);
                Log.d("()()()$$$", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ctx).add(request);
    }

    public void ChipMaking(int tag)
    {
        Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.item_chip_category, null, false);
        chip.setId(tag);
        if (tag == 0)
        {
            term = semesterModelArrayList.get(0).getTerm_id();
            getCoursesSemester();
            chip.setChecked(true);
            chip.setEnabled(false);
            chip.setCheckedIcon(getResources().getDrawable(R.drawable.ic_check_black));
            lastpos = tag;
            getPackageAvailable();
        }
        chip.setText(semesterModelArrayList.get(tag).getTerm_name());
        chip.setPaddingRelative(5, 5, 5, 5);
        chip.setElevation(5);
        //chip.setChecked(true);
        chip.setCheckable(true);
        chip.setClickable(true);
        chipGroup.addView(chip);
        chipGroup.invalidate();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
