package com.zocarro.myvideobook.BottomNavigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Dashboard.Categories;
import com.zocarro.myvideobook.Dashboard.CategoriesAdapter;
import com.zocarro.myvideobook.Dashboard.topRatedCourses;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LiveKitabFragment extends Fragment {

    private final ArrayList<Categories> subCatArrayList = new ArrayList<>();
    CategoriesAdapter adapter;

    String sub_id1;
    RecyclerView categoryRecyclerView;
    private final String[] sliderImageId = new String[3];
    ViewPager viewPager;

    List<topRatedCourses> topRatedCoursesList = new ArrayList<>();
    DrawerLayout drawer;
    TextView txtp,coursetext;

    String stream_id,br_id,pr_id,term_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_live_kitab, container, false);


        categoryRecyclerView = view.findViewById(R.id.recsubject);

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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        getCategories();

        return view;
    }

    private void getCategories()
    {

        Common.progressDialogShow(getContext());

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String uid = mPrefs.getString("u_id", "none");

        String url = Common.GetBaseURL() +"fetch_subjects.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                subCatArrayList.clear();
//                Toast.makeText(getContext() , response , Toast.LENGTH_SHORT).show();

                try
                {
                    Common.progressDialogDismiss(getContext());

                    Log.d("***", response);
                    JSONArray array = new JSONArray(response);
//                    int total = array.getJSONObject(0).getInt("total");
//                    String message = array.getJSONObject(1).getString("message");
//                    if(total>0) {
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        subCatArrayList.add(new Categories(object.getString("sub_id"),
                                object.getString("sub_bg"),
                                object.getString("sub_name"),
                                object.getString("sub_code")));
                        sub_id1 = object.getString("sub_id");
                    }
                    adapter = new CategoriesAdapter(getContext(), subCatArrayList);
                    categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    categoryRecyclerView.setAdapter(adapter);
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
                data.put("stream_id",stream_id);
                data.put("branch_id",br_id);
                data.put("pr_id",pr_id);
                data.put("term_id",term_id);
                data.put("user_id",uid);
                Log.d("data1",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }
}