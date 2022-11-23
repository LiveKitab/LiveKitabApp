package com.zocarro.myvideobook.Dashboard;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

public class SearchableActivity extends AppCompatActivity
{
    androidx.appcompat.widget.SearchView searchView;
    public static SearchAdapter adapter;
    RecyclerView recsearch;
    ArrayList<SearchQuery> searchQueries=new ArrayList<>();
    String title,imageview,rating,users, university, subjectCode,id;
    Context ctx=this;
    static String search;
    TextView txtresult;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = findViewById(R.id.toolbarsearch);
        toolbar.setTitle("Search Course");
        toolbar.setTitleTextColor(Color.WHITE);
        recsearch=findViewById(R.id.recsearch);
        txtresult=findViewById(R.id.txtresult);
//        searchView = findViewById(R.id.search_item);
        handleIntent(getIntent());
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
        recsearch.setHasFixedSize(true);
        recsearch.setLayoutManager(new LinearLayoutManager(ctx));
    }
    private void changeCloseIcon(SearchView searchView)
    {
        ImageView closeButtonImage = searchView.findViewById(R.id.search_close_btn);
        closeButtonImage.setImageResource(R.drawable.ic_close_white_24dp);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.serach_item).getActionView();
        //searchView.setBackgroundColor(Color.WHITE);
        changeCloseIcon(searchView);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                search=s;
                searchQueries.clear();
                SendRequest();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s)
            {
                // adapter.getFilter().filter(s);
                return false;
            }
        });
        /*ImageView searchClose = (ImageView) searchView.findViewById(R.id.search_close_btn);
        searchClose.setColorFilter(Color.argb(127, 128, 248, 198));

        searchClose.setAlpha(255);*/

        return true;
    }

    private void SendRequest()
    {
        Common.progressDialogShow(SearchableActivity.this);
        String WebServiceUrl = Common.GetWebServiceURL()+"searchquery.php?search="+search;
        Log.d("abc",WebServiceUrl);
        JsonArrayRequest request = new JsonArrayRequest(WebServiceUrl, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {

                try
                {
                    Common.progressDialogDismiss(SearchableActivity.this);
                    Log.d("shubh", response.toString());
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false)
                    {

                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {
                        int total=response.getJSONObject(1).getInt("total");
                        if (total==0)
                        {
                            txtresult.setVisibility(View.VISIBLE);
                            Toast.makeText(SearchableActivity.this,"No result found",Toast.LENGTH_LONG).show();

                        }
                        else {
                            txtresult.setVisibility(View.GONE);
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
                                id = current.getString("course_id");
                                title = current.getString("course_name");
                                imageview = current.getString("course_bg");
                                rating = current.getString("rating");
                                users = current.getString("enrolled");
                                subjectCode = current.getString("sub_code");
                                university = current.getString("uni");
                                SearchQuery s = new SearchQuery(id,title, imageview, rating, users,subjectCode, university);
                                searchQueries.add(s);
                            }
                            adapter = new SearchAdapter(ctx,searchQueries);
                            recsearch.setItemAnimator(new DefaultItemAnimator());
                            recsearch.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }

                    }

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
        Volley.newRequestQueue(SearchableActivity.this).add(request);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(getIntent());
    }
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
        JsonArrayRequest request = new JsonArrayRequest(WebServiceUrl, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                try {
                    Log.d("hhh", response.toString());
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false)
                    {
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

                            title= current.getString("course_name");
                            imageview = current.getString("course_bg");
                            rating = current.getString("rating");
                            users= current.getString("enrolled");
                            subjectCode= current.getString("sub_code");
                            university= current.getString("uni");
                            SearchQuery s = new SearchQuery(id,title,imageview,rating,users,subjectCode, university);
                            searchQueries.add(s);
                        }
                    }
                    recsearch.setHasFixedSize(true);
                    recsearch.setLayoutManager(new LinearLayoutManager(ctx));
                    adapter = new SearchAdapter(ctx,searchQueries);
                    recsearch.setItemAnimator(new DefaultItemAnimator());
                    recsearch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


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
//                Common.ShowDialog(ctx);
            }

        });
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SearchableActivity.this).add(request);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
