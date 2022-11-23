package com.zocarro.myvideobook.Dashboard.ui.dashboard;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    public static SearchAdapter adapter;
    RecyclerView recsearch;
    private DashboardViewModel dashboardViewModel;
    SearchView searchView;
    Context ctx=getContext();
    ProgressDialog progressDialog;
    static String search;
    ArrayList<SearchQuery> searchQueries=new ArrayList<>();
    String title,imageview,rating,users,courseDesc,videoid,courseid,videolink,videoAttachment;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        progressDialog = new ProgressDialog(getActivity());

        searchView = root.findViewById(R.id.action_search);
        recsearch=root.findViewById(R.id.recSearch);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recsearch.setHasFixedSize(true);
        recsearch.setLayoutManager(new LinearLayoutManager(ctx));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search=s;
                Log.d("aaa",search);
                searchQueries.clear();
                SendRequest();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return root;

    }

    private void SendRequest() {
        progressDialog.setTitle("Searching");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String WebServiceUrl = Common.GetWebServiceURL()+"searchquery.php?search="+search;
        Log.d("abc",WebServiceUrl);
        JsonArrayRequest request = new JsonArrayRequest(WebServiceUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.d("shubh", response.toString());
                    progressDialog.dismiss();
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {
                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {
                        int total=response.getJSONObject(1).getInt("total");
                        if (total==0)
                        {
//                            Toast.makeText(SearchableActivity.this,"No result found",Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"No result found!",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            int asize = response.length();
                            for (int i = 2; i < asize; i++) {
                                        /*
                                       {
                                    "s_img": "90335501021.jpg",
                                    "sin": "BSIN1",
                                    "s_id": "SSID1",
                                    "label": "book",
                                    "ss_name": "Book Mania"
                                  }
                                  String sin, String ssid, String name, String image, String ssname
                             */
                                JSONObject current = response.getJSONObject(i);
                                title = current.getString("course_name");
                                imageview = current.getString("course_bg");
                                rating = current.getString("rating");
                                users = current.getString("enrolled");
                                courseDesc = current.getString("course_des");
                                courseid = current.getString("course_id");
                                videoid = current.getString("v_id");
                                videolink = current.getString("v_link");
                                videoAttachment = current.getString("v_attach");
                                SearchQuery s = new SearchQuery(title, imageview, rating, users,courseDesc,courseid,
                                        videoid,videolink,videoAttachment);
                                searchQueries.add(s);
                            }
                            if (getActivity()!=null){
                                adapter = new SearchAdapter(getActivity(),searchQueries);
                                recsearch.setItemAnimator(new DefaultItemAnimator());
                                recsearch.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Common.ShowDialog(ctx);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(request);


    }
   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(getIntent());
    }*/
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            showResults(query);
        }
    }
    private void showResults(String query) {
        // Query your data set and show results
        // ...

        String WebServiceUrl = Common.GetWebServiceURL()+"searchquery.php?search="+query;
        //Log.d("abc",WebServiceUrl);
        JsonArrayRequest request = new JsonArrayRequest(WebServiceUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.d("hhh", response.toString());
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {
                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {

                        int asize = response.length();
                        for (int i = 1; i < asize; i++) {
                                        /*
                                        {
                                    "sin": "TSIN5",
                                    "s_id": "SSID1",
                                    "label": "dmsjdk"
                                  }
                             */
                            JSONObject current = response.getJSONObject(i);
                            title = current.getString("course_name");
                            imageview = current.getString("course_bg");
                            rating = current.getString("rating");
                            users = current.getString("enrolled");
                            courseDesc = current.getString("course_des");
                            courseid = current.getString("course_id");
                            videoid = current.getString("v_id");
                            videolink = current.getString("v_link");
                            videoAttachment = current.getString("v_attach");
                            SearchQuery s = new SearchQuery(title, imageview, rating, users,courseDesc,courseid,
                                    videoid,videolink,videoAttachment);
                            searchQueries.add(s);
                        }

                    }
                    recsearch.setHasFixedSize(true);
                    recsearch.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new SearchAdapter(ctx,searchQueries);
                    recsearch.setItemAnimator(new DefaultItemAnimator());
                    recsearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Common.ShowDialog(ctx);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(getActivity()).add(request);


    }


}