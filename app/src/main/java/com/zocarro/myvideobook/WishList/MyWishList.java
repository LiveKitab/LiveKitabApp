package com.zocarro.myvideobook.WishList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.zocarro.myvideobook.UserCourses.Mycourse;
import com.zocarro.myvideobook.listener.OnDeleteAddressListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyWishList extends AppCompatActivity
{
    RecyclerView wishlistRec;

    //ShimmerRecyclerView shimmerRecycler;
    TextView txtwishlist;
    List<WishListModel> wishListmodelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish_list);
        wishlistRec = findViewById(R.id.recwishlist);
        txtwishlist = findViewById(R.id.txtwishlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Wishlist");
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
        getWishList();
    }
    private void getWishList()
    {
       Common.progressDialogShow(MyWishList.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
        String wishlistUrl = Common.GetWebServiceURL() + "myWishlist.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                wishListmodelList.clear();
                try
                {
                    JSONArray array = new JSONArray(response);
//                    int total = array.getJSONObject(0).getInt("total");
                    String message = array.getJSONObject(0).getString("message");
                   Common.progressDialogDismiss(MyWishList.this);

                    if(message.equals("No favorite Shop found"))
                    {
                        txtwishlist.setVisibility(View.VISIBLE);
                    }
                    for (int i = 1;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        wishListmodelList.add(new WishListModel(object.getString("id"),object.getString("sub_id"),
                                object.getString("sub_name"),object.getString("c_id"),
                                object.getString("c_name"),object.getString("c_no"),object.getString("c_email"),
                                object.getString("c_img"),object.getString("rating")));
                    }
                    //shimmerRecycler.hideShimmerAdapter();
                    wishlistRec.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyWishList.this, LinearLayoutManager.VERTICAL, false);
                    wishlistRec.setLayoutManager(layoutManager);
                    WishListAdapter adapter=new WishListAdapter(MyWishList.this, wishListmodelList, new OnDeleteAddressListener()
                    {
                        @Override
                        public void deleteAddress()
                        {
                            getWishList();
                        }
                    });
                    //shimmerRecycler.setAdapter(adapter);
                    wishlistRec.setAdapter(adapter);
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
                data.put("user_id",uid);
                Log.d("WWW",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(MyWishList.this).add(sr);
    }
}