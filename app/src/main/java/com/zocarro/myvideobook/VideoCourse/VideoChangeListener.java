package com.zocarro.myvideobook.VideoCourse;

import android.util.Log;

public interface VideoChangeListener {
    void OnVideoChange(int position, String title, String description, String course_id, String video_id, String mentoridm, String videolink);
}
