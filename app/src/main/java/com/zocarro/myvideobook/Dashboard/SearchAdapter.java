package com.zocarro.myvideobook.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.List;

class SearchAdapter extends RecyclerView.Adapter
{
    Context ctx;
    List<SearchQuery> courselistModelList;

    public SearchAdapter(Context ctx, List<SearchQuery> courselistModelList)
    {
        this.ctx = ctx;
        this.courselistModelList = courselistModelList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_search_row,null);
        WishListHolder container=new WishListHolder(myview);
        return  container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final SearchQuery courseListModel=courselistModelList.get(position);
        WishListHolder container=(WishListHolder) holder;

        container.courseName.setText(courseListModel.getTitle());
        container.courseRating.setText(courseListModel.getRating());
        container.courseUsers.setText(courseListModel.getUsers());
        container.subjectCode.setText(courseListModel.getSubjectCode());
        container.university.setText(courseListModel.getUniversity());
    container.relativeLayout.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(ctx.getApplicationContext(), CourseActivity.class);
            intent.putExtra("course_id",courseListModel.getCourse_id());
            ctx.startActivity(intent);
        }
    });
        String previewUrl= Common.GetBaseImageURL()+"src/course/" + courseListModel.getImageView();
        Log.d("IMGGG",previewUrl);
       Glide.with(ctx).load(previewUrl).into(container.courseImg);
    }

    @Override
    public int getItemCount()
    {
        return courselistModelList.size();
    }

    class WishListHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout relativeLayout;
        ImageView courseImg;
        TextView courseName,courseRating,courseUsers, university, subjectCode;
        public WishListHolder(@NonNull View itemView)
        {
            super(itemView);
            courseImg = itemView.findViewById(R.id.imageView);
            courseName = itemView.findViewById(R.id.txtCourseName);
            courseRating = itemView.findViewById(R.id.courseRating);
            courseUsers = itemView.findViewById(R.id.courseUsers);
            university = itemView.findViewById(R.id.universityNameTextView);
            subjectCode = itemView.findViewById(R.id.subjectCodeTextView);
            relativeLayout = itemView.findViewById(R.id.relative);
        }
    }
}
