package com.zocarro.myvideobook.BottomNavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zocarro.myvideobook.Adapter.purchasedsubjectAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Model.purchasedsubject;
import com.zocarro.myvideobook.MyPurchases.Purchases;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class purchasedCourceFragment extends Fragment {



    private final ArrayList<com.zocarro.myvideobook.Model.purchasedsubject> purchasedsubject = new ArrayList<>();

    purchasedsubjectAdapter adapter1;
    TextView viewalltrendingmentor;

    String sub_id;
    RecyclerView categoryRecyclerView;

    LinearLayout oopslnr;
    Button viewcource;

    ViewPager viewPager;
    int currentPage = 0;

    String p_subid;
    LinearLayout purchasedcourse;
    RecyclerView userpuchasedcourse;
    RecyclerView topRatedRec;
    TextView txtp,coursetext;
    String stream_id,br_id,pr_id,term_id;

    private ArrayList<Purchases> list =new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_purchased_cource, container, false);

        oopslnr = view.findViewById(R.id.oopslnr);
        viewcource = view.findViewById(R.id.viewCourse);

        viewcource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , BottomNavigationActivity.class));
            }
        });



        categoryRecyclerView = view.findViewById(R.id.recsubject);
        topRatedRec = view.findViewById(R.id.topRatedRec);
        userpuchasedcourse = view.findViewById(R.id.userpuchasedcourse);
        purchasedcourse = view.findViewById(R.id.purchasedcourse);
        viewalltrendingmentor = view.findViewById(R.id.viewall);
        viewPager = view.findViewById(R.id.viewPager);
        coursetext = view.findViewById(R.id.coursetext);
        txtp = view.findViewById(R.id.txtp);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_img = preferences.getString("userImg", "none");
        Log.d("user_img",user_img);
        stream_id = preferences.getString("stream_id","none");
        br_id = preferences.getString("br_id","none");
        pr_id = preferences.getString("pr_id","none");
        term_id = preferences.getString("term_id","none");

        oopslnr.setVisibility(View.GONE);


        getpurchasedcourse();
        
        return  view;
    }

    private void getpurchasedcourse()
    {

        Common.progressDialogShow(getContext());

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String uid = mPrefs.getString("u_id", "none");

        String url = Common.GetBaseURL() +"homepurchasedcourse.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {

                    Common.progressDialogDismiss(getContext());

                    Log.d("***", response);
                    purchasedsubject.clear();
                    JSONArray array = new JSONArray(response);
                    String message = array.getJSONObject(0).getString("message");
                    if(message.equalsIgnoreCase("Enrolled"))
                    {
                        userpuchasedcourse.setVisibility(View.VISIBLE);
                        purchasedcourse.setVisibility(View.VISIBLE);

                        oopslnr.setVisibility(View.GONE);
                    }
                    else {
                        oopslnr.setVisibility(View.VISIBLE);
                    }
//                    String message = array.getJSONObject(1).getString("message");

//                    if(total>0) {
                    for (int i = 1; i < array.length(); i++)
                    {


                        JSONObject object = array.getJSONObject(i);
                        sub_id = object.getString("sub_id");
                        purchasedsubject.add(new purchasedsubject(object.getString("sub_id"),
                                object.getString("sub_bg"),
                                object.getString("sub_name"),
                                object.getString("cid"),object.getString("c_name"),object.getString("durability")));
                        p_subid = object.getString("sub_id");
                    }
                    adapter1 = new purchasedsubjectAdapter(getContext(), purchasedsubject);
                    userpuchasedcourse.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    userpuchasedcourse.setAdapter(adapter1);
//                    } else {
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id",uid);
                Log.d("data1",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }

}