package com.zocarro.myvideobook.UserCourses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter {

    Context ctx;
    List<CourseListModel> courselistModelList;

    public MyCoursesAdapter(Context ctx, List<CourseListModel> courselistModelList) {
        this.ctx = ctx;
        this.courselistModelList = courselistModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_my_courses,null);
        WishListHolder container=new WishListHolder(myview);
        return  container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CourseListModel courseListModel=courselistModelList.get(position);
        WishListHolder container=(WishListHolder) holder;

        container.courseName.setText(courseListModel.getTitle());
        String previewUrl= Common.GetBaseImageURL() +"src/course/" + courseListModel.getImageView();
        Log.d("IMGGG",previewUrl);
        container.subjectCode.setText(courseListModel.getSub_code());
        Glide.with(ctx).load(previewUrl).into(container.courseImg);
        container.cardtestview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, CourseActivity.class);
                intent.putExtra("course_id", courseListModel.getCourse_id());
                ctx.startActivity(intent);
            }
        });

        int count = Integer.parseInt(courseListModel.getCount());
        int total = Integer.parseInt(courseListModel.getTotal());

        try {
            int progress = (total*100) / count ;
            container.courseProgressbar.setProgress(progress);
            container.txt100percent.setText(courseListModel.getCount());
            container.txtCourseProgress.setText(courseListModel.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return courselistModelList.size();
    }

    class WishListHolder extends RecyclerView.ViewHolder{

        ImageView courseImg;
        ProgressBar courseProgressbar;
        TextView courseName, txtCourseProgress ,txt100percent;
        MaterialCardView cardtestview;
        TextView subjectCode;
        public WishListHolder(@NonNull View itemView) {
            super(itemView);

            courseImg = itemView.findViewById(R.id.imageView);
            courseName = itemView.findViewById(R.id.txtCourseName);
            cardtestview = itemView.findViewById(R.id.cardtestview);
            courseProgressbar = itemView.findViewById(R.id.courseProgressbar);
            txt100percent = itemView.findViewById(R.id.txt100percent);
            txtCourseProgress = itemView.findViewById(R.id.txtCourseProgress);
            subjectCode = itemView.findViewById(R.id.txtSubjectCode);
        }
    }
}