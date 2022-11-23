package com.zocarro.myvideobook.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.WishList.MyWishList;
import com.zocarro.myvideobook.WishList.WishListAdapter;
import com.zocarro.myvideobook.WishList.WishListModel;
import com.zocarro.myvideobook.listener.OnDeleteAddressListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavouriteMentorFragment extends Fragment
{
    Context context;
    RecyclerView wishlistRec;
    //ShimmerRecyclerView shimmerRecycler;
    TextView txtwishlist;
    List<WishListModel> wishListmodelList = new ArrayList<>();

    public FavouriteMentorFragment()
    {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_favourite_mentor2, container, false);
        context = getContext();
        // Inflate the layout for this fragment
        wishlistRec = v.findViewById(R.id.recwishlist);
        txtwishlist = v.findViewById(R.id.txtwishlist);
        getWishList();

        return v;
    }
    private void getWishList()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final String uid = mPrefs.getString("u_id", "none");
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    wishlistRec.setLayoutManager(layoutManager);
                    WishListAdapter adapter=new WishListAdapter(context, wishListmodelList, new OnDeleteAddressListener()
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
        Volley.newRequestQueue(context).add(sr);
    }
}