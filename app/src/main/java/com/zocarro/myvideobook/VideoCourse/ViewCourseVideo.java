package com.zocarro.myvideobook.VideoCourse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.zocarro.myvideobook.Activity.StudymaterialshowActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.Test.StartQuizActivity;
import com.zocarro.myvideobook.courses.CourseActivity;
import com.zocarro.myvideobook.listener.OnDeleteCartListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewCourseVideo extends AppCompatActivity
{
    RecyclerView recvideonext;
    TextView txtdescrdetails, txtcomment, txtdislikes, txtlikes, txtviewcourse, txttitle, videoAttach;
    static ArrayList<VideoViewModel> videoViewModelArrayList = new ArrayList<>();
    ImageButton dislike, like, comment;
    String videolink,file;
    static String commentStatus, course_id, video_id, title, description, sub_id;
    int isLiked = 0;
    String mentorid;
    YouTubePlayerView youTubePlayerView;
    int isDisliked = 0;
    ImageView videoView_thumbnail;
    static int position,pos;
    Button attendTest, enrollButton;
    String videoAttachment;
    String sub_name,subject_code,chap_id,chap_name;
    int getIsLiked,getIsDisliked;
    private static final String TAG = "ViewCourseVideo";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_video);

        //prevent screenshort and recording
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


        allocatememory();
        Toolbar toolbar = findViewById(R.id.toolbar);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        Intent i = getIntent();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        youTubePlayerView.setLongClickable(false);
        youTubePlayerView.setOnLongClickListener(v -> true);
        sub_name = i.getStringExtra("subject_name");
        subject_code = i.getStringExtra("subject_code");
        position = i.getIntExtra("pos", 0);
        videolink = i.getStringExtra("v_link");
        title = i.getStringExtra("courseTitle");
        description = i.getStringExtra("courseDesc");
        course_id = i.getStringExtra("sub_id");
        video_id = i.getStringExtra("v_id");
        mentorid = i.getStringExtra("c_id");
        chap_id = i.getStringExtra("chap_id");
        chap_name = i.getStringExtra("chap_name");
        file = i.getStringExtra("file");
//        Log.d("cjshchsch",file);
        toolbar.setTitle(sub_name +  "(" +subject_code +")");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setSupportActionBar(toolbar);


//        Log.d("sub_name123",sub_name);
        Log.d("Viewcourse", ""+video_id+mentorid+position+title+description+course_id+videolink);
        Log.d("position", "" + position);
