package com.zocarro.myvideobook.UserCourses;

import android.content.SharedPreferences;
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
import java.util.List;
import java.util.Map;

public class Mycourse extends AppCompatActivity {

    RecyclerView myCourseRec;
    List<CourseListModel> courseListModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        myCourseRec = findViewById(R.id.courseRec);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Course Video");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

        getCourses();
    }

    private void getCourses() {
        Common.progressDialogShow(Mycourse.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String url = Common.GetWebServiceURL() + "viewMyCourses.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(Mycourse.this);
                    Log.d("response", response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);

                        courseListModelList.add(new CourseListModel(object.getString("course_name"),
                                object.getString("course_bg"),object.getString("rating"),
                                object.getString("enrolled"),object.getString("course_id"),
                                object.getString("totalgiven"), object.getString("totaltest"),object.getString("sub_code")));
                    }
                    Collections.reverse(courseListModelList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Mycourse.this, LinearLayoutManager.VERTICAL, false);
                    myCourseRec.setLayoutManager(layoutManager);
                    MyCoursesAdapter adapter=new MyCoursesAdapter(Mycourse.this,courseListModelList);
                    myCourseRec.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("user_id",uid);
                Log.d("LLL",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(Mycourse.this).add(sr);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
