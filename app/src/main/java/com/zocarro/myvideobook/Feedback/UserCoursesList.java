package com.zocarro.myvideobook.Feedback;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserCoursesList extends AppCompatActivity
{
    RecyclerView recyclerView;
    ArrayList<UserCourses> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_courses_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Feedback");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        getCourses();
    }
    private void getCourses()
    {
        Common.progressDialogShow(UserCoursesList.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String wishlistUrl = Common.GetWebServiceURL() + "feedbackCourseLIst.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(UserCoursesList.this);
                    Log.d("response1233", response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new UserCourses(
                                object.getString("c_img"),
                                object.getString("c_id"),
                                object.getString("c_name"),
                                object.getString("c_lname"),
                                object.getString("sub_name"),
                                object.getString("sub_id"),
                                object.getString("no_of_video"),
                                object.getString("no_of_test"),object.getString("rating")));
                    }
                    Collections.reverse(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(UserCoursesList.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    CourseAdapter adapter=new CourseAdapter(UserCoursesList.this,list);
                    recyclerView.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(UserCoursesList.this);
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                Common.progressDialogDismiss(UserCoursesList.this);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> data = new HashMap<>();
                data.put("user_id",uid);
                Log.d("LLL",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(UserCoursesList.this).add(sr);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}

