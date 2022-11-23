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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zocarro.myvideobook.Adapter.ChapterVideoAdapter;
import com.zocarro.myvideobook.Adapter.ChapterVideoverticalAdapter;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.VideoCourseAdapter;
import com.zocarro.myvideobook.VideoCourse.VideoViewModel;
import com.zocarro.myvideobook.listener.OnDeleteCartListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChapterVideoDetailsVerticalActivity extends AppCompatActivity
{
    Toolbar toolbar;
    String c_id, subject_id, chapter_id, chapter_name;
    static String sub_id,videolink,video_id;
    String title,description;
    String number;
    RecyclerView chaptervideorcv;
    static ArrayList<VideoViewModel> videoViewModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_video_details_vertical);
        toolbar = findViewById(R.id.toolbar);
        chaptervideorcv = findViewById(R.id.chaptervideorcv);
        Intent intent = getIntent();
        c_id = intent.getStringExtra("c_id");
        subject_id = intent.getStringExtra("sub_id");
        chapter_id = intent.getStringExtra("chapter_id");
        chapter_name = intent.getStringExtra("chapter_name");
        number = intent.getStringExtra("number");
        toolbar.setTitle(chapter_name + " (Chapter-" + number + ")");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getVideo();
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
        Common.progressDialogShow(ChapterVideoDetailsVerticalActivity.this);
        String caturl = Common.GetWebServiceURL() + "chapter_video_vertical.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(ChapterVideoDetailsVerticalActivity.this);
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
                            Toast.makeText(ChapterVideoDetailsVerticalActivity.this, "No video available :(", Toast.LENGTH_SHORT).show();
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
                                        object.getString("link"),object.getString("sub_name"),object.getString("sub_code"),chapter_id,object.getString("isInFav"),object.getString("file")));
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
                                public void onFailure(Throwable throwable)
                                {

                                }
                            });*/

                            ChapterVideoverticalAdapter catAdapter = new ChapterVideoverticalAdapter(ChapterVideoDetailsVerticalActivity.this, videoViewModelArrayList, video_id, true,new OnDeleteCartListener()
                            {
                                @Override
                                public void deletecart()
                                {
                                    getVideo();
                                }
                            });
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ChapterVideoDetailsVerticalActivity.this, LinearLayoutManager.VERTICAL, false);
                            chaptervideorcv.setLayoutManager(layoutManager);
                            chaptervideorcv.setAdapter(catAdapter);
                            catAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                        {
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

                        ChapterVideoverticalAdapter catAdapter = new ChapterVideoverticalAdapter(ChapterVideoDetailsVerticalActivity.this, videoViewModelArrayList, video_id, false,new OnDeleteCartListener()
                        {
                            @Override
                            public void deletecart()
                            {
                                getVideo();
                            }
                        });
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ChapterVideoDetailsVerticalActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        chaptervideorcv.setLayoutManager(layoutManager);
                        chaptervideorcv.setAdapter(catAdapter);
                        catAdapter.notifyDataSetChanged();

                        Toast.makeText(ChapterVideoDetailsVerticalActivity.this, "Not Enrolled", Toast.LENGTH_LONG).show();
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
                    Common.progressDialogDismiss(ChapterVideoDetailsVerticalActivity.this);
                }
            }
        }, error -> Common.progressDialogDismiss(ChapterVideoDetailsVerticalActivity.this))
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
        Volley.newRequestQueue(ChapterVideoDetailsVerticalActivity.this).add(request);
    }

}