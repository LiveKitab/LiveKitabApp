package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.HomeActivity;
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

public class TrendingMentorActivity extends AppCompatActivity
{
RecyclerView mentortrending;
    List<topRatedCourses> topRatedCoursesList = new ArrayList<>();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_mentor);
        mentortrending = findViewById(R.id.mentortrending);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Trending Mentors");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getTopCourses();
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
    private void getTopCourses()
    {
        Common.progressDialogShow(TrendingMentorActivity.this);
        String topcourseurl = Common.GetWebServiceURL()+"topRatedCoursevertical.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, response ->
        {
            try
            {
                Common.progressDialogDismiss(TrendingMentorActivity.this);
                JSONArray res = new JSONArray(response);
                Log.d("!!!", response);
                topRatedCoursesList.clear();
                for (int i=0;i<res.length();i++)
                {
                    JSONObject object = res.getJSONObject(i);
                    topRatedCoursesList.add(new topRatedCourses(
                            object.getString("c_id"),
                            object.getString("enrolled"),
                            object.getString("rating"),
                            object.getString("m_name"),
                            object.getString("c_img")));

                }
                topCoursesAdapter courseAdapter=new topCoursesAdapter(TrendingMentorActivity.this, topRatedCoursesList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mentortrending.setLayoutManager(layoutManager);
                mentortrending.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(TrendingMentorActivity.this);
                Toast.makeText(TrendingMentorActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        },error ->
        {
            Common.progressDialogDismiss(TrendingMentorActivity.this);
            Toast.makeText(TrendingMentorActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return new HashMap<>();
            }
        };
        Volley.newRequestQueue(TrendingMentorActivity.this).add(request);
    }

}