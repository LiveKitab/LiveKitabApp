package com.zocarro.myvideobook.Test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.zocarro.myvideobook.Activity.ChapterTestActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Dashboard.DashboardMain;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestActivity extends AppCompatActivity
{
    static TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;
    static ArrayList<TestQuestion> testQuestionArrayList = new ArrayList<>();
    ArrayList<FinalAnswer> finalAnswers = new ArrayList<>();
    String tst_id, sub;
    TextView txttimer;
    public static TextView txttotal, txtunanswered, txttotalunanswered, txtposmark, txtnegmark, txtsub;
    String[] mDataset;
    int hours, min;
    int duration, sec;
    long result;
    public static int ansques = 0;
    public static int unanswered = 0;
    private DBHelper mydb;
    Context ctx = this;
    static boolean dataflags = false;
    Handler handler;
    String time;
    int pos;
    String neg;
    String checkactivity;
    String type;
    Button Btnsubmit, btnabort;
    String mentor_id,subject_id,chapter_id,chapter_name,number;
    String vid;
    String sub_code,sub_name;
    static int score=0;
    static int position;
    static String cid, vid_name, course_id, nextVideo_id, title, description, videolink, videoAttachment,v_img,file;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_test);
        if(TestActivity.score>=0)
        {
            TestActivity.score=0;
        }
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        cid = getIntent().getStringExtra("cid");
        title = getIntent().getStringExtra("courseTitle");
        description = getIntent().getStringExtra("courseDesc");
        course_id = getIntent().getStringExtra("course_id");
        nextVideo_id = getIntent().getStringExtra("nextVideo_id");
        vid_name = getIntent().getExtras().getString("vid_name");
        videolink = getIntent().getStringExtra("v_link");
        vid = getIntent().getExtras().getString("video_id");
        position = getIntent().getIntExtra("pos", 0);
        sub_code = getIntent().getExtras().getString("sub_code");
        sub_name = getIntent().getExtras().getString("sub_name");
        checkactivity = getIntent().getExtras().getString("chaptertesr");
        number = getIntent().getExtras().getString("number");
        file = getIntent().getExtras().getString("file");
//Log.d("testactivityfile",file);
        Log.d("checkactivity",checkactivity);

        Intent intent = getIntent();
        mentor_id = intent.getStringExtra("c_id");
        subject_id = intent.getStringExtra("sub_id");
        chapter_id = intent.getStringExtra("chap_id");
        Log.d("chap_id",chapter_id);
        chapter_name = intent.getStringExtra("chap_name");
//        vid_name = getIntent().getExtras().getString("vid_name");
//        position = getIntent().getIntExtra("pos", 0);


//        v_img = getIntent().getStringExtra("v_img");
//        Log.d("Video_id", "" +vid);
//        Log.d("%%%%^^^&^^^", "" + title);

        SendRequest();

        allocatememory();
        mydb = new DBHelper(ctx);

        Btnsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to submit test?..");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                if(checkactivity.equalsIgnoreCase("chaptertest"))
                                {
                                    Intent intent = new Intent(TestActivity.this,TestResult.class);
                                    intent.putExtra("courseTitle", title);
                                    intent.putExtra("courseDesc", description);
                                    intent.putExtra("score", score);
                                    intent.putExtra("chaptertest","chaptertest");
                                    intent.putExtra("c_id",mentor_id);
                                    intent.putExtra("sub_id",subject_id);
                                    intent.putExtra("chap_id",chapter_id);
                                    intent.putExtra("chap_name",chapter_name);
                                    intent.putExtra("size",pos );
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Intent intent=new Intent(TestActivity.this,TestResult.class);
                                    intent.putExtra("cid",cid);
                                    intent.putExtra("courseTitle", title);
                                    intent.putExtra("courseDesc", description);
                                    intent.putExtra("course_id", course_id);
                                    intent.putExtra("chap_id", chapter_id);
//                                intent.putExtra("vid_name",vid_name );
                                    intent.putExtra("vid_name",vid_name);
                                    intent.putExtra("nextvideo_id",nextVideo_id);
                                    intent.putExtra("pos", position);
                                    intent.putExtra("file",file);
                                    intent.putExtra("v_link", videolink);
                                    Log.d("%%%%^^^&^^^", ""+videolink);
                                    intent.putExtra("chaptertest","viewCourseVideo");
                                    intent.putExtra("score", score);
                                    intent.putExtra("size",pos );
                                    intent.putExtra("tst_id",tst_id );
                                    intent.putExtra("v_img",v_img );
                                    intent.putExtra("sub_code", sub_code );
                                    intent.putExtra("sub_name", sub_name );
                                    startActivity(intent);
                                    finish();

                                }
