package com.zocarro.myvideobook.MyPurchases;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class MyPurchasesActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ArrayList<Purchases> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);
        recyclerView =findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Purchased Courses");
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
        getCourses();
    }

    private void getCourses()
    {
        Common.progressDialogShow(MyPurchasesActivity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String wishlistUrl = Common.GetWebServiceURL() + "purchasedCourses.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(MyPurchasesActivity.this);
                    Log.d("response", response);
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(0).getString("message");
                    if (message.equals("Not yet Purchased course"))
                    {
                        Toast.makeText(MyPurchasesActivity.this, "No course purchased yet...!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    for (int i = 1;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new Purchases(object.getString("sub_id"),
                                object.getString("c_id"),
                                object.getString("c_name"),
                                object.getString("c_no"),
                                object.getString("c_email"),
                                object.getString("c_img"),
                                object.getString("rating"),
                                object.getString("review"),
                                object.getString("subject_name"),
                                object.getString("no_of_video"),
                                object.getString("no_of_test"),
                                object.getString("price"),
                                object.getString("discount"),object.getString("sub_code"),object.getString("bill_id"),object.getString("durability"),object.getString("sub_bg")));
                    }
                    Collections.reverse(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyPurchasesActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    PurchasesAdapter adapter=new PurchasesAdapter(MyPurchasesActivity.this,list);
                    recyclerView.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(MyPurchasesActivity.this);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                Common.progressDialogDismiss(MyPurchasesActivity.this);
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
        Volley.newRequestQueue(MyPurchasesActivity.this).add(sr);

    }
}
