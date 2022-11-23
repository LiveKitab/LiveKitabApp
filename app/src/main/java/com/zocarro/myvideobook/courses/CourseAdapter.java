package com.zocarro.myvideobook.courses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<SubjectSemester> subjectSemesterArrayList;

    public CourseAdapter(Context ctx, ArrayList<SubjectSemester> subjectSemesterArrayList)
    {
        this.ctx = ctx;
        this.subjectSemesterArrayList = subjectSemesterArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_user_courses, null);
        CourseHolder container = new CourseHolder(myview);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SubjectSemester semesterModel = subjectSemesterArrayList.get(position);
        final CourseHolder container =(CourseHolder) holder;
        container.courseName.setText(semesterModel.getCourse_name());
        container.courseRating.setText(semesterModel.getRating());
        container.courseUsers.setText(semesterModel.getEnrolled());
        String previewUrl= Common.GetBaseImageURL() +"course/" + semesterModel.getCourse_bg();
        Log.d("IMGGG",previewUrl);
        Glide.with(ctx).load(previewUrl).into(container.courseImg);
        container.cardtestview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,CourseActivity.class);
                intent.putExtra("course_id", semesterModel.getCourse_id());
                Log.d("course", semesterModel.getCourse_id());
                ctx.startActivity(intent);

            }
        });

     
    }

    @Override
    public int getItemCount() {
        return subjectSemesterArrayList.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        ImageView courseImg;
        TextView courseName,courseRating,courseUsers;
        MaterialCardView cardtestview;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);


            courseImg = itemView.findViewById(R.id.imageView);
            courseName = itemView.findViewById(R.id.txtCourseName);
            courseRating = itemView.findViewById(R.id.courseRating);
            courseUsers = itemView.findViewById(R.id.courseUsers);
            cardtestview = itemView.findViewById(R.id.cardtestview);
        }
    }
}