//        cid = i.getStringExtra("cid");
//        Log.d("hdbvhvbdjv",course_id);
//      Log.d("vdvdvg",video_id);
//      Log.d("videolink1234",videolink);
//      videolink = i.getStringExtra("v_link");
//      vid_img = i.getStringExtra("v_link");
        getVideo();
        videoAttach.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ViewCourseVideo.this, StudymaterialshowActivity.class);
                intent.putExtra("file", file);
                Log.d("videoattachment",file);
                startActivity(intent);
            }
        });
        attendTest.setOnClickListener(v ->
        {
            try
            {
                Intent intent=new Intent(ViewCourseVideo.this, StartQuizActivity.class);
//                course_id = videoViewModelArrayList.get(position + 1).getC_id();
//                title = videoViewModelArrayList.get(position + 1).getV_title();
//                description = videoViewModelArrayList.get(position + 1).getV_des();
//                sub_id = videoViewModelArrayList.get(position + 1).getSubject_id();
//                video_id = videoViewModelArrayList.get(position + 1).getV_id();
//                videolink = videoViewModelArrayList.get(position + 1).getLink();
//                intent.putExtra("c_id", course_id);
//                intent.putExtra("courseTitle", title);
//                intent.putExtra("courseDesc", description);
//                intent.putExtra("sub_id", sub_id);
//                intent.putExtra("v_id", video_id);

//                intent.putExtra("v_link", videolink);
                intent.putExtra("cid",videoViewModelArrayList.get(position + 1).getC_id());
                intent.putExtra("courseTitle", videoViewModelArrayList.get(position+1).getV_title());
                intent.putExtra("courseDesc", videoViewModelArrayList.get(position+1).getV_des());
                intent.putExtra("course_id", videoViewModelArrayList.get(position+1).getSubject_id());
                intent.putExtra("vid_name", videoViewModelArrayList.get(position).getV_title());
                intent.putExtra("nextvideo_id",videoViewModelArrayList.get(position+1).getV_id());
                intent.putExtra("test_video_id",videoViewModelArrayList.get(position).getV_id());
                intent.putExtra("v_link", videoViewModelArrayList.get(position+1).getLink());
                intent.putExtra("file", videoViewModelArrayList.get(position+1).getFile());
                intent.putExtra("sub_code",subject_code);
                intent.putExtra("chaptertesr","viewCourseVideo");
                intent.putExtra("chap_id",chap_id);
                Log.d("chap_id",chap_id);
                intent.putExtra("sub_name",sub_name);
                intent.putExtra("pos", position + 1);
//                intent.putExtra("pos", position+1);
//                Log.d("posion1", String.valueOf(pos));
//                    intent.putExtra("v_img", videoViewModelArrayList.get(pos+1).get);
                startActivity(intent);
                finish();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });

        enrollButton.setOnClickListener(v ->
        {
                Intent intent = new Intent(ViewCourseVideo.this, CourseActivity.class);
                intent.putExtra("sub_id", sub_id);
                intent.putExtra("cid", mentorid);
                startActivity(intent);
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recvideonext.setLayoutManager(layoutManager);
//        comment = findViewById(R.id.comment);

//        comment.setOnClickListener(v ->
//        {
//            CommentBottomSheetDialog bottomSheet = new CommentBottomSheetDialog();
//            bottomSheet.show(getSupportFragmentManager(), "commentSheet");
//        });

        like.setOnClickListener(v ->
        {
            if (like.getTag().toString().equals("liked"))
            {
                like.setImageResource(R.drawable.ic_like);
                like.setTag("like");
                like.invalidate();
                getIsLiked = getIsLiked - 1;
                txtlikes.setText("" + getIsLiked);
            }
            else {
                like.setImageResource(R.drawable.ic_like_blue);
                like.setTag("liked");
                getIsLiked=getIsLiked + 1;
                txtlikes.setText("" + getIsLiked);
                like.invalidate();
            }
            if (dislike.getTag().toString().equalsIgnoreCase("disliked"))
            {
                if(getIsDisliked>0)
                {
                    dislike.setImageResource(R.drawable.ic_dislike);
                    getIsDisliked=getIsDisliked-1;
                    txtdislikes.setText(""+getIsDisliked);
                    dislike.setTag("dislike");
                }
            }
            else {
                if(getIsDisliked>0)
                {
                    dislike.setImageResource(R.drawable.ic_dislike);
                    /*getIsDisliked=getIsDisliked-1;
                    txtdislikes.setText(""+getIsDisliked);*/
                    dislike.setTag("dislike");
                }
            }
            LikeVideo();
            //videoLike();
        });

        dislike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dislike.getTag().toString().equalsIgnoreCase("disliked"))
                {
                    dislike.setImageResource(R.drawable.ic_dislike);
                    dislike.setTag("dislike");
                    dislike.invalidate();
                    getIsDisliked=getIsDisliked-1;
                    txtdislikes.setText(""+getIsDisliked);
                }
                else
                {
                    dislike.setImageResource(R.drawable.ic_dislike_blue);
                    dislike.setTag("disliked");
                    getIsDisliked=getIsDisliked+1;
                    txtdislikes.setText(""+getIsDisliked);
                    dislike.invalidate();
                }
                if (like.getTag().toString().equalsIgnoreCase("liked"))
                {
                    if(getIsLiked>0)
                    {
                        like.setImageResource(R.drawable.ic_like);
                        like.setTag("like");
                        getIsLiked = getIsLiked - 1;
                        txtlikes.setText("" + getIsLiked);
                    }
                }
                else {
                    if(getIsLiked>0)
                    {
                        like.setImageResource(R.drawable.ic_like);
                        like.setTag("like");
                        /*getIsLiked = getIsLiked - 1;
                        txtlikes.setText("" + getIsLiked);*/
                    }
                }
                DislikeVideo();
                //videoLike();
            }
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener()
        {
            @Override
            public void onYouTubePlayerEnterFullScreen()
            {
                Intent in = new Intent(ViewCourseVideo.this, VideoFullscreenActivity.class);
                in.putExtra("v_link",videolink);
                startActivity(in);
            }
            @Override
            public void onYouTubePlayerExitFullScreen()
            {

            }
        });
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener()
        {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer)
            {
                String videoId = "BSckZGliL-Y";
                try {
                    youTubePlayer.loadVideo(videolink, 0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state)
            {
                super.onStateChange(youTubePlayer, state);
                if (state.equals(PlayerConstants.PlayerState.PLAYING))
                {

                }
                if (state.equals(PlayerConstants.PlayerState.ENDED))
                {
                    if (videoViewModelArrayList.size() != position + 1)
                    {
                        course_id = videoViewModelArrayList.get(position + 1).getC_id();
                        title = videoViewModelArrayList.get(position + 1).getV_title();
                        description = videoViewModelArrayList.get(position + 1).getV_des();
                        sub_id = videoViewModelArrayList.get(position + 1).getSubject_id();
                        video_id = videoViewModelArrayList.get(position + 1).getV_id();
                        videolink = videoViewModelArrayList.get(position + 1).getLink();
                        Intent intent = new Intent(ViewCourseVideo.this, ViewCourseVideo.class);
                        intent.putExtra("c_id", course_id);
                        intent.putExtra("courseTitle", title);
                        intent.putExtra("courseDesc", description);
                        intent.putExtra("subject_name", sub_name);
                        intent.putExtra("subject_code", subject_code);
                        intent.putExtra("sub_id", sub_id);
                        intent.putExtra("v_id", video_id);
                        intent.putExtra("v_link", videolink);
                        intent.putExtra("chap_id", chap_id);
                        intent.putExtra("pos", position + 1);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality)
            {
                super.onPlaybackQualityChange(youTubePlayer, playbackQuality.MEDIUM);
            }
        });


    }
   /* private void PlayVideo(final String hdStream) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //videoView.setVideoPath(hdStream).getPlayer().start();
                            try {
                                videoView.reset();
                                videoView.setVideoURI(Uri.parse(hdStream));
                                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        // not playVideo
                                        // playVideo();
                                        mp.stop();
                                        // VideoVimeo();

                                        if (videoViewModelArrayList.size() == position) {
                                            Toast.makeText(ViewCourseVideo.this,"Congrats you have completed the course!",Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            //mp.start();
                                            Toast.makeText(ViewCourseVideo.this, "Completed", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ViewCourseVideo.this, ViewCourseVideo.class);
                                            intent.putExtra("pos", position);
                                            intent.putExtra("cid", cid);
                                            intent.putExtra("courseTitle", videoViewModelArrayList.get(position).getV_title());
                                            intent.putExtra("courseDesc", videoViewModelArrayList.get(position).getV_des());
                                            intent.putExtra("course_id", videoViewModelArrayList.get(position).getCourse_id());
                                            intent.putExtra("v_id", videoViewModelArrayList.get(position).getV_id());
                                            intent.putExtra("v_link", videoViewModelArrayList.get(position).getV_link());
                                            startActivity(intent);
                                            finish();


                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });




    }*/

//   private void playVideo(final String stream) {
//       runOnUiThread(new Runnable() {
//           @Override
//           public void run() {
//               MediaController mediacontroller = new MediaController(ViewCourseVideo.this);
//               mediacontroller.setAnchorView(videoview);
//               mediacontroller = new FullScreenMediaController(ViewCourseVideo.this);
//               videoview.setMediaController(mediacontroller);
//               Toast.makeText(ViewCourseVideo.this, "Video is playing", Toast.LENGTH_SHORT).show();
//            //   progressBar.setVisibility(View.VISIBLE);
//               videoview.setBackgroundColor(Color.TRANSPARENT);
//               Uri video = Uri.parse(stream);
//               videoview.setVideoURI(video);
//              /* ""
//               Glide.with(ViewCourseVideo.this).load()*/
//               String img_url = Common.GetBaseImageURL()+"editor/video/"+vid_img;
//               Glide.with(getApplicationContext()).load(img_url).into(videoView_thumbnail);
//               Log.d("img_url", img_url);
//               final MediaController finalMediacontroller = mediacontroller;
//               videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
//               {
//                   @Override
//                   public void onPrepared(MediaPlayer mp) {
//                      // videoview.seekTo(position);
//
//                       Toast.makeText(ViewCourseVideo.this, "video is ready for playing", Toast.LENGTH_SHORT).show();
//                   //    System.out.println("video is ready for playing");
//                       videoView_thumbnail.setVisibility(View.GONE);
//                       videoview.requestFocus();
//                       videoview.start();
//                       finalMediacontroller.show(0);
//
//                       mp.setOnInfoListener(new MediaPlayer.OnInfoListener()
//                       {
//                           @Override
//                           public boolean onInfo(MediaPlayer mp, int what, int extra)
//                           {
//                               if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
//                               {
//                                    videoView_thumbnail.setVisibility(View.VISIBLE);
//                               }
////                                   bufferingDialog.show();
//
//                         ///      progressBar.setVisibility(View.VISIBLE);
//                               if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
//                               {
//                                     videoView_thumbnail.setVisibility(View.GONE);
//                               }
////                                   bufferingDialog.dismiss();
//
//                                //   progressBar.setVisibility(View.GONE);
//                               return false;
//                           }
//                       });
//                   }
//               });
//               videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                   @Override
//                   public void onCompletion(MediaPlayer mp) {
//                       mp.release();
//                       System.out.println("Video Finish");
//                       finish();
//                       pos++;
//                       if (videoViewModelArrayList.size() == position) {
//                           Toast.makeText(ViewCourseVideo.this,"Congrats you have completed the course!",Toast.LENGTH_SHORT).show();
//                           finish();
//                       } else {
//                           //mp.start();
//                           Toast.makeText(ViewCourseVideo.this, "Completed", Toast.LENGTH_LONG).show();
//                           Intent intent = new Intent(ViewCourseVideo.this, ViewCourseVideo.class);
//                           intent.putExtra("pos", pos);
//                           intent.putExtra("cid", cid);
//                           intent.putExtra("courseTitle", videoViewModelArrayList.get(position).getV_title());
//                           intent.putExtra("courseDesc", videoViewModelArrayList.get(position).getV_des());
//                           intent.putExtra("course_id", videoViewModelArrayList.get(position).getCourse_id());
//                           intent.putExtra("v_id", videoViewModelArrayList.get(position).getV_id());
//                           intent.putExtra("v_link", videoViewModelArrayList.get(position).getV_link());
//                           startActivity(intent);
//                           finish();
//                       }
//                   }
//               });
//           }
//       });
//
//
//   }
    public void videoLike()
    {
        String videoLikeUrl = Common.GetWebServiceURL() + "likesvideo.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest sr = new StringRequest(StringRequest.Method.POST, videoLikeUrl, response ->
        {
            try
            {
                JSONArray array = new JSONArray(response);
                Log.d("QQQ", response);
                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    object.getString("message");
                    String message = object.getString("message");
                    if (message.equals("Not Enrolled"))
                    {
                        Toast.makeText(ViewCourseVideo.this, "You're not Enrolled in this course", Toast.LENGTH_SHORT).show();
                    }
                    else if (message.equals("Enrolled"))
                    {
                        getIsLiked = Integer.parseInt(object.getString("likes"));
                        getIsDisliked = Integer.parseInt(object.getString("dislikes"));
                        commentStatus = object.getString("comment");
                        isDisliked = object.getInt("isDisliked");
                        isLiked = object.getInt("isLiked");
                        txtlikes.setText(""+getIsLiked);
                        txtdislikes.setText(""+getIsDisliked);
                    }
                }
                if (isLiked == 1) {
                    like.setImageResource(R.drawable.ic_like_blue);
                    dislike.setImageResource(R.drawable.ic_dislike);
                    like.setTag("liked");
                   /* txtlikes.setText(""+getIsLiked);
                    txtdislikes.setText(""+getIsDisliked);*/
                }

                if (isDisliked == 1)
                {
                    dislike.setImageResource(R.drawable.ic_dislike_blue);
                    like.setImageResource(R.drawable.ic_like);
                    dislike.setTag("disliked");
                   /* txtlikes.setText(""+getIsLiked);
                    txtdislikes.setText(""+getIsDisliked);*/
                }
                txtlikes.setText(""+getIsLiked);
                txtdislikes.setText(""+getIsDisliked);
//                txtcomment.setText(commentStatus);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                data.put("course_id", course_id);
                data.put("v_id", video_id);
                data.put("c_id",mentorid);
                Log.d("data12345", data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ViewCourseVideo.this).add(sr);
    }

    private void getVideo()
    {
        Common.progressDialogShow(ViewCourseVideo.this);
        String caturl = Common.GetWebServiceURL() + "Videoview.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(ViewCourseVideo.this);
                    JSONArray array = new JSONArray(response);
                    Log.d("!!!", response);
                    videoViewModelArrayList.clear();
                    String total = array.getJSONObject(0).getString("total");
                    String message = array.getJSONObject(1).getString("message");

                    if (message.equals("Enrolled")) {
                        enrollButton.setVisibility(View.GONE);
                        attendTest.setVisibility(View.VISIBLE);
                        if (total.equals("0")) {
                            Toast.makeText(ViewCourseVideo.this, "No video available :(", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                videoViewModelArrayList.add(new VideoViewModel(
                                        object.getString("c_id"),
                                        sub_id = object.getString("subject_id"),
                                        object.getString("ch_name"),
                                        object.getString("sequence"),
                                        object.getString("v_id"),
                                        object.getString("v_title"),
                                        object.getString("v_des"),
                                        object.getString("link"),
                                        object.getString("sub_name"),
                                        object.getString("sub_code"),
                                        chap_id,object.getString("isInFav"),object.getString("file")));
                                videolink = object.getString("link");
                                video_id = object.getString("v_id");
                            }
                            video_id = videoViewModelArrayList.get(position).getV_id();
                            videolink = videoViewModelArrayList.get(position).getLink();
                            /*
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

                            VideoCourseAdapter catAdapter = new VideoCourseAdapter(ViewCourseVideo.this, videoViewModelArrayList, video_id, true,new OnDeleteCartListener()
                            {
                                @Override
                                public void deletecart()
                                {
                                    getVideo();
                                }
                            });
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ViewCourseVideo.this, LinearLayoutManager.VERTICAL, false);
                            recvideonext.setLayoutManager(layoutManager);
                            recvideonext.setAdapter(catAdapter);
                            catAdapter.notifyDataSetChanged();
                        }
                    }

/*
                    else {
                        for (int i = 2; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            videoViewModelArrayList.add(new VideoViewModel(
                                    object.getString("c_id"),
                                    sub_id = object.getString("subject_id"),
                                    object.getString("ch_name"),
                                    object.getString("sequence"),
                                    video_id = object.getString("v_id"),
                                    title = object.getString("v_title"),
                                    description = object.getString("v_des"),
                                    videolink = object.getString("link"),"NA","NA","NA"));
                        }

                        if (videoViewModelArrayList.size() > 0){
                            video_id = videoViewModelArrayList.get(position).getV_id();
                            videolink = videoViewModelArrayList.get(position).getLink();
                        }

                        txttitle.setText(title);
                        txtdescrdetails.setText(description);

                        enrollButton.setVisibility(View.VISIBLE);
                        attendTest.setVisibility(View.GONE);

                        like.setEnabled(false);
                        like.setClickable(false);

                        dislike.setEnabled(false);
                        dislike.setClickable(false);

                        comment.setEnabled(false);
                        comment.setClickable(false);

                        VideoCourseAdapter catAdapter = new VideoCourseAdapter(ViewCourseVideo.this, videoViewModelArrayList, video_id, false);
                        recvideonext.setAdapter(catAdapter);
                        catAdapter.notifyDataSetChanged();

                        Toast.makeText(ViewCourseVideo.this, "Not Enrolled", Toast.LENGTH_LONG).show();
                    }
*/
                    if (position == 0 && videoViewModelArrayList.size() > 0) {
                        title = videoViewModelArrayList.get(0).getV_title();
                        description = videoViewModelArrayList.get(0).getV_des();
                        video_id = videoViewModelArrayList.get(0).getV_id();
                    }

                    txttitle.setText(title);
                    txtdescrdetails.setText(description);

                    videoLike();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Common.progressDialogDismiss(ViewCourseVideo.this);
                }
            }
        }, error -> Common.progressDialogDismiss(ViewCourseVideo.this)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                data.put("c_id",mentorid);
                data.put("sub_id", course_id);
                data.put("chap_id", chap_id);
                Log.d("###", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ViewCourseVideo.this).add(request);
    }

    private void LikeVideo()
    {
        String caturl = Common.GetWebServiceURL() + "likeVideo.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, response ->
        {
            try
            {
                JSONArray array = new JSONArray(response);
                Log.d("!!!", response);
                String message = array.getJSONObject(0).getString("message");
                if (message.equals("Data updated"))
                {
                    //videoLike();
                    /*if (like.getTag().toString().equals("liked"))
                    {
                        like.setImageResource(R.drawable.ic_like);
                        like.setTag("like");
                        like.invalidate();
                    }
                    else
                    {
                        like.setImageResource(R.drawable.ic_like_blue);
                        like.setTag("liked");
                        like.invalidate();

                    }*/
                }
               /* if (isDisliked == 1) {
                    dislike.setImageResource(R.drawable.ic_dislike);
                    dislike.setTag("dislike");
                } else {
                    like.setImageResource(R.drawable.ic_like);
                    like.setTag("like");
                }
*/
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("user_id", uid);
                data.put("sub_id", course_id);
                data.put("v_id", video_id);
                data.put("mentorid",mentorid);
                Log.d("###1233", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ViewCourseVideo.this).add(request);
    }
    private void DislikeVideo()
    {
        String caturl = Common.GetWebServiceURL() + "dislikeVideo.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, caturl, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray array = new JSONArray(response);
                    Log.d("!!!", response);
                    videoViewModelArrayList.clear();
                    String message = array.getJSONObject(0).getString("message");
                    if (message.equals("Data updated"))
                    {
                      /*  getIsDisliked++;
                        txtdislikes.setText(""+getIsDisliked);*/
                        //videoLike();
                        /*if (dislike.getTag().toString().equalsIgnoreCase("disliked"))
                        {
                            dislike.setImageResource(R.drawable.ic_dislike);
                            dislike.setTag("dislike");
                            dislike.invalidate();
                        }

                        else
                        {
                            dislike.setImageResource(R.drawable.ic_dislike_blue);
                            dislike.setTag("disliked");
                            dislike.invalidate();
                        }
*/

                    }
                   /* if (isLiked == 1) {
                        lik e.setImageResource(R.drawable.ic_like);
                        like.setTag("like");
                    } else {
                        dislike.setImageResource(R.drawable.ic_dislike);
                        dislike.setTag("dislike");
                    }*/
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, error -> {
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("u_id", uid);
                data.put("sub_id", course_id);
                data.put("v_id", video_id);
                data.put("mentorid",mentorid);
                Log.d("###", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ViewCourseVideo.this).add(request);
    }

    private void allocatememory()
    {
      //  progressBar = findViewById(R.id.progressBar);
        recvideonext = findViewById(R.id.recvideonext);
        enrollButton = findViewById(R.id.enrollButton);
        txttitle = findViewById(R.id.txttitlecourse);
        txtdescrdetails = findViewById(R.id.txtdescrdetails);
//        txtcomment = findViewById(R.id.txtcomment);
        txtdislikes = findViewById(R.id.txtdislikes);
        txtlikes = findViewById(R.id.txtlikes);
//        txtviewcourse = findViewById(R.id.txtviewcourse);
//        videoview = findViewById(R.id.video_view);
//        comment = findViewById(R.id.comment);
        like = findViewById(R.id.like);
        dislike = findViewById(R.id.dislike);
        videoAttach = findViewById(R.id.videoAttach);
        attendTest = findViewById(R.id.attendTest);
        videoView_thumbnail = findViewById(R.id.videoView_thumbnail);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
