package com.zocarro.myvideobook.SemesterPackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.google.android.material.button.MaterialButton;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.DashboardMain;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.UserCourses.Mycourse;
import com.zocarro.myvideobook.checksum;
import com.zocarro.myvideobook.courses.CourseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PackageActivity extends AppCompatActivity {

    RecyclerView recPackageCourse;
    ArrayList<PackageCourse> courseArrayList = new ArrayList<>();
    Context ctx=this;
    TextView txtPackageTitle,txtPackageDesc,priceAlertTextView,oldPriceTextView,currentPriceTextView;
    MaterialButton btnBuyNow;
    String pk_id;
    Toolbar toolbar;
    String packageTitle,packageId,packageDesc,packageDiscount,packageOldPrice,packagePrice;
    private static final String TAG = "PackageActivity";
    private boolean isBought=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Package Details");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        packageId = intent.getStringExtra("pk_id");
        packageTitle = intent.getStringExtra("pk_title");
        packageDesc = intent.getStringExtra("pk_desc");
        packageDiscount = intent.getStringExtra("pk_dis");
        packageOldPrice = intent.getStringExtra("pk_oldPrice");
        packagePrice = intent.getStringExtra("pk_Price");
        Log.d(TAG, "onCreate: " + packageId + packageTitle + packageDesc
                + packageDiscount + packageOldPrice + packagePrice);

        allocateMemory();
        getPackageCourse();
        checkPackageStatus();
        txtPackageTitle.setText(packageTitle);
        txtPackageDesc.setText(packageDesc);
        currentPriceTextView.setText(packagePrice + " ₹");
        oldPriceTextView.setText(packageOldPrice + " ₹");
        oldPriceTextView.setPaintFlags(oldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        priceAlertTextView.setText(packageDiscount + " % off");

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBought){
                    buyPackage();
                }
                else {
//                    btnBuyNow.setText("View Courses");
                    Intent intent = new Intent(ctx,Mycourse.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void checkPackageStatus() {
        Common.progressDialogShow(PackageActivity.this);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String u_id = mPrefs.getString("u_id", "none");
        final String courseUrl = Common.GetWebServiceURL() + "package_bought.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, courseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Common.progressDialogDismiss(PackageActivity.this);
                            JSONObject object = new JSONObject(response);
                            String message = object.getString("message");
                            if (message.equalsIgnoreCase("YES")) {
                                isBought= true;
                                btnBuyNow.setText("View Courses");
                            } else {
                                // buy course
                                btnBuyNow.setText("Buy Package");
//                                Toast.makeText(ctx, "NO", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Common.progressDialogDismiss(PackageActivity.this);
                            Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(PackageActivity.this);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("pkg_id", packageId);
                data.put("u_id", u_id);
                Log.d("data###", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PackageActivity.this).add(sr);
    }

    private void buyPackage() {
        Common.progressDialogShow(ctx);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String u_id = mPrefs.getString("u_id", "none");

        final String courseUrl = Common.GetWebServiceURL() + "buyPackage.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, courseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("&&&&&", response);
                            Common.progressDialogDismiss(ctx);
                            JSONObject object = new JSONObject(response);
                            String message = object.getString("message");
                            if (message.equals("Success")){
                                String trans_id=object.getString("transaction_id");
                                Intent intent = new Intent(PackageActivity.this, checksum.class);
                                intent.putExtra("price", ""+packagePrice);
                                intent.putExtra("trans_id", trans_id);
                                intent.putExtra("course_id", packageId);
                                startActivity(intent);
                                //Toast.makeText(ctx, "Course added successfully", Toast.LENGTH_SHORT).show();
                            }
                            else if(message.equals("Already Enrolled")){
                                //already enrolled
                                isBought=true;
                            } else {
                                Toast.makeText(ctx, "ERROR", Toast.LENGTH_SHORT).show();
                            }
                           /* else {
                                Toast.makeText(ctx, "ERROR", Toast.LENGTH_SHORT).show();
                            }*/
                        } catch (JSONException e) {
                            Common.progressDialogDismiss(ctx);
                            Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("pk_id", packageId);
                data.put("u_id", u_id);
                data.put("price", packageOldPrice);
                data.put("discount", packageDiscount);
                Log.d("data**", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PackageActivity.this).add(sr);

    }

    private void allocateMemory() {
        btnBuyNow = findViewById(R.id.btnBuyNow);
        currentPriceTextView = findViewById(R.id.currentPriceTextView);
        oldPriceTextView = findViewById(R.id.oldPriceTextView);
        priceAlertTextView = findViewById(R.id.priceAlertTextView);
        txtPackageTitle = findViewById(R.id.txtPackageTitle);
        txtPackageDesc = findViewById(R.id.txtPackageDesc);
        recPackageCourse = findViewById(R.id.recCoursedetails);

        LinearLayoutManager layoutManager  = new LinearLayoutManager(ctx);
        recPackageCourse.setLayoutManager(layoutManager);
    }

    private void getPackageCourse(){
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stream_id = mPrefs.getString("stream_id", "none");
        final String b_id = mPrefs.getString("br_id","none");
        final String pr_id = mPrefs.getString("pr_id","none");

        final String courseUrl = Common.GetWebServiceURL() + "packageCourseDetails.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, courseUrl,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        courseArrayList.add(new PackageCourse(object.getString("c_id"),
                                object.getString("course_id"),object.getString("course_name"),
                                object.getString("course_bg"),object.getString("rating"),
                                object.getString("enrolled")));
                    }
                    PackageCourseAdapter adapter = new PackageCourseAdapter(ctx,courseArrayList);
                    recPackageCourse.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("pkg_id", packageId);
                Log.d("PckgId", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PackageActivity.this).add(sr);
    }
}
