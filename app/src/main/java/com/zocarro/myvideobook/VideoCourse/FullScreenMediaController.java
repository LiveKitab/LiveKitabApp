package com.zocarro.myvideobook.VideoCourse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import com.zocarro.myvideobook.R;


public class FullScreenMediaController extends MediaController
{
    private ImageButton fullScreen;
    private String isFullScreen;

    public FullScreenMediaController(Context context)
    {
        super(context);
    }
    @Override
    public void setAnchorView(View view)
    {
        super.setAnchorView(view);
        //image button for full screen to be added to media controller
        fullScreen = new ImageButton (super.getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 80;
        addView(fullScreen, params);

        //fullscreen indicator from intent
        isFullScreen =  ((Activity)getContext()).getIntent().
                getStringExtra("fullScreenInd");

        if("y".equals(isFullScreen))
        {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit_black_24dp);
        }else
            {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_white_24dp);
        }

        //add listener to image button to handle full screen and exit full screen events
//        fullScreen.setOnClickListener(new OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = null;
//
//                if("y".equals(isFullScreen))
//                {
//                    intent= new Intent(getContext(), ViewCourseVideo.class);
//                    intent.putExtra("fullScreenInd", "");
//                    intent.putExtra("cid", cid);
//                    intent.putExtra("courseTitle", videoViewModelArrayList.get(ViewCourseVideo.position).getV_title());
//                    intent.putExtra("courseDesc", videoViewModelArrayList.get(ViewCourseVideo.position).getV_des());
//                    intent.putExtra("course_id", videoViewModelArrayList.get(ViewCourseVideo.position).getCourse_id());
//                    intent.putExtra("v_id", videoViewModelArrayList.get(ViewCourseVideo.position).getV_id());
//                    intent.putExtra("v_link", videoViewModelArrayList.get(ViewCourseVideo.position).getV_link());
//                }else{
//
//                    intent= new Intent(getContext(), FullScreenVideoActivity.class);
//                    intent.putExtra("fullScreenInd", "y");
//                    intent.putExtra("cid", cid);
//                    intent.putExtra("courseTitle", videoViewModelArrayList.get(ViewCourseVideo.position).getV_title());
//                    intent.putExtra("courseDesc", videoViewModelArrayList.get(ViewCourseVideo.position).getV_des());
//                    intent.putExtra("course_id", videoViewModelArrayList.get(ViewCourseVideo.position).getCourse_id());
//                    intent.putExtra("v_id", videoViewModelArrayList.get(ViewCourseVideo.position).getV_id());
//                    intent.putExtra("v_link", videoViewModelArrayList.get(ViewCourseVideo.position).getV_link());
//                }
//                ((Activity)getContext()).startActivity(intent);
//            }
//        });
    }
}