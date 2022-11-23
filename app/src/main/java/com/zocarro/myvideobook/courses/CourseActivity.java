package com.zocarro.myvideobook.courses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.zocarro.myvideobook.Activity.PaymentActivity;
import com.zocarro.myvideobook.Adapter.Coursefeatures;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Model.coursefeatures;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;
import com.zocarro.myvideobook.checksum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseActivity extends AppCompatActivity
{
    MaterialButton buyNowButton;
    TextView txtCourseName, txtCourseDesc, txtNewPrice, txtOldPrice, txtAlertStmt,priceAlertTextView,txtreview,courserating,enrollstudent,txtCourseRequirementstxt,mentorName,mentoreducation,mentorspec,mentorname1;
    Chip txtRating, txtDuration, txtUsers, txtSubjectCredit, txtSubjectCode;
    ImageView courseImg,coursePlayImg;
    String courseTitle, courseDesc, courseUsers, courseDuration, newPrice, oldPrice,
            purchased,videolink,subjectCode, subjectCredit, courseDiscount,c_name,days,remarks,c_edu,c_spec,img_url1,c_img;
    ChipGroup ChipGroup;
    CircleImageView mentorimg;
    RatingBar courseRatingBar;
    String message,newprice;
    ImageButton btnWishList;
    String videoid,course_id;
    String order_id,transcation_id,bill_id;
    String img_url,img;
    String novideo,notest,nomaterial;
    String trans_id;
    RecyclerView coursedetails;
    ArrayList<CourseVideo> courseVideoArrayList = new ArrayList<>()                       ;
    ArrayList<coursefeatures> coursefeaturesArrayList = new ArrayList<>();
    float np;
    int op, dis;
    String sub_id;
    String mentorid;
    Double courseRating;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        allocatememory();
        getCourse();
        Intent i = getIntent();
        sub_id = i.getStringExtra("sub_id");
        mentorid = i.getStringExtra("cid");
        c_name = i.getStringExtra("c_name");
        novideo =  i.getStringExtra("novideo");
        nomaterial =  i.getStringExtra("nomaterial");
        notest =  i.getStringExtra("notest");
//        Log.d("jhsvc",nomaterial);
//        Log.d("notest",notest);
//        Log.d("videos",novideo);
        mentorName.setText(c_name);
        mentorname1.setText(c_name);

//        Log.d("course_activity", courseid);
//        checkpurchase();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Course Details");
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setSupportActionBar(toolbar);
        coursePlayImg.setOnClickListener(v ->
        {
            Intent intent = new Intent(CourseActivity.this,PlayPreview.class);
            intent.putExtra("v_id", videoid);
            intent.putExtra("v_link", videolink);
            startActivity(intent);
        });
        Glide.with(CourseActivity.this).load(img_url).into(courseImg);

        buyNowButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(CourseActivity.this, PaymentActivity.class);
            intent.putExtra("courseTitle", courseTitle);
            intent.putExtra("transaction_id", transcation_id);
            intent.putExtra("order_id", message);
            intent.putExtra("bill_id", bill_id);
            intent.putExtra("courseDesc", courseDiscount);
            intent.putExtra("subject_id", sub_id);
            intent.putExtra("price", ""+newPrice);
            intent.putExtra("v_id", videoid);
            intent.putExtra("v_link", videolink);
            intent.putExtra("c_id", mentorid);
            intent.putExtra("oldprice", ""+oldPrice);
            startActivity(intent);
        });
        
        btnWishList.setOnClickListener(v -> getWishListStatus());

        if(buyNowButton.getText().equals("View Course"))
        {
            btnWishList.setVisibility(View.GONE);
        }
        else {
            btnWishList.setVisibility(View.VISIBLE);
        }

        getCourseVideos();
        generateorderid();
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

    public void getWishListStatus()
    {
        Common.progressDialogShow(CourseActivity.this);
        String userRegUrl = Common.GetWebServiceURL() + "wishlist.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");

        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, response ->
        {
            try {
                Log.d("***", response);
                Common.progressDialogDismiss(CourseActivity.this);

                JSONObject object = new JSONObject(response);
                String message = object.getString("message");

                if (message.equals("Added to wishlist"))
                {
                    Toast.makeText(CourseActivity.this, "Added To wishList",
                            Toast.LENGTH_SHORT).show();
                    btnWishList.setVisibility(View.GONE);
                } else if (message.equals("Failed to Wishlist"))
                {
                    Toast.makeText(CourseActivity.this, "Failed to WishList",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CourseActivity.this, "Already In wishlist",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(CourseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Common.progressDialogDismiss(CourseActivity.this);
            }
        }, error -> {
            Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            Common.progressDialogDismiss(CourseActivity.this);
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                data.put("sub_id", sub_id);
                data.put("c_id",mentorid);
                Log.d("parammmm", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(CourseActivity.this).add(sr);
    }

//    public void checkpurchase()
//    {
//        String userRegUrl = Common.GetWebServiceURL() + "checkpurchase.php";
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        final String uid = mPrefs.getString("u_id", "none");
//
//        StringRequest sr = new StringRequest(StringRequest.Method.POST, userRegUrl, response -> {
//            try {
//                Log.d("AAA", response);
//                JSONObject object = new JSONObject(response);
//                String message = object.getString("message");
//                if (message.equals("Already Enrolled")) {
//                    Toast.makeText(CourseActivity.this, "Already Enrolled", Toast.LENGTH_SHORT).show();
//                    viewcourse.setOnClickListener(v -> {
//                        Intent intent = new Intent(CourseActivity.this, ViewCourseVideo.class);
//                        intent.putExtra("courseTitle", courseTitle);
//                        intent.putExtra("subject_name", courseTitle);
//                        intent.putExtra("subject_code", subjectCode);
//                        intent.putExtra("courseDesc", courseDesc);
//                        intent.putExtra("sub_id", sub_id);
//                        intent.putExtra("c_id",mentorid);
//                        intent.putExtra("v_id", videoid);
//                        intent.putExtra("v_link", videolink);
////                        intent.putExtra("v_img", img);
//                        startActivity(intent);
//                    });
//
////                      trans_id=object.getString("transaction_id");
////                        intent.putExtra("v_img", img);
//                }
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//                Toast.makeText(CourseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }, error -> {
//            Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            Common.progressDialogDismiss(CourseActivity.this);
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String, String> data = new HashMap<>();
//                data.put("user_id", uid);
//                data.put("course_id", sub_id);
//                data.put("c_id",mentorid);
//                Log.d("parammmm", data.toString());
//                return data;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
//        Volley.newRequestQueue(CourseActivity.this).add(sr);
//    }

    public void alreadyInWishList()
    {
        String courseUrl = Common.GetWebServiceURL() + "alreadyWishlist.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");

        StringRequest sr = new StringRequest(StringRequest.Method.POST, courseUrl, response -> {
            try {
                Common.progressDialogDismiss(CourseActivity.this);
                JSONObject obj = new JSONObject(response);
                String message;
                Log.d("&&&", response);
                    message = obj.getString("message");
                Log.d("mess", message);
                    if(message.equalsIgnoreCase("In"))
                    {
                        btnWishList.setVisibility(View.GONE);
                    }
                    else {
                        btnWishList.setVisibility(View.VISIBLE);
                    }
               /* if (message.equalsIgnoreCase("Not in") && purchased.equalsIgnoreCase("true")) {
                    btnWishList.setVisibility(View.GONE);
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
                Common.progressDialogDismiss(CourseActivity.this);
                Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(CourseActivity.this);
                Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("course_id", sub_id);
                data.put("user_id", uid);
                data.put("c_id",mentorid);
                Log.d("###1", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(CourseActivity.this).add(sr);
    }
    public void getCourse()
    {
        Common.progressDialogShow(CourseActivity.this);
        String courseUrl = Common.GetWebServiceURL() + "courseDetails.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest sr = new StringRequest(StringRequest.Method.POST, courseUrl, response ->
        {
            try {
                Common.progressDialogDismiss(CourseActivity.this);
                JSONArray array = new JSONArray(response);
                Log.d("&&&&", response);
                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    courseTitle = object.getString("sub_name");
                    courseDesc = object.getString("des");
                    oldPrice = object.getString("price");
                    courseRating = object.getDouble("review");
                    courseUsers = object.getString("enrolled");
                    courseDuration = object.getString("sub_duration");
                    purchased = object.getString("purschased");
                    days = object.getString("days");
                    videoid=object.getString("v_id");
                    videolink=object.getString("link");
//                        img = object.getString("v_preview");
//                        subjectCredit = object.getString("credit");
                    subjectCode = object.getString("sub_code");
                    courseDiscount= object.getString("discount");
                    order_id = object.getString("order_id");
                    transcation_id = object.getString("transaction_id");
                    bill_id = object.getString("bill_id");
                    remarks = object.getString("remarks");
                    c_edu = object.getString("c_edu");
                    c_spec = object.getString("c_spec");
                    c_img = object.getString("c_img");
                    newPrice = object.getString("nprice");

                }
                img_url1 = Common.GetBaseImageURL() +"src/creator/"+c_img;
                Glide.with(CourseActivity.this).load(img_url1).into(mentorimg);

                txtCourseName.setText(courseTitle);
                txtCourseDesc.setText(courseDesc);
                mentoreducation.setText(c_edu);
                mentorspec.setText(c_spec);
                txtOldPrice.setText(oldPrice + " ₹");
                enrollstudent.setText("(" +courseUsers + " Student Enroll" + ")");
                img_url="http://img.youtube.com/vi/" + videolink + "/0.jpg";
//                    Log.d("url_course", img_url);
                Glide.with(CourseActivity.this).load(img_url).into(courseImg);
                courserating.setText(courseRating.toString());
//                courseRatingBar.setRating();
                courseRatingBar.setRating(Float.parseFloat(courseRating.toString()));
//                txtRating.setChipText("" + courseRating);
//                txtUsers.setChipText(courseUsers + " Enrolled");
//                txtDuration.setChipText(courseDuration + " hours");
                txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    txtSubjectCode.setText( "Subject Code " + subjectCode );
//                    txtSubjectCredit.setText(subjectCredit + " Credit");

//                op = Integer.parseInt(oldPrice);
//                dis = Integer.parseInt(courseDiscount);
//                np = op - (op * dis) / 100;
                txtNewPrice.setText(newPrice + " ₹");

                priceAlertTextView.setText(courseDiscount + " % off on this course Hurry up!");
//                if (purchased.equals("true"))
//                {
//                    buyNowButton.setVisibility(View.GONE);
//                    viewcourse.setVisibility(View.VISIBLE);
//                    btnWishList.setVisibility(View.GONE);
//                } else {
//                    btnWishList.setVisibility(View.VISIBLE);
//                }

                coursefeaturesArrayList.add(new coursefeatures(courseDuration, notest, novideo, nomaterial,days));
                Coursefeatures adapter = new Coursefeatures(CourseActivity.this, coursefeaturesArrayList);
                coursedetails.setAdapter(adapter);
                coursedetails.addItemDecoration(new DividerItemDecoration(coursedetails.getContext(), DividerItemDecoration.VERTICAL));
                alreadyInWishList();
//                getCourseReview();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(CourseActivity.this);
                Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        },error ->
        {
            Common.progressDialogDismiss(CourseActivity.this);
            Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("course_id", sub_id);
                data.put("user_id", uid);
                data.put("c_id", mentorid);
                Log.d("###", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(CourseActivity.this).add(sr);
    }

    private void getCourseVideos()
    {
       // Common.progressDialogShow(CourseActivity.this);
        String courseUrl = Common.GetWebServiceURL() + "subjectvideo.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest sr = new StringRequest(StringRequest.Method.POST, courseUrl, response ->
        {
            try
            {
                Common.progressDialogDismiss(CourseActivity.this);
                JSONArray array = new JSONArray(response);
                Log.d("ReviewRes", response);
                int total=array.getJSONObject(0).getInt("total");
                if(total==0)
                {
//                    txtreview.setText("No Video yet :( !!");
                }
                else {
//                    recCourseReview.setVisibility(View.VISIBLE);
//                    txtreview.setVisibility(View.GONE);
                    for (int i = 2; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        courseVideoArrayList.add(new CourseVideo(object.getString("v_id"),
                                object.getString("ch_name"),
                                object.getString("v_title"),
                                object.getString("v_des"),
                                object.getString("link")));
                    }
                    CourseReviewAdapter adapter = new CourseReviewAdapter(CourseActivity.this, courseVideoArrayList);
//                    recCourseReview.setAdapter(adapter);
//                    recCourseReview.addItemDecoration(new DividerItemDecoration(recCourseReview.getContext(), DividerItemDecoration.VERTICAL));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Common.progressDialogDismiss(CourseActivity.this);
                Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Common.progressDialogDismiss(CourseActivity.this);
            Toast.makeText(CourseActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("subject_id", sub_id);
                data.put("c_id", mentorid);
                Log.d("Review1", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(CourseActivity.this).add(sr);
    }
    private void generateorderid()
    {
        String ADD_CART_URL = Common.GetBaseURL() + "generateorderid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_CART_URL, response -> {
            try
            {
                Log.d("aaa", response);
                JSONObject object = new JSONObject(response);
                 message = object.getString("order_id");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error ->
        {


        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void allocatememory()
    {
        buyNowButton = findViewById(R.id.buyNowButton);
        txtCourseName = findViewById(R.id.courseNameTextView);
        txtCourseDesc = findViewById(R.id.courseDescriptionTextView);
        txtNewPrice = findViewById(R.id.currentPriceTextView);
//        viewcourse = findViewById(R.id.viewNowButton);
        txtOldPrice = findViewById(R.id.oldPriceTextView);
        priceAlertTextView = findViewById(R.id.priceAlertTextView);
        courseImg = findViewById(R.id.previewImageView);
        btnWishList = findViewById(R.id.wishlistButton);
        coursePlayImg = findViewById(R.id.imgPlaybtn);
        courserating = findViewById(R.id.courserating);
        enrollstudent = findViewById(R.id.enrollstudent);
        courseRatingBar = findViewById(R.id.courseRatingBar);
        coursedetails = findViewById(R.id.coursedetails);
        mentorName = findViewById(R.id.mentorName);
        mentorname1 = findViewById(R.id.mentorname1);
        mentoreducation = findViewById(R.id.mentoreducation);
        mentorimg = findViewById(R.id.mentorimg);
        mentorspec = findViewById(R.id.mentorspec);
//        txtSubjectCode = findViewById(R.id.subjectCodeChip);
//        txtSubjectCredit = findViewById(R.id.subjectCreditChip);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        coursedetails.setLayoutManager(manager);
    }
}