//                                saveData();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        btnabort.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                if(checkactivity.equalsIgnoreCase("chaptertest"))
                                {
                                    Intent intent = new Intent(TestActivity.this, ChapterTestActivity.class);
                                    intent.putExtra("c_id", mentor_id);
                                    intent.putExtra("sub_id", subject_id);
                                    intent.putExtra("chap_id", chapter_id);
                                    intent.putExtra("chapter_name",chapter_name);
                                    intent.putExtra("number",number);
                                    intent.putExtra("size",pos );
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Intent intent=new Intent(TestActivity.this, ViewCourseVideo.class);
                                    intent.putExtra("v_id",nextVideo_id);
                                    intent.putExtra("c_id",cid);
                                    intent.putExtra("pos", position);
                                    intent.putExtra("courseTitle", title);
                                    intent.putExtra("chap_id", chapter_id);
                                    intent.putExtra("courseDesc", description);
                                    intent.putExtra("sub_id", course_id);
                                    intent.putExtra("v_link", videolink);
                                    intent.putExtra("subject_code", sub_code );
                                    intent.putExtra("subject_name", sub_name );

                                /*intent.putExtra("v_id",video_id);
                                intent.putExtra("cid",cid);
                                intent.putExtra("pos", position);
                                intent.putExtra("courseTitle", title);
                                intent.putExtra("courseDesc", description);
                                intent.putExtra("course_id", course_id);
                                intent.putExtra("v_link", videolink);
                                Log.d("next video", title);
                                intent.putExtra("v_img",v_img );*/
                                    startActivity(intent);
                                    finish();
                                }


                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }

    private void saveData()
    {
        Common.progressDialogShow(TestActivity.this);
        finalAnswers.clear();
        ArrayList<TestAnswer> answers = mydb.getAllTestData(tst_id);
        final String test = new Gson().toJson(answers);
        Log.d("test", test);
        String Webserviceurl = Common.GetWebServiceURL() + "submitTest.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String stu_id = preferences.getString("stu_id", "none").toUpperCase();
        final String class_id = preferences.getString("class_id", "none").toUpperCase();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("!!!", response);
                Common.progressDialogDismiss(TestActivity.this);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("fail")) {
                        Toast.makeText(TestActivity.this, "Sorry!! Test is no Submitted", Toast.LENGTH_LONG).show();

                    } else if (object.getString("message").equals("Success"))
                    {
                        Toast.makeText(TestActivity.this, "Test Submitted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TestActivity.this, ViewCourseVideo.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    // Toast.makeText(TestTine.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(TestActivity.this);
                Log.d("mess", error.toString());
                Toast.makeText(TestActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.clear();
                data.put("test", test);
                data.put("sc_id", sc_id);
                data.put("stu_id", stu_id);
                data.put("cid", class_id);
                data.put("tst_id", tst_id);
                Log.d("PPP", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1));
        Volley.newRequestQueue(TestActivity.this).add(request);
    }



    private void SendRequest()
    {
        Common.progressDialogShow(this);
        String Webserviceurl = Common.GetWebServiceURL() + "test_question_fetch.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String u_id = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    Common.progressDialogDismiss(TestActivity.this);
                    Log.d("aaa", response);
                    testQuestionArrayList.clear();
                    JSONArray array = new JSONArray(response);
                    int total = array.getJSONObject(0).getInt("total");
                    pos=total;
                    if (total == 0) {
                        Toast.makeText(TestActivity.this, "No Question", Toast.LENGTH_LONG).show();
                    } else {
//                         txtquestion.setText(""+total);
                        mDataset = new String[array.length() - 1];
                        for (int i = 1; i < array.length(); i++)
                        {                            /*  {
                            "qid": "QST1",
                                "question": "Test Question ?",
                                "a": "A",
                                "b": "B",
                                "c": "C",
                                "d": "D"
                        },*/
                            JSONObject object = array.getJSONObject(i);
                            tst_id = object.getString("test_id");
                            testQuestionArrayList.add(new TestQuestion(object.getString("que_id"), object.getString("question"), object.getString("a"), object.getString("b"),
                                    object.getString("c"), object.getString("d"), "Not Set",
                                    object.getString("test_id"), false,object.getString("correct")));
                        }
                        for (int j = 0; j < testQuestionArrayList.size(); j++) {
                            if (dataflags == false) {
                                if (mydb.inserttest(testQuestionArrayList.get(j).getTst_id(), testQuestionArrayList.get(j).getQ_id(), testQuestionArrayList.get(j).getQuestion(),
                                        testQuestionArrayList.get(j).getA(), testQuestionArrayList.get(j).getB(), testQuestionArrayList.get(j).getC(), testQuestionArrayList.get(j).getD(), "Not Set", 0)) {

                                }
                            }
                        }
                        for (int k = 0; k < testQuestionArrayList.size(); k++) {
                            tabLayout.addTab(tabLayout.newTab());
                            TextView tv = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_view, null);
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            TestActivity.tabLayout.getTabAt(k).setCustomView(tv).setText("" + (k + 1));
                        }
                        adapter = new TabAdapter
                                (getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
                        {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab)
                            {
                                viewPager.setCurrentItem(tab.getPosition());


                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab)
                            {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab)
                            {

                            }
                        });
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(TestActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("vid", vid);
                Log.d("data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000, 3, 1));
        Volley.newRequestQueue(TestActivity.this).add(request);
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    private void allocatememory()
    {
        Btnsubmit = findViewById(R.id.toolbar_overflow_menu_button);
        btnabort = findViewById(R.id.toolbar_overflow_abort_button);
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        if(checkactivity.equalsIgnoreCase("chaptertest"))
                        {
                            Intent intent = new Intent(TestActivity.this, ChapterTestActivity.class);
                            intent.putExtra("c_id", mentor_id);
                            intent.putExtra("sub_id", subject_id);
                            intent.putExtra("chapter_id", chapter_id);
                            intent.putExtra("chapter_name",chapter_name);
                            intent.putExtra("number",number);
                            intent.putExtra("size",pos );
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(TestActivity.this, ViewCourseVideo.class);
                            intent.putExtra("v_id",nextVideo_id);
                            intent.putExtra("c_id",cid);
                            intent.putExtra("pos", position);
                            intent.putExtra("courseTitle", title);
                            intent.putExtra("courseDesc", description);
                            intent.putExtra("sub_id", course_id);
                            intent.putExtra("v_link", videolink);
                            intent.putExtra("subject_code", sub_code );
                            intent.putExtra("subject_name", sub_name );
                            intent.putExtra("chap_id", chapter_id);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                alertDialogBuilder.setCancelable(true);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
