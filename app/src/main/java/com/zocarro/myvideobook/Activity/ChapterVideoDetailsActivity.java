package com.zocarro.myvideobook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Adapter.ChapterVideoAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Feedback.Feedbacks;
import com.zocarro.myvideobook.Feedback.FeedbacksAdapter;
import com.zocarro.myvideobook.Feedback.ViewFeedbacksActivity;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.VideoCourseAdapter;
import com.zocarro.myvideobook.VideoCourse.VideoViewModel;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChapterVideoDetailsActivity extends AppCompatActivity
{
    Toolbar toolbar;
    static String sub_id,videolink,video_id;
    LinearLayout test,study;
    ArrayList<Feedbacks> list = new ArrayList<>();
    static ArrayList<VideoViewModel> videoViewModelArrayList = new ArrayList<>();
    RecyclerView chaptervideorcv,mentor_feedback;
    TextView viewall,txt2;
    String title,description;
    RelativeLayout notclick;
    String c_id, subject_id, chapter_id, chapter_name,enroll,number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_video_details);
        chaptervideorcv = findViewById(R.id.chaptervideorcv);
//        mentor_feedback = findViewById(R.id.mentor_feedback);
//        txt2 = findViewById(R.id.txt2);
        notclick = findViewById(R.id.notclick);
        viewall = findViewById(R.id.viewall);
        toolbar = findViewById(R.id.toolbar);
        test = findViewById(R.id.test);
        study = findViewById(R.id.study);
        Intent intent = getIntent();
        c_id = intent.getStringExtra("c_id");
        subject_id = intent.getStringExtra("sub_id");
        chapter_id = intent.getStringExtra("chapter_id");
        chapter_name = intent.getStringExtra("chapter_name");
        enroll = intent.getStringExtra("enroll");
        number = intent.getStringExtra("number");
        Log.d("enrollornot",enroll);
        getVideo();
        toolbar.setTitle(chapter_name + " (Chapter-" + number + ")");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        getFeedbacks();
        final int[] viewallclick = {0};
        viewall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(enroll.equalsIgnoreCase("Not Enrolled") && viewallclick[0] == 0)
                {
                    viewallclick[0]++;
                    viewall.setClickable(false);
                    Toast.makeText(ChapterVideoDetailsActivity.this, "Please enroll in this course to continue", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(ChapterVideoDetailsActivity.this,ChapterVideoDetailsVerticalActivity.class);
                    intent.putExtra("c_id",c_id);
                    intent.putExtra("sub_id",subject_id);
                    intent.putExtra("chapter_id",chapter_id);
                    intent.putExtra("chapter_name",chapter_name);
                    intent.putExtra("number",number);
                    startActivity(intent);
                }
            }
        });
        test.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(enroll.equalsIgnoreCase("Not Enrolled"))
                {
                    viewallclick[0]++;
                    viewall.setClickable(false);
                    Toast.makeText(ChapterVideoDetailsActivity.this, "Please enroll in this course to continue", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(ChapterVideoDetailsActivity.this,ChapterTestActivity.class);
                    intent.putExtra("c_id",c_id);
                    intent.putExtra("sub_id",subject_id);
                    intent.putExtra("chapter_id",chapter_id);
                    intent.putExtra("chapter_name",chapter_name);
                    intent.putExtra("number",number);
                    startActivity(intent);
                }
            }
        });
        study.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(enroll.equalsIgnoreCase("Not Enrolled"))
                {
                    viewallclick[0]++;
                    viewall.setClickable(false);
                    Toast.makeText(ChapterVideoDetailsActivity.this, "Please enroll in this course to continue", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ChapterVideoDetailsActivity.this, ChapterStudyActivity.class);
                    intent.putExtra("c_id", c_id);
                    intent.putExtra("sub_id", subject_id);
                    intent.putExtra("chapter_id", chapter_id);
                    intent.putExtra("chapter_name", chapter_name);
                    intent.putExtra("number", number);
                    startActivity(intent);
                }
            }
        });
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
    private void getVideo()
    {
        Common.progressDialogShow(ChapterVideoDetailsActivity.this);
        String caturl = Common.GetWebServiceURL() + "chapter_video.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(ChapterVideoDetailsActivity.this);
                    JSONArray array = new JSONArray(response);
                    Log.d("!!!", response);
                    videoViewModelArrayList.clear();
                    String message = array.getJSONObject(1).getString("message");

                    if (message.equals("Enrolled") )
                    {
//                        enrollButton.setVisibility(View.GONE);
//                        attendTest.setVisibility(View.VISIBLE);
                        String total = array.getJSONObject(0).getString("total");
                        if(total.equals("0"))
                        {
                            Toast.makeText(ChapterVideoDetailsActivity.this, "No video available :(", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            for (int i = 2; i < array.length(); i++)
                            {
                                JSONObject object = array.getJSONObject(i);
                                videoViewModelArrayList.add(new VideoViewModel(
                                        object.getString("c_id"),
                                        sub_id = object.getString("subject_id"),
                                        object.getString("ch_name"),
                                        object.getString("sequence"),
                                        object.getString("v_id"),
                                        object.getString("v_title"),
                                        object.getString("v_des"),
                                        object.getString("link"),object.getString("sub_name"),object.getString("sub_code"),chapter_id,"NA",object.getString("file")));
                                videolink = object.getString("link");
                                video_id = object.getString("v_id");
                            }

//                            video_id = videoViewModelArrayList.get(position).getV_id();
//                            videolink = videoViewModelArrayList.get(position).getLink();

                            /*videoAttachment = videoViewModelArrayList.get(0).getV_attach();
                            videoAttach.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ViewCourseVideo.this, AttachmentView.class);
                                    intent.putExtra("doc", videoAttachment);
                                    startActivity(intent);
                                }
                            });
                            final String vimeoVideo = videolink;
                            String substr = vimeoVideo.substring(31);
                            Log.d("substr", substr);
                            VimeoExtractor.getInstance().fetchVideoWithIdentifier(substr, null, new OnVimeoExtractionListener() {
                                @Override
                                public void onSuccess(VimeoVideo video) {
                                    String hdStream = video.getStreams().get("360p");
                                    System.out.println("VIMEO VIDEO STREAM" + hdStream);
                                    if (hdStream != null)
                                    {
//                                        playVideo(hdStream);
                                        hdstream = hdStream;
                                        // videoPlay(hdStream);
                                        //setupVideoView();
                                        //PlayVideo(hdStream);
                                    }
                                }

                                @Override
                                public void onFailure(Throwable throwable) {

                                }
                            });*/
                            ChapterVideoAdapter catAdapter = new ChapterVideoAdapter(ChapterVideoDetailsActivity.this, videoViewModelArrayList, video_id, true);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ChapterVideoDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            chaptervideorcv.setLayoutManager(layoutManager);
                            chaptervideorcv.setAdapter(catAdapter);
                            catAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        for (int i = 2; i < array.length(); i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            videoViewModelArrayList.add(new VideoViewModel(
                                    object.getString("c_id"),
                                    sub_id = object.getString("subject_id"),
                                    object.getString("ch_name"),
                                    object.getString("sequence"),
                                    video_id = object.getString("v_id"),
                                    title = object.getString("v_title"),
                                    description = object.getString("v_des"),
                                    videolink = object.getString("link"),"NA","NA",chapter_id,"NA",object.getString("file")));
                        }
//                        if (videoViewModelArrayList.size() > 0)
//                        {
//                            video_id = videoViewModelArrayList.get(position).getV_id();
//                            videolink = videoViewModelArrayList.get(position).getLink();
//                        }

//                        txttitle.setText(title);
//                        txtdescrdetails.setText(description);
//
//                        enrollButton.setVisibility(View.VISIBLE);
//                        attendTest.setVisibility(View.GONE);
//
//                        like.setEnabled(false);
//                        like.setClickable(false);
//
//                        dislike.setEnabled(false);
//                        dislike.setClickable(false);
//
//                        comment.setEnabled(false);
//                        comment.setClickable(false);

                        ChapterVideoAdapter catAdapter = new ChapterVideoAdapter(ChapterVideoDetailsActivity.this, videoViewModelArrayList, video_id, false);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ChapterVideoDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        chaptervideorcv.setLayoutManager(layoutManager);
                        chaptervideorcv.setAdapter(catAdapter);
                        catAdapter.notifyDataSetChanged();
                        Toast.makeText(ChapterVideoDetailsActivity.this, "Not Enrolled", Toast.LENGTH_LONG).show();
                    }
//                    if (position == 0 && videoViewModelArrayList.size() > 0)
//                    {
//                        title = videoViewModelArrayList.get(0).getV_title();
//                        description = videoViewModelArrayList.get(0).getV_des();
//                        video_id = videoViewModelArrayList.get(0).getV_id();
//                    }

//                    txttitle.setText(title);
//                    txtdescrdetails.setText(description);

//                    videoLike();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(ChapterVideoDetailsActivity.this);
                }
            }
        }, error -> Common.progressDialogDismiss(ChapterVideoDetailsActivity.this))
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                data.put("c_id",c_id);
                data.put("sub_id", subject_id);
                data.put("chap_id", chapter_id);
                Log.d("###", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ChapterVideoDetailsActivity.this).add(request);
    }
    private void getFeedbacks()
    {
        String url = Common.GetWebServiceURL() + "feedbackcourse.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(ChapterVideoDetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray res = new JSONArray(response);
                    int total = res.getJSONObject(0).getInt("total");
                    if (total == 0)
                    {
//                        txt2.setVisibility(View.GONE);
                    }
                    Log.d("!!!", response);
                    list.clear();
                    for (int i=1;i<res.length();i++)
                    {
                        JSONObject object=res.getJSONObject(i);
                        list.add(new Feedbacks(object.getString("user_id"), object.getString("sub_id"),
                                object.getString("rating"), object.getString("review"), object.getString("u_img"),
                                object.getString("username")));
                    }
                    Collections.reverse(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                    mentor_feedback.setLayoutManager(layoutManager);
                    FeedbacksAdapter adapter=new FeedbacksAdapter(ChapterVideoDetailsActivity.this,list);
//                    mentor_feedback.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("@@@", response);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(ChapterVideoDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                Toast.makeText(ChapterVideoDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> HashMapParams = new HashMap<>();
                HashMapParams.put("sub_id", subject_id);
                HashMapParams.put("c_id", c_id);
                Log.v("VVVVV", String.valueOf(HashMapParams));
                return HashMapParams;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}