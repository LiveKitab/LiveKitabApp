package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Adapter.ChapterTestAdapter;
import com.zocarro.myvideobook.Adapter.MentorChapAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Model.MentorModel;
import com.zocarro.myvideobook.Model.chaptermodel;
import com.zocarro.myvideobook.Model.chaptertestmodel;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterTestActivity extends AppCompatActivity
{
    String c_id, subject_id, chapter_id, chapter_name,number;
    Toolbar toolbar;
    RecyclerView chap_test;
    String total;
    TextView testcount;
    ArrayList<chaptertestmodel> mentorModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_test);
        toolbar = findViewById(R.id.toolbar);
        testcount = findViewById(R.id.testcount);
        chap_test = findViewById(R.id.chapter_test);

        Intent intent = getIntent();
        c_id = intent.getStringExtra("c_id");
        subject_id = intent.getStringExtra("sub_id");
        chapter_id = intent.getStringExtra("chapter_id");
        chapter_name = intent.getStringExtra("chapter_name");
        number = intent.getStringExtra("number");
        getchaptertestname();
        toolbar.setTitle(chapter_name + " (Chapter-" + number + ")");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
    private void getchaptertestname()
    {
        Common.progressDialogShow(ChapterTestActivity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String wishlistUrl = Common.GetWebServiceURL() + "chapter_test_video.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("chapter",response);
                mentorModels.clear();
                try
                {
                    JSONArray array = new JSONArray(response);
                    total = array.getJSONObject(0).getString("total");
                    testcount.setText(total);
                    Common.progressDialogDismiss(ChapterTestActivity.this);
                    for (int i = 1;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        mentorModels.add(new chaptertestmodel(object.getString("v_id"),
                                object.getString("test_id"),
                                object.getString("v_title"),
                                object.getString("v_des"),
                                object.getString("v_link"),c_id,subject_id,chapter_id,chapter_name,object.getString("sequence"),number));
                    }
                    //shimmerRecycler.hideShimmerAdapter();
                    chap_test.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ChapterTestActivity.this, LinearLayoutManager.VERTICAL, false);
                    chap_test.setLayoutManager(layoutManager);
                    ChapterTestAdapter adapter=new ChapterTestAdapter(ChapterTestActivity.this, mentorModels);
                    //shimmerRecycler.setAdapter(adapter);
                    chap_test.setAdapter(adapter);
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

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("sub_id", subject_id);
                data.put("chap_id", chapter_id);
                data.put("c_id", c_id);
                Log.d("WWW", data.toString());
                return data;

            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ChapterTestActivity.this).add(sr);
    }

}