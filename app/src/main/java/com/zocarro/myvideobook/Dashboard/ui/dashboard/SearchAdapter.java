package com.zocarro.myvideobook.Dashboard.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.List;

class SearchAdapter extends RecyclerView.Adapter {
    Context ctx;
    List<SearchQuery> courselistModelList;

    public SearchAdapter(Context ctx, List<SearchQuery> courselistModelList) {
        this.ctx = ctx;
        this.courselistModelList = courselistModelList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_search_row,null);
        WishListHolder container=new WishListHolder(myview);
        return  container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SearchQuery courseListModel=courselistModelList.get(position);
        WishListHolder container=(WishListHolder) holder;

        container.courseName.setText(courseListModel.getTitle());
        container.courseRating.setText(courseListModel.getRating());
        container.courseUsers.setText(courseListModel.getUsers());
        String previewUrl= Common.GetBaseImageURL()+ "course/" + courseListModel.getImageView();
        container.courseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, CourseActivity.class);
                i.putExtra("course_id",courseListModel.getCourseid());
                ctx.startActivity(i);

            }
        });
        Log.d("IMGGG",previewUrl);
        Glide.with(ctx).load(previewUrl).into(container.courseImg);

    }

    @Override
    public int getItemCount() {
        return courselistModelList.size();
    }

    class WishListHolder extends RecyclerView.ViewHolder{

        ImageView courseImg;
        CardView courseCard;
        TextView courseName,courseRating,courseUsers;
        public WishListHolder(@NonNull View itemView) {
            super(itemView);

            courseCard = itemView.findViewById(R.id.courseCard);
            courseImg = itemView.findViewById(R.id.imageView);
            courseName = itemView.findViewById(R.id.txtCourseName);
            courseRating = itemView.findViewById(R.id.courseRating);
            courseUsers = itemView.findViewById(R.id.courseUsers);
        }
    }
}
