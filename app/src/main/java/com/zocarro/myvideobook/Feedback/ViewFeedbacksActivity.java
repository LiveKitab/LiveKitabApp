package com.zocarro.myvideobook.Feedback;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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

public class ViewFeedbacksActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    ArrayList<Feedbacks> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedbacks);

        recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("View Feedback");
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewFeedbacksActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getFeedbacks();

    }
    private void getFeedbacks()
    {
        Common.progressDialogShow(ViewFeedbacksActivity.this);
        String url = Common.GetWebServiceURL() + "feedbackcourse.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(ViewFeedbacksActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(ViewFeedbacksActivity.this);
                    JSONArray res = new JSONArray(response);
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
                    FeedbacksAdapter adapter=new FeedbacksAdapter(ViewFeedbacksActivity.this,list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Log.d("@@@", response);
                } catch (Exception e) {
                    Common.progressDialogDismiss(ViewFeedbacksActivity.this);
                    e.printStackTrace();
                    Toast.makeText(ViewFeedbacksActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(ViewFeedbacksActivity.this);
                error.printStackTrace();
                Toast.makeText(ViewFeedbacksActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> HashMapParams = new HashMap<>();
                Intent i = getIntent();
                String sub_id = i.getStringExtra("sub_id");
                String c_id = i.getStringExtra("c_id");
                HashMapParams.put("sub_id", sub_id);
                HashMapParams.put("c_id", c_id);
                Log.v("VVVVV", String.valueOf(HashMapParams));
                return HashMapParams;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
