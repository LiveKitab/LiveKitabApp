package com.zocarro.myvideobook.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Model.coursefeatures;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.courses.CourseVideo;

import java.util.ArrayList;

public class Coursefeatures extends RecyclerView.Adapter
{
    Context ctx;
    ArrayList<coursefeatures> coursefeaturesArrayList;

    public Coursefeatures(Context ctx, ArrayList<coursefeatures> coursefeatures)
    {
        this.ctx = ctx;
        this.coursefeaturesArrayList = coursefeatures;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_course_features, null);
        CourseReviewHolder container = new CourseReviewHolder(myview);
        return container;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final coursefeatures coursefeatures = coursefeaturesArrayList.get(position);
        CourseReviewHolder container =(CourseReviewHolder) holder;
       container.txtfeatures.setText(coursefeatures.getSubjectduration() + " total hours on-demand video");
       container.videos.setText(coursefeatures.getVideo() +" total Video");
       container.test.setText(coursefeatures.getTest() +" total Test");
       container.material.setText(coursefeatures.getMaterial() +" total Material");
       container.days.setText(coursefeatures.getDays() +" total Days");
    }

    @Override
    public int getItemCount() {
        return coursefeaturesArrayList.size();
    }

    class CourseReviewHolder extends RecyclerView.ViewHolder {


        TextView txtfeatures,videos,test,material,days;
//        RatingBar ratingBar;

        public CourseReviewHolder(@NonNull View itemView)
        {
            super(itemView);

            txtfeatures = itemView.findViewById(R.id.txtfeatures);
            videos = itemView.findViewById(R.id.videos);
            test = itemView.findViewById(R.id.test);
            material = itemView.findViewById(R.id.material);
            days = itemView.findViewById(R.id.days);
//            ratingBar = itemView.findViewById(R.id.userRating);

        }
    }
}
