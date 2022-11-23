package com.zocarro.myvideobook.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.DashBoardActivity;
import com.zocarro.myvideobook.Dashboard.courseCategories;
import com.zocarro.myvideobook.Dashboard.courseCategoriesAdapter;
import com.zocarro.myvideobook.Dashboard.topRatedCreators;
import com.zocarro.myvideobook.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    ArrayList<Department> list = new ArrayList<>();

//kbb
    String b_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Course Video");
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

        Intent i = getIntent();
        b_id = i.getStringExtra("b_id");
        getCat();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getCat()
    {
        Common.progressDialogShow(CategoryActivity.this);
        String caturl = Common.GetWebServiceURL()+"catWiseCourse.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(CategoryActivity.this);
                    JSONArray res = new JSONArray(response);
                    Log.d("!!!", response);
                    list.clear();
                    for (int i=0;i<res.length();i++)
                    {
                        JSONObject object=res.getJSONObject(i);
                        list.add(new Department(object.getString("course_id"),
                                object.getString("course_name"),object.getString("course_bg"),
                                object.getString("rating"),object.getString("enrolled")));
                    }
                    Collections.reverse(list);
                    DepartmentAdapter adapter=new DepartmentAdapter(CategoryActivity.this,list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(CategoryActivity.this);
                }
            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(CategoryActivity.this);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("b_id", b_id);
                Log.d("###", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(CategoryActivity.this).add(request);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
