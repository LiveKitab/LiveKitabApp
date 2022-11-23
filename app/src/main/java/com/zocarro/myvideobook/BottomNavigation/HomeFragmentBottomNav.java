package com.zocarro.myvideobook.BottomNavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.zocarro.myvideobook.Activity.NotificationsActivity;
import com.zocarro.myvideobook.Activity.TrendingMentorActivity;
import com.zocarro.myvideobook.Adapter.purchasedsubjectAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Dashboard.Categories;
import com.zocarro.myvideobook.Dashboard.CategoriesAdapter;
import com.zocarro.myvideobook.Dashboard.topCoursesAdapter;
import com.zocarro.myvideobook.Dashboard.topRatedCourses;
import com.zocarro.myvideobook.Model.purchasedsubject;
import com.zocarro.myvideobook.R;
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


public class HomeFragmentBottomNav extends Fragment {


    private final ArrayList<Categories> subCatArrayList = new ArrayList<>();
    private final ArrayList<com.zocarro.myvideobook.Model.purchasedsubject> purchasedsubject = new ArrayList<>();
    CategoriesAdapter adapter;
    purchasedsubjectAdapter adapter1;
    TextView viewalltrendingmentor;
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
    TextView txtp,coursetext;
    String stream_id,br_id,pr_id,term_id;
    ImageView notification;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        View view =  inflater.inflate(R.layout.fragment_home_bottom_nav, container, false);


        categoryRecyclerView = view.findViewById(R.id.recsubject);
        topRatedRec = view.findViewById(R.id.topRatedRec);
        userpuchasedcourse = view.findViewById(R.id.userpuchasedcourse);
        purchasedcourse = view.findViewById(R.id.purchasedcourse);
        viewalltrendingmentor = view.findViewById(R.id.viewall);
        viewPager = view.findViewById(R.id.viewPager);
        coursetext = view.findViewById(R.id.coursetext);
        txtp = view.findViewById(R.id.txtp);
        notification = view.findViewById(R.id.notification);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String username = preferences.getString("username", "none");
        String u_email = preferences.getString("u_email", "none");
        String user_img = preferences.getString("userImg", "none");
        Log.d("user_img",user_img);
        stream_id = preferences.getString("stream_id","none");
        br_id = preferences.getString("br_id","none");
        pr_id = preferences.getString("pr_id","none");
        term_id = preferences.getString("term_id","none");


        Common.progressDialogDismiss(view.getContext());

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , NotificationsActivity.class));
            }
        });


        getpurchasedcourse();
        getCategories();
        loadBanner();
        getTopCourses();

        viewalltrendingmentor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), TrendingMentorActivity.class);
                startActivity(intent);
            }
        });

        
        return view;
    }



    private void getTopCourses()
    {
        Common.progressDialogShow(getContext());
        String topcourseurl = Common.GetWebServiceURL()+"topRatedCourse.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, response ->
        {
            Log.d("!!!", response);
            try
            {
                Common.progressDialogDismiss(getContext());
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
                topCoursesAdapter courseAdapter=new topCoursesAdapter(getContext(), topRatedCoursesList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                topRatedRec.setLayoutManager(layoutManager);
                topRatedRec.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(getContext());
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        },error ->
        {
            Common.progressDialogDismiss(getContext());
            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String, String> getParams()

            {
                return new HashMap<>();
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }
    private void getpurchasedcourse()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String uid = mPrefs.getString("u_id", "none");

        String url = Common.GetBaseURL() +"homepurchasedcourse.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
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
                    adapter1 = new purchasedsubjectAdapter(getContext(), purchasedsubject);
                    userpuchasedcourse.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    userpuchasedcourse.setAdapter(adapter1);
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String uid = mPrefs.getString("u_id", "none");

        String url = Common.GetBaseURL() +"fetch_subjects.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                subCatArrayList.clear();
//                Toast.makeText(getContext() , response , Toast.LENGTH_SHORT).show();

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
                    adapter = new CategoriesAdapter(getContext(), subCatArrayList);
                    categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    categoryRecyclerView.setAdapter(adapter);
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                Log.d("&&&", response);
                try
                {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        sliderImageId[i] = object.getString("banner_img");
                    }
                    AdvertismentAdapter adapterView = new AdvertismentAdapter(getContext(), sliderImageId);
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
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getContext()).add(request);

    }
}