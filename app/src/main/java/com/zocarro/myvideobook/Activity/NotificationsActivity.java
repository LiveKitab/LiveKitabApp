package com.zocarro.myvideobook.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.zocarro.myvideobook.Adapter.NotificationsAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Model.Notifications;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsActivity extends AppCompatActivity
{
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView notificationsRecyclerView;
    TextView noTextView;
    ArrayList<Notifications> notificationsArrayList = new ArrayList<>();
    private static final String TAG = "NotificationsActivity";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notifications");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView);
        noTextView = findViewById(R.id.noTextView);

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        initRecyclerView();

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

    private void initRecyclerView()
    {
        String userRegUrl = Common.GetWebServiceURL() + "fetch_notification.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                notificationsArrayList.clear();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    Log.d(TAG, "onResponse: "+ response);
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(1).getString("message");
                    int total = array.getJSONObject(0).getInt("total");

                    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NotificationsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("notification_count", total);
                    editor.commit();
                    if (total<1)
                    {
                        noTextView.setVisibility(View.VISIBLE);
                        noTextView.setText(message);
                    }
                    else {
                        for (int i = 2; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            notificationsArrayList.add(new Notifications(object.getString("nt_id"),
                                    object.getString("title"),
                                    object.getString("description"),
                                    object.getString("date"),
                                    object.getString("status"),
                                    object.getString("link")));
                        }
                    }
                    NotificationsAdapter adapter = new NotificationsAdapter(notificationsArrayList, NotificationsActivity.this );
                    notificationsRecyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NotificationsActivity.this);
                    notificationsRecyclerView.setLayoutManager(mLayoutManager);
                    notificationsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    notificationsRecyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    Toast.makeText(NotificationsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                Toast.makeText(NotificationsActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d(TAG, "getParams: "+ data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }


    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmerAnimation();
    }
}