package com.zocarro.myvideobook.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.MainActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.SplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    RecyclerView topRatedRec,topCreatorRec,lastViewRec,categoriesRec;
    List<topRatedCourses> topRatedCoursesList = new ArrayList<>();
    List<topRatedCreators> topRatedCreatorsList = new ArrayList<>();
    List<MyCourses> myCoursesList = new ArrayList<>();
    List<courseCategories> courseCategoriesList = new ArrayList<>();
    DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        allocateMemory();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        setNavigationViewListener();

        drawer = findViewById(R.id.drawer_layout);
        //navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        topRatedRec.setHasFixedSize(true);
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
//        getTopCourses();

        topCoursesAdapter adapter = new topCoursesAdapter(DashBoardActivity.this,topRatedCoursesList);
        topRatedRec.setAdapter(adapter);
        topCreatorsAdapter adapter1 = new topCreatorsAdapter(DashBoardActivity.this,topRatedCreatorsList);
        topCreatorRec.setAdapter(adapter1);
        courseCategoriesAdapter adapter2 = new courseCategoriesAdapter(DashBoardActivity.this,courseCategoriesList);
        categoriesRec.setAdapter(adapter2);
        MyCoursesAdapter adapter3 = new MyCoursesAdapter(DashBoardActivity.this,myCoursesList);
        lastViewRec.setAdapter(adapter3);

    }

    private void allocateMemory() {
     topCreatorRec = findViewById(R.id.creatorRec);
     topRatedRec = findViewById(R.id.topRatedRec);
     categoriesRec = findViewById(R.id.catRec);
     lastViewRec = findViewById(R.id.viewRec);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }
    private void setNavigationViewListener() {
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)
                DashBoardActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.action_logout:
            startActivity(new Intent(DashBoardActivity.this, MainActivity.class));
            finish();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("u_id");
            editor.remove("status");
            editor.apply();

            // do something
            return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Log.d("clicked", String.valueOf(id));
        switch (id) {

            case R.id.nav_wishList:
                Intent intent = new Intent(DashBoardActivity.this, SplashScreen.class);
                startActivity(intent);
                break;

            case R.id.nav_my_course:
                Intent intent2 = new Intent(DashBoardActivity.this,MainActivity.class);
                startActivity(intent2);
                break;

        }
        //close navigation drawer
        //drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void getCat() {
        Common.progressDialogShow(DashBoardActivity.this);
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
                    courseCategoriesAdapter catAdapter=new courseCategoriesAdapter(DashBoardActivity.this,courseCategoriesList);
                    categoriesRec.setAdapter(catAdapter);
                    catAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(DashBoardActivity.this);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(DashBoardActivity.this);
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
        Volley.newRequestQueue(DashBoardActivity.this).add(request);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
//    private void getTopCourses() {
////        Common.progressDialogShow(DashBoardActivity.this);
//        String topcourseurl = Common.GetWebServiceURL()+"topRatedCourse.php";
//        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Common.progressDialogDismiss(DashBoardActivity.this);
//                    JSONArray res = new JSONArray(response);
//                    Log.d("!!!", response);
//                    topRatedCoursesList.clear();
//                    for (int i=0;i<res.length();i++)
//                    {
//                        JSONObject object=res.getJSONObject(i);
//                        topRatedCoursesList.add(new topRatedCourses(object.getString("c_id"),
//                                object.getString("course_id"),object.getString("course_name"),
//                                object.getString("rating"),object.getString("enrolled"),object.getString("course_bg")));
//                    }
//                    topCoursesAdapter courseAdapter=new topCoursesAdapter(DashBoardActivity.this,topRatedCoursesList);
//                    topRatedRec.setAdapter(courseAdapter);
//                    courseAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Common.progressDialogDismiss(DashBoardActivity.this);
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Common.progressDialogDismiss(DashBoardActivity.this);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<>();
////                data.put("cid", cid);
////                Log.d("###", data.toString());
//
//                return data;
//            }
//        };
//        Volley.newRequestQueue(DashBoardActivity.this).add(request);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
    private void getTopCreators() {
        Common.progressDialogShow(DashBoardActivity.this);
        String topcourseurl = Common.GetWebServiceURL()+"topRatedCreators.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(DashBoardActivity.this);
                    JSONArray res = new JSONArray(response);
                    Log.d("!!!", response);
                    topRatedCreatorsList.clear();
                    for (int i=0;i<res.length();i++)
                    {
                        JSONObject object=res.getJSONObject(i);
                        topRatedCreatorsList.add(new topRatedCreators(object.getString("course_name"),
                                object.getString("rating"),object.getString("username")));
                    }
                    topCreatorsAdapter creatorAdapter=new topCreatorsAdapter(DashBoardActivity.this,topRatedCreatorsList);
                    topCreatorRec.setAdapter(creatorAdapter);
                    creatorAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(DashBoardActivity.this);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(DashBoardActivity.this);
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
        Volley.newRequestQueue(DashBoardActivity.this).add(request);
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
                    Common.progressDialogDismiss(DashBoardActivity.this);
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
                    MyCoursesAdapter creatorAdapter=new MyCoursesAdapter(DashBoardActivity.this,myCoursesList);
                    lastViewRec.setAdapter(creatorAdapter);
                    creatorAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(DashBoardActivity.this);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(DashBoardActivity.this);
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
        Volley.newRequestQueue(DashBoardActivity.this).add(request);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
