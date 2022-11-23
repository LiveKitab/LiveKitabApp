package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zocarro.myvideobook.Adapter.TrendingMentorchapAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Dashboard.Categories;
import com.zocarro.myvideobook.Dashboard.CategoriesAdapter;
import com.zocarro.myvideobook.Model.MentorchapModel;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrendingMentorsubjectActivity extends AppCompatActivity
{
    String sub_id,cid,c_name,subject_name;
    String stream_id,br_id,pr_id,term_id;
    private final ArrayList<MentorchapModel> subCatArrayList = new ArrayList<>();
    RecyclerView categoryRecyclerView;
    Toolbar toolbar;
    TextView authorname,noTextView;
    TrendingMentorchapAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_mentorsubject);
        categoryRecyclerView = findViewById(R.id.topRatedRec);
        toolbar = findViewById(R.id.toolbar);
        authorname = findViewById(R.id.authorname);
        noTextView = findViewById(R.id.noTextView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        stream_id = preferences.getString("stream_id","none");
        br_id = preferences.getString("br_id","none");
        pr_id = preferences.getString("pr_id","none");
        term_id = preferences.getString("term_id","none");
        Intent i = getIntent();
//        sub_id = i.getStringExtra("sub_id");
        c_name = i.getStringExtra("c_name");
        cid = i.getStringExtra("cid");
//        subject_name = i.getStringExtra("subject_name");
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        setSupportActionBar(toolbar);
        authorname.setText(c_name);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        gettrendingmentorsubject();
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
    private void gettrendingmentorsubject()
    {
        String url = Common.GetBaseURL() +"mentortrendingsubject.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Log.d("***", response);
                    subCatArrayList.clear();
                    JSONArray array = new JSONArray(response);
//                    int total = array.getJSONObject(0).getInt("total");
                    String message = array.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("no subject available"))
                    {
                        noTextView.setVisibility(View.VISIBLE);
                        noTextView.setText(message);
                    }
                    for (int i = 1; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        subCatArrayList.add(new MentorchapModel(object.getString("sub_id"),
                                object.getString("sub_bg"),
                                object.getString("sub_name"),
                                object.getString("sub_code"),cid,c_name));
                    }
                    adapter = new TrendingMentorchapAdapter(getApplicationContext(), subCatArrayList);
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
                data.put("c_id",cid);
                Log.d("data1",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }

}