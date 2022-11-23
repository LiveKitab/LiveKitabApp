package com.zocarro.myvideobook.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.List;

public class topCreatorsAdapter extends RecyclerView.Adapter {

    Context ctx;
    List<topRatedCreators> topRatedCreatorsList;

    public topCreatorsAdapter(Context ctx, List<topRatedCreators> topRatedCreatorsList) {
        this.ctx = ctx;
        this.topRatedCreatorsList = topRatedCreatorsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_toprated_creator,null);
        CreatorsHolder container = new CreatorsHolder(myview);
        return container;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final topRatedCreators topratedcreators = topRatedCreatorsList.get(position);
        CreatorsHolder container =(CreatorsHolder) holder;

        container.courseTitle.setText(topratedcreators.getCourseTitle());
        container.courseRating.setText(topratedcreators.getCourseRating());
        container.courseUser.setText(topratedcreators.getCourseUser());
        container.cardtopcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, CourseActivity.class);
//                intent.putExtra("course_id",topratedcreators.getCourse_Id());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topRatedCreatorsList.size();
    }

    class CreatorsHolder extends RecyclerView.ViewHolder{
        TextView courseTitle,courseRating,courseUser;
        MaterialCardView cardtopcourse;

        public CreatorsHolder(@NonNull View itemView) {
            super(itemView);

            courseTitle = itemView.findViewById(R.id.txtCourseTitle);
            courseRating = itemView.findViewById(R.id.txtRating);
            courseUser = itemView.findViewById(R.id.txtUser);
            cardtopcourse=itemView.findViewById(R.id.cardtopcourse);
        }
    }
}
