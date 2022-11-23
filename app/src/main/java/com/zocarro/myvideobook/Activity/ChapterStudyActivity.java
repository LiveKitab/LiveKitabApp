package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Adapter.ChapterStudyAdapter;
import com.zocarro.myvideobook.Adapter.ChapterTestAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Model.chaptermaterialmodel;
import com.zocarro.myvideobook.Model.chaptertestmodel;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChapterStudyActivity extends AppCompatActivity
{
    String c_id, subject_id, chapter_id, chapter_name,number;
    RecyclerView studymaterialrcv;
    TextView testcount;
    Toolbar toolbar;
    ArrayList<chaptermaterialmodel> chaptermaterialmodels =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_study);
        toolbar = findViewById(R.id.toolbar);
        testcount = findViewById(R.id.testcount);
        Intent intent = getIntent();
        c_id = intent.getStringExtra("c_id");
        subject_id = intent.getStringExtra("sub_id");
        chapter_id = intent.getStringExtra("chapter_id");
        chapter_name = intent.getStringExtra("chapter_name");
        number = intent.getStringExtra("number");
        studymaterialrcv = findViewById(R.id.chapterstudymaterialrcv);
        toolbar.setTitle(chapter_name + " (Chapter-" + number + ")");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Log.d("chapter_name",chapter_name);

        getmaterial();
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
    private  void getmaterial()
    {
        String weburl = Common.GetBaseURL() + "chapter_material_video.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, weburl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("response",response);
                chaptermaterialmodels.clear();
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    String total = jsonArray.getJSONObject(0).getString("total");
                    testcount.setText(total);
                    for(int i=1;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        chaptermaterialmodels.add(new chaptermaterialmodel(object.getString("v_id"),
                                object.getString("file"),
                                object.getString("v_title"),
                                object.getString("v_des"),
                                object.getString("v_link"),c_id,subject_id,chapter_id,chapter_name,object.getString("sequence")));
                    }
                    studymaterialrcv.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ChapterStudyActivity.this, LinearLayoutManager.VERTICAL, false);
                    studymaterialrcv.setLayoutManager(layoutManager);
                    ChapterStudyAdapter adapter=new ChapterStudyAdapter(ChapterStudyActivity.this, chaptermaterialmodels);
                    //shimmerRecycler.setAdapter(adapter);
                    studymaterialrcv.setAdapter(adapter);
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
                Map<String,String> data = new HashMap<>();
                data.put("sub_id",subject_id);
                data.put("chap_id",chapter_id);
                data.put("c_id",c_id);
                Log.d("WWW",data.toString());
                return data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ChapterStudyActivity.this).add(stringRequest);
    }

}