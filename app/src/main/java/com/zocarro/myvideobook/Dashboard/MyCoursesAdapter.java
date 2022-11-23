package com.zocarro.myvideobook.Dashboard;

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
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.List;

public class MyCoursesAdapter extends RecyclerView.Adapter {

    Context ctx;
    List<MyCourses> lastViewCoursesList;

    public MyCoursesAdapter(Context ctx, List<MyCourses> lastViewCoursesList) {
        this.ctx = ctx;
        this.lastViewCoursesList = lastViewCoursesList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_my_courses,null);
        LastViewContent container = new LastViewContent(myview);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyCourses lastviewcourses = lastViewCoursesList.get(position);
        LastViewContent container =(LastViewContent) holder;

        container.courseTitle.setText(lastviewcourses.getCourse_name());
        container.courseRating.setText(lastviewcourses.getRating());
        container.relCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, CourseActivity.class);
                i.putExtra("course_id", lastviewcourses.getCourse_id());
                ctx.startActivity(i);
            }
        });
        String previewUrl= Common.GetBaseImageURL() +"course/"+ lastviewcourses.getCourse_bg();
        Log.d("IMG",previewUrl);
        Glide.with(ctx).load(previewUrl).into(container.cardimageView);

        container.courseUser.setText(lastviewcourses.getEnrolled());
    }

    @Override
    public int getItemCount() {
        return lastViewCoursesList.size();
    }

    class LastViewContent extends RecyclerView.ViewHolder{

        TextView courseTitle,courseRating,courseUser;
        ImageView cardimageView;
        RelativeLayout relCard;

        public LastViewContent(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.txtCourseTitle);
            courseRating = itemView.findViewById(R.id.txtRating);
            courseUser = itemView.findViewById(R.id.txtUser);
//            cardimageView = itemView.findViewById(R.id.cardImageView);
            relCard = itemView.findViewById(R.id.relCard);
        }
    }
}
