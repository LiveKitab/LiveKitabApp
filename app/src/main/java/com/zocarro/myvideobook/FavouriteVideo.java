package com.zocarro.myvideobook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.zocarro.myvideobook.VideoCourse.VideoFullscreenActivity;

public class FavouriteVideo extends AppCompatActivity
{
    YouTubePlayerView youTubePlayerView;
    VideoView videoView;
    String VideoLink;
    Toolbar toolbar;

    //   ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_preview);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setTitle("Favourite video");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //    progressBar = findViewById(R.id.progressBar);
        videoView = findViewById(R.id.video_view);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent i = getIntent();
        String videoId = i.getStringExtra("v_id");
        VideoLink = i.getStringExtra("v_link");
        Log.d("v_link",VideoLink);

        youTubePlayerView.setLongClickable(false);
        youTubePlayerView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return true;
            }

        });
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener()
        {
            @Override
            public void onYouTubePlayerEnterFullScreen()
            {
                Intent in = new Intent(FavouriteVideo.this, VideoFullscreenActivity.class);
                in.putExtra("v_link",VideoLink);
                startActivity(in);
            }
            @Override
            public void onYouTubePlayerExitFullScreen()
            {

            }
        });

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "BSckZGliL-Y";
                youTubePlayer.loadVideo(VideoLink, 0);
            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {
                super.onPlaybackQualityChange(youTubePlayer, playbackQuality.MEDIUM);
            }
        });

//        youTubePlayerView.enterFullScreen();

//        final String vimeoVideo = VideoLink;
//        String substr=vimeoVideo.substring(31);
//        Log.d("substr", substr);
//        VimeoExtractor.getInstance().fetchVideoWithIdentifier(substr, null, new OnVimeoExtractionListener()
//        {
//            @Override
//            public void onSuccess(VimeoVideo video)
//            {
//                String hdStream = video.getStreams().get("360p");
//                System.out.println("VIMEO VIDEO STREAM" + hdStream);
//                if (hdStream != null)
//                {
//                    // playVideo(hdStream);
//                    PlayVideo(hdStream);
//                }
//            }
//            @Override
//            public void onFailure(Throwable throwable)
//            {
//
//            }
//        });
//
//    }
//    private void PlayVideo(final String hdStream) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                //videoView.setVideoPath(hdStream).getPlayer().start();
//                MediaController mediacontroller = new MediaController(PlayPreview.this);
//                mediacontroller.setAnchorView(videoView);
//                mediacontroller = new FullScreenMediaController(PlayPreview.this);
//                videoView.setMediaController(mediacontroller);
//                //    System.out.println("Video Play");
//                Toast.makeText(PlayPreview.this, "please wait while we played video", Toast.LENGTH_SHORT).show();
//                //       progressBar.setVisibility(View.VISIBLE);
//                videoView.setBackgroundColor(Color.TRANSPARENT);
//                Uri video = Uri.parse(hdStream);
//                videoView.setVideoURI(video);
//              /* String img_url=Common.GetBaseURL()+""
//               Glide.with(ViewCourseVideo.this).load()*/
//                final MediaController finalMediacontroller = mediacontroller;
//                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        // videoview.seekTo(position);
//                        //    System.out.println("video is ready for playing");
//                        Toast.makeText(PlayPreview.this, "Video is now playing", Toast.LENGTH_SHORT).show();
//                        videoView.requestFocus();
//                        videoView.start();
//                        finalMediacontroller.show(0);
//
//                        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                            @Override
//                            public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
//
//                                }
////                                   bufferingDialog.show();
//                                //      progressBar.setVisibility(View.VISIBLE);
//                                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
//
//                                }
////                                   bufferingDialog.dismiss();
//                                //    progressBar.setVisibility(View.GONE);
//
//                                return false;
//                            }
//                        });
//                    }
//                });
//
//
//                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//
//                        mp.release();
//                        //  System.out.println("Video Finish");
//                        Toast.makeText(PlayPreview.this, "video is finished", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                });
//
//            }
//        });
//    }
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
}
