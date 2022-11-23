package com.zocarro.myvideobook.BottomNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zocarro.myvideobook.Adapter.feedVideoAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Model.feedlinkmodel;
import com.zocarro.myvideobook.R;


public class feedFragment extends Fragment {

    RecyclerView ytplayerrcl;
    feedVideoAdapter adapter;

    ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_feed, container, false);


        shimmerFrameLayout = view.findViewById(R.id.feed_shimmer);
        shimmerFrameLayout.startShimmerAnimation();



        ytplayerrcl = view.findViewById(R.id.ytplayerRcl);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ytplayerrcl.setHasFixedSize(true);
        ytplayerrcl.setLayoutManager(linearLayoutManager);

        getLink();

        return view;
    }

    private void getLink() {

        String webserviceurl = Common.GetWebServiceURL() + "getfeedpagelink.php";
        StringRequest request = new StringRequest(webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                feedlinkmodel data[] = gson.fromJson(response,feedlinkmodel[].class);

                adapter = new feedVideoAdapter(data,getContext());
                ytplayerrcl.setAdapter(adapter);
                shimmerFrameLayout.setVisibility(View.GONE);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


    }
}