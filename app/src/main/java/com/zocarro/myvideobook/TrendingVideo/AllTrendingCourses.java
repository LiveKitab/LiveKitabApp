package com.zocarro.myvideobook.TrendingVideo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.topCoursesAdapter;
import com.zocarro.myvideobook.Dashboard.topRatedCourses;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTrendingCourses extends AppCompatActivity {
    List<topRatedCourses> topRatedCoursesList = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trending_courses);

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Trending Videos");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

//        getTrendingCourses();
    }
//    private void getTrendingCourses() {
//        Common.progressDialogShow(AllTrendingCourses.this);
//        String topcourseurl = Common.GetWebServiceURL()+"allTopRatedCourse.php";
//        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Common.progressDialogDismiss(AllTrendingCourses.this);
//                    JSONArray res = new JSONArray(response);
//                    Log.d("!!!", response);
//                    topRatedCoursesList.clear();
//                    for (int i=0;i<res.length();i++)
//                    {
//                        JSONObject object=res.getJSONObject(i);
//                        topRatedCoursesList.add(new topRatedCourses(object.getString("c_id"),
//                                object.getString("course_id"),object.getString("course_name"),
//                                object.getString("rating"),object.getString("enrolled"),
//                                object.getString("course_bg")));
//                    }
//                    topCoursesAdapter courseAdapter=new topCoursesAdapter(AllTrendingCourses.this,topRatedCoursesList);
//                    recyclerView.setAdapter(courseAdapter);
//                    courseAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Common.progressDialogDismiss(AllTrendingCourses.this);
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Common.progressDialogDismiss(AllTrendingCourses.this);
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
//        Volley.newRequestQueue(AllTrendingCourses.this).add(request);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
