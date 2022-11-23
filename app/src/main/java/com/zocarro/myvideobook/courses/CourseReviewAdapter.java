package com.zocarro.myvideobook.courses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import java.util.ArrayList;

public class CourseReviewAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<CourseVideo> courseVideoArrayList;

    public CourseReviewAdapter(Context ctx, ArrayList<CourseVideo> courseVideoArrayList)
    {
        this.ctx = ctx;
        this.courseVideoArrayList = courseVideoArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_course_review, null);
        CourseReviewHolder container = new CourseReviewHolder(myview);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final CourseVideo courseVideo = courseVideoArrayList.get(position);
        CourseReviewHolder container =(CourseReviewHolder) holder;
        container.videotitle.setText(courseVideo.getV_title());
        container.videodescription.setText(courseVideo.getV_des());
//        container.ratingBar.setRating(Float.parseFloat(courseVideo.getUserRating()));
            String  img_url = "http://img.youtube.com/vi/"+courseVideo.getV_link() +"/0.jpg" ;
//            String img_url= Common.GetBaseImageURL()+"src/user/"+ courseVideo.getUserImg();
            Log.d("course", img_url);
            Glide.with(ctx).load(img_url).into(container.videoImg);
    }

    @Override
    public int getItemCount() {
        return courseVideoArrayList.size();
    }

    class CourseReviewHolder extends RecyclerView.ViewHolder {

        ImageView videoImg;
        TextView videotitle,videodescription;
//        RatingBar ratingBar;

        public CourseReviewHolder(@NonNull View itemView) {
            super(itemView);


            videoImg = itemView.findViewById(R.id.videoImg);
            videotitle = itemView.findViewById(R.id.videotitle);
            videodescription = itemView.findViewById(R.id.videodescription);
//            ratingBar = itemView.findViewById(R.id.userRating);

        }
    }
}
