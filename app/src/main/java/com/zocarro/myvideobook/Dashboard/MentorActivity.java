package com.zocarro.myvideobook.Dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.zocarro.myvideobook.Adapter.MenterAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Model.MentorModel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.listener.OnDeleteCartListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorActivity extends AppCompatActivity
{
    Toolbar toolbar;
    ShimmerFrameLayout shimmerFrameLayout;
    String subject_id,sub_name;
    List<MentorModel> mentorModels = new ArrayList<>();
    MenterAdapter menterAdapter;
    RecyclerView recyclerView;
    TextView nomentor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.mentor_recyclerview);
        Intent intent = getIntent();
        sub_name = intent.getStringExtra("sub_name");
        nomentor = findViewById(R.id.nomentor);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Select Mentor Of " + sub_name);
        setSupportActionBar(toolbar);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        subject_id = intent.getStringExtra("sub_id");

        getMentor();
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
    private void getMentor()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        String topcourseurl = Common.GetWebServiceURL()+"fetch_mentor.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, topcourseurl, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

//                Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
                try
                {
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    Log.d("response",response);
                    JSONArray res = new JSONArray(response);
                    String message  = res.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("No malyu"))
                    {
                        nomentor.setVisibility(View.VISIBLE);
                    }
                    Log.d("!!!", response);
                    mentorModels.clear();
                    for (int i=1;i<res.length();i++)
                    {
                        JSONObject object=res.getJSONObject(i);
                        mentorModels.add(new MentorModel(subject_id,
                                object.getString("c_id"),
                                object.getString("c_name"),
                                object.getString("c_no"),
                                object.getString("c_email"),
                                object.getString("c_img"),
                                object.getString("rating"),
                                object.getString("review"),
                                object.getString("subject_name"),
                                object.getString("no_of_video"),
                                object.getString("no_of_test"),object.getString("sub_code"),object.getString("isInFav")));
                    }
                    menterAdapter=new MenterAdapter(MentorActivity.this,mentorModels, new OnDeleteCartListener()
                    {
                        @Override
                        public void deletecart()
                        {
                            getMentor();
                        }
                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(menterAdapter);
                    menterAdapter.notifyDataSetChanged();
                }
                catch (JSONException e)
                {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    e.printStackTrace();
                    Toast.makeText(MentorActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();
                Toast.makeText(MentorActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("sub_id",subject_id);
                data.put("user_id",uid);
                Log.d("sub_id",subject_id);
                return data;
            }
        };
        Volley.newRequestQueue(MentorActivity.this).add(request);

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.search_menu,menu);
            MenuItem searchItem = menu.findItem(R.id.serach_item);
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
            ImageView searchClose = (ImageView) searchView.findViewById(R.id.search_close_btn);
            searchClose.setImageResource(R.drawable.ic_cancel);
            EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
            searchEditText.setTextColor(getResources().getColor(R.color.white));
            searchEditText.setHintTextColor(getResources().getColor(R.color.white));
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText)
                {
                    menterAdapter.getFilter().filter(newText);
                    return false;
                }
            });
            return  true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        shimmerFrameLayout.stopShimmerAnimation();
    }
}