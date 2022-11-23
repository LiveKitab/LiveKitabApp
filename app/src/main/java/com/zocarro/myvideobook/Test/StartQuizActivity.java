package com.zocarro.myvideobook.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zocarro.myvideobook.R;

public class StartQuizActivity extends AppCompatActivity
{
    Button btnStartQuiz;
    String vid;
    static int score=0;
    String sub_code,sub_name;
    TextView txtVideoName;
    static int position;
    static String cid, vid_name, course_id, video_id, title, description, videolink, videoAttachment,v_img,videoId,chaptertesr,chap_id,file;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        cid = getIntent().getStringExtra("cid");
        title = getIntent().getStringExtra("courseTitle");
        description = getIntent().getStringExtra("courseDesc");
        course_id = getIntent().getStringExtra("course_id");
        vid = getIntent().getExtras().getString("nextvideo_id");
        videolink = getIntent().getStringExtra("v_link");
        vid_name = getIntent().getExtras().getString("vid_name");
        video_id = getIntent().getExtras().getString("test_video_id");
        sub_code = getIntent().getExtras().getString("sub_code");
        chap_id = getIntent().getExtras().getString("chap_id");
        sub_name = getIntent().getExtras().getString("sub_name");
        file = getIntent().getExtras().getString("file");
        Log.d("startquizactivity",file);
        position = getIntent().getIntExtra("pos", 0);
        position = getIntent().getIntExtra("pos", 0);

        Log.d("chap_id",chap_id);

//        Log.d("posion1", String.valueOf(position));
        videoId = getIntent().getStringExtra("v_id");
        btnStartQuiz = findViewById(R.id.startQuizButton);
        txtVideoName = findViewById(R.id.videoNameTextView);

        Log.d("startquizactivity", ""+vid+title+description+course_id+videolink+vid_name+cid+video_id+position);

//        Log.d("VIDNAME", "" + vid_name);
//        Log.d("VIDEOID", "" + vid);
//        Log.d("%%%%^^^&^^^", "" + videolink);

        txtVideoName.setText("" + vid_name);
        btnStartQuiz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(StartQuizActivity.this,TestActivity.class);
                intent1.putExtra("cid",cid );
                intent1.putExtra("courseTitle",title );
                intent1.putExtra("courseDesc",description );
                intent1.putExtra("course_id",course_id );
                intent1.putExtra("nextVideo_id",vid);
                intent1.putExtra("vid_name",vid_name);
                intent1.putExtra("v_link",videolink );
                intent1.putExtra("video_id",video_id);
                intent1.putExtra("pos", position );
                intent1.putExtra("sub_code", sub_code );
                intent1.putExtra("sub_name", sub_name );
                intent1.putExtra("chap_id", chap_id );
                intent1.putExtra("file",file );
                intent1.putExtra("chaptertesr", "viewCourseVideo" );


//                intent1.putExtra("cid",cid );
//                intent1.putExtra("video_id",video_id);
//                intent1.putExtra("vid_name",vid_name );
//                intent1.putExtra("pos",position );
//                intent1.putExtra("courseTitle",title );
//                intent1.putExtra("courseDesc",description );
//                intent1.putExtra("course_id",course_id );
//                intent1.putExtra("nextVideo_id",video_id );
//                intent1.putExtra("v_link",videolink );
//                intent1.putExtra("v_img",v_img );

                startActivity(intent1);
                finish();
            }
        });
    }
}