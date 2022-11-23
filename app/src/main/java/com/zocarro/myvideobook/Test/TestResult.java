package com.zocarro.myvideobook.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zocarro.myvideobook.Activity.ChapterTestActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestResult extends AppCompatActivity {

    TextView videoNameTextView,scoreTextView,txtoutof;
    Button nextButton,retryButton;
    String vid,chaptertest;
    int score;
    int position,size;
    String mentor_id,subject_id,chapter_id,chapter_name,file;
    String sub_code,sub_name;
    Double percentage;
    String cid, vid_name, course_id, video_id, title, description, videolink, tst_id,v_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        allocatememory();

        cid = getIntent().getStringExtra("cid");
        title = getIntent().getStringExtra("courseTitle");
        description = getIntent().getStringExtra("courseDesc");
        course_id = getIntent().getStringExtra("course_id");
        vid = getIntent().getExtras().getString("nextvideo_id");
        chaptertest = getIntent().getExtras().getString("chaptertest");
//        vid_name = getIntent().getExtras().getString("vid_name");
        position = getIntent().getIntExtra("pos", 0);
        videolink = getIntent().getStringExtra("v_link");
        sub_code = getIntent().getExtras().getString("sub_code");
        vid_name = getIntent().getExtras().getString("vid_name");
        sub_name = getIntent().getExtras().getString("sub_name");
        sub_name = getIntent().getExtras().getString("sub_name");
        file = getIntent().getExtras().getString("file");

//        Log.d("testresultfile",file);

        Intent intent = getIntent();
        mentor_id = intent.getStringExtra("c_id");
        subject_id = intent.getStringExtra("sub_id");
        chapter_id = intent.getStringExtra("chap_id");
        Log.d("testresult",chapter_id);
        chapter_name = intent.getStringExtra("chap_name");

        score = getIntent().getIntExtra("score",0);
        size = getIntent().getIntExtra("size",0);
        tst_id = getIntent().getStringExtra("tst_id");
        v_img = getIntent().getStringExtra("v_img");

        videoNameTextView.setText(vid_name);
        scoreTextView.setText(""+score);
        txtoutof.setText("Out of "+ score +" / "+size);

        Double totalMark = (double)size;
        Double scoredMark = (double)score;
        percentage = scoredMark*100/totalMark;

        if (percentage<50)
        {
            retryButton.setVisibility(View.VISIBLE);
        }
        else {
            nextButton.setVisibility(View.VISIBLE);
        }

        retryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chaptertest.equalsIgnoreCase("chaptertest"))
                {
                    Intent intent=new Intent(TestResult.this, ChapterTestActivity.class);
                    intent.putExtra("c_id", mentor_id);
                    intent.putExtra("sub_id", subject_id);
                    intent.putExtra("chapter_id", chapter_id);
                    intent.putExtra("chapter_name",chapter_name);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(TestResult.this,StartQuizActivity.class);
                    intent.putExtra("c_id",cid);
                    intent.putExtra("courseTitle", title);
                    intent.putExtra("courseDesc", description);
                    intent.putExtra("sub_id", course_id);
                    intent.putExtra("v_id",vid);
                    intent.putExtra("pos", position);
                    intent.putExtra("vid_name", vid_name);
                    intent.putExtra("v_link", videolink);
                    intent.putExtra("chap_id", chapter_id);
                    startActivity(intent);
                    finish();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(chaptertest.equalsIgnoreCase("chaptertest"))
                {
                    Intent intent=new Intent(TestResult.this, ChapterTestActivity.class);
                    intent.putExtra("c_id", mentor_id);
                    intent.putExtra("sub_id", subject_id);
                    intent.putExtra("chapter_id", chapter_id);
                    intent.putExtra("chapter_name",chapter_name);
                    startActivity(intent);
                    finish();
                }
                else {
                    addResult();
                }
            }
        });
    }

    private void addResult()
    {
        Common.progressDialogShow(TestResult.this);
        String Webserviceurl = Common.GetWebServiceURL() + "testResult.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String u_id = preferences.getString("u_id", "none").toUpperCase();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(")(_)(", response);
                Common.progressDialogDismiss(TestResult.this);
                try
                {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success"))
                    {
                            Intent intent=new Intent(TestResult.this, ViewCourseVideo.class);
                            intent.putExtra("v_id",video_id);
                            intent.putExtra("c_id",cid);
                            intent.putExtra("pos", position);
                            intent.putExtra("courseTitle", title);
                            intent.putExtra("courseDesc", description);
                            intent.putExtra("sub_id", course_id);
                            intent.putExtra("file",file);
                            intent.putExtra("v_link", videolink);
                            intent.putExtra("subject_code", sub_code );
                            intent.putExtra("subject_name", sub_name );
                            intent.putExtra("chap_id", chapter_id );
                            Log.d("*****", ""+video_id+cid+position+title+description+course_id+videolink);
//                        intent.putExtra("v_img",v_img );
                            startActivity(intent);
                            finish();
                    }
                }
                catch (JSONException e)
                {
                    Common.progressDialogDismiss(TestResult.this);
                    Toast.makeText(TestResult.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Common.progressDialogDismiss(TestResult.this);
                Log.d("mess", error.toString());
                Toast.makeText(TestResult.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.clear();
                data.put("user_id", u_id);
                data.put("tst_id", tst_id);
                data.put("percentage", String.valueOf( Math.round(percentage)));
                Log.d("WWW", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1));
        Volley.newRequestQueue(TestResult.this).add(request);
    }

    private void allocatememory() {
        videoNameTextView=findViewById(R.id.videoNameTextView);
        scoreTextView=findViewById(R.id.scoreTextView);
        txtoutof=findViewById(R.id.txtoutof);
        nextButton=findViewById(R.id.nextButton);
        retryButton=findViewById(R.id.retryButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}