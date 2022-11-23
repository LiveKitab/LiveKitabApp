package com.zocarro.myvideobook.VideoCourse;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.zocarro.myvideobook.R;

import vimeoextractor.OnVimeoExtractionListener;
import vimeoextractor.VimeoExtractor;
import vimeoextractor.VimeoVideo;

public class FullScreenVideoActivity extends AppCompatActivity {

    VideoView videoView;
    private MediaController mediaController;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);

        //prevent screenshort and recording
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar);

        String fullScreen =  getIntent().getStringExtra("fullScreenInd");
        String videoLink=getIntent().getExtras().getString("v_link");
        if("y".equals(fullScreen)){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //getSupportActionBar().hide();
        }
        final String vimeoVideo = videoLink;
        String substr = vimeoVideo.substring(31);
        Log.d("substr", substr);
        VimeoExtractor.getInstance().fetchVideoWithIdentifier(substr, null, new OnVimeoExtractionListener() {
            @Override
            public void onSuccess(VimeoVideo video) {
                String hdStream = video.getStreams().get("240p");
                System.out.println("VIMEO VIDEO STREAM" + hdStream);
                if (hdStream != null) {
                    playVideo(hdStream);
                   // hdstream = hdStream;
                    // videoPlay(hdStream);
                    //setupVideoView();
                    //PlayVideo(hdStream);

                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        Uri videoUri = Uri.parse(videoLink);


        videoView.setVideoURI(videoUri);

        mediaController = new FullScreenMediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();
    }
    private void playVideo(final String stream) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MediaController mediacontroller = new MediaController(FullScreenVideoActivity.this);
                mediacontroller.setAnchorView(videoView);
                mediacontroller = new FullScreenMediaController(FullScreenVideoActivity.this);
                videoView.setMediaController(mediacontroller);
                System.out.println("Video Play");
                //btn = (Button) findViewById(R.id.fullscreen);
               /* final ProgressDialog bufferingDialog;
                bufferingDialog = ProgressDialog.show(FullScreenVideoActivity.this,
                        "Please wait", "Please wait", true, true);*/

               progressBar.setVisibility(View.VISIBLE);


                videoView.setBackgroundColor(Color.TRANSPARENT);
                Uri video = Uri.parse(stream);
                videoView.setVideoURI(video);
                final MediaController finalMediacontroller = mediacontroller;
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // videoview.seekTo(position);

                        System.out.println("video is ready for playing");

                        videoView.requestFocus();
                        videoView.start();
                        finalMediacontroller.show(0);

                        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                            @Override
                            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                                    progressBar.setVisibility(View.VISIBLE);
                                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                                    progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        });
                    }
                });

               final MediaController finalMediacontroller1 = mediacontroller;
                videoView.setOnTouchListener(new View.OnTouchListener() {
                   @Override
                   public boolean onTouch(View v, MotionEvent event) {
                       if( ((VideoView)v).isPlaying() ) {
                           ((VideoView) v).pause();
                           finalMediacontroller1.show();
                       }
                       else {
                           ((VideoView) v).start();
                           finalMediacontroller1.show();
                       }
                       return true;
                   }
               });
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        System.out.println("Video Finish");
                        finish();

                    }
                });
            }
        });






    }
}
