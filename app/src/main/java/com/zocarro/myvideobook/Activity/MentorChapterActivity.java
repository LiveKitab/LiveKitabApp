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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Adapter.MentorChapAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Feedback.Feedbacks;
import com.zocarro.myvideobook.Feedback.FeedbacksAdapter;
import com.zocarro.myvideobook.Model.chaptermodel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.WishList.MyWishList;
import com.zocarro.myvideobook.WishList.WishListAdapter;
import com.zocarro.myvideobook.WishList.WishListModel;
import com.zocarro.myvideobook.courses.CourseActivity;
import com.zocarro.myvideobook.listener.OnDeleteAddressListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorChapterActivity extends AppCompatActivity
{
    TextView subjectname,chaptercount,authorname;
    Toolbar toolbar;
    RecyclerView chap_rcl;
    String sub_id,cid,c_name,subject_name;
    Button purchasetxt;
    RecyclerView mentor_feedback;
    ArrayList<Feedbacks> list = new ArrayList<>();
    TextView txt2;
    String total,message;
    String novideo,notest,nomaterial;
    List<chaptermodel> wishListmodelList = new ArrayList<>();
    MentorChapAdapter mentorChapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_chapter);
        toolbar = findViewById(R.id.toolbar);
        mentor_feedback = findViewById(R.id.mentor_feedback);
        txt2 = findViewById(R.id.txt2);
        subjectname = findViewById(R.id.subjectname);
        chaptercount = findViewById(R.id.chaptercount);
        purchasetxt = findViewById(R.id.purchasebtn);
        authorname = findViewById(R.id.authorname);
        Intent i = getIntent();
        sub_id = i.getStringExtra("sub_id");
        c_name = i.getStringExtra("c_name");
        cid = i.getStringExtra("cid");
        subject_name = i.getStringExtra("subject_name");
        subjectname.setText(subject_name);
        authorname.setText(c_name);
        chap_rcl = findViewById(R.id.chapter_name);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        purchasetxt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MentorChapterActivity.this, CourseActivity.class);
                intent.putExtra("sub_id",sub_id);
                intent.putExtra("cid",cid);
                intent.putExtra("c_name",c_name);
                intent.putExtra("novideo",novideo);
                intent.putExtra("nomaterial",nomaterial);
                intent.putExtra("notest",notest);
                startActivity(intent);
                finish();
            }
        });
        getchaptername();
        getFeedbacks();
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
    private void getchaptername()
    {
        Common.progressDialogShow(MentorChapterActivity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String wishlistUrl = Common.GetWebServiceURL() + "chapter_name.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("chapter",response);
                wishListmodelList.clear();
                try
                {
                    JSONArray array = new JSONArray(response);
                     total = array.getJSONObject(0).getString("total");
                     message = array.getJSONObject(1).getString("Message");
                     if (!message.equalsIgnoreCase("Already Enrolled"))
                     {
                         purchasetxt.setVisibility(View.VISIBLE);
                     }
                    chaptercount.setText(total);
                    Common.progressDialogDismiss(MentorChapterActivity.this);
                    for (int i = 2;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        wishListmodelList.add(new chaptermodel(object.getString("sub_id"),
                                object.getString("c_id"),
                                object.getString("ch_id"),
                                object.getString("ch_name"),
                                object.getString("number"),
                                object.getString("sub_name"),
                                object.getString("sub_code"),
                                message,object.getString("countvideo"),
                                object.getString("counttest"),
                                object.getString("countmaterial"),
                                object.getString("countvideonumber"),
                                object.getString("counttestnumber"),
                                object.getString("countmaterialnumber")));
                        nomaterial = object.getString("countmaterialnumber");
                        notest = object.getString("counttestnumber");
                        novideo  = object.getString("countvideonumber");

                    }
                    //shimmerRecycler.hideShimmerAdapter();
                    chap_rcl.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MentorChapterActivity.this, LinearLayoutManager.VERTICAL, false);
                    chap_rcl.setLayoutManager(layoutManager);
                    MentorChapAdapter adapter=new MentorChapAdapter(MentorChapterActivity.this, wishListmodelList);
                    //shimmerRecycler.setAdapter(adapter);
                    chap_rcl.setAdapter(adapter);
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
                data.put("sub_id",sub_id);
                data.put("c_id",cid);
                data.put("user_id",uid);
                Log.d("WWW",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(MentorChapterActivity.this).add(sr);
    }
    private void getFeedbacks()
    {
        String url = Common.GetWebServiceURL() + "feedbackcourse.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(MentorChapterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray res = new JSONArray(response);
                    int total = res.getJSONObject(0).getInt("total");
                    if (total == 0)
                    {
                        txt2.setVisibility(View.GONE);
                    }
                    Log.d("!!!", response);
                    list.clear();
                    for (int i=1;i<res.length();i++)
                    {
                        JSONObject object=res.getJSONObject(i);
                        list.add(new Feedbacks(object.getString("user_id"), object.getString("sub_id"),
                                object.getString("rating"), object.getString("review"), object.getString("u_img"),
                                object.getString("username")));
                    }
                    Collections.reverse(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    mentor_feedback.setLayoutManager(layoutManager);
                    FeedbacksAdapter adapter=new FeedbacksAdapter(MentorChapterActivity.this,list);
                    mentor_feedback.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("@@@", response);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MentorChapterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                Toast.makeText(MentorChapterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> HashMapParams = new HashMap<>();
                HashMapParams.put("sub_id", sub_id);
                HashMapParams.put("c_id", cid);
                Log.v("VVVVV", String.valueOf(HashMapParams));
                return HashMapParams;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}