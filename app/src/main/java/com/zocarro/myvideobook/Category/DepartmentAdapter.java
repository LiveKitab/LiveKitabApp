package com.zocarro.myvideobook.Category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {

    ArrayList<Department> list;
    Context mCtx;

    public DepartmentAdapter(final Context mCtx, ArrayList<Department> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_department_cat, parent, false);
        return new DepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {

        Department model =list.get(position);
        final DepartmentViewHolder container = holder;

        String img_url = Common.GetBaseImageURL() + model.getCourse_bg();
        Glide.with(mCtx).load(img_url).into(container.cardImage);
        container.txtCourseTitle.setText(model.getCourse_name());
        container.txtRating.setText(model.getRating());
        container.txtUser.setText(model.getEnrolled());
        container.cardCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mCtx, CourseActivity.class);
                mCtx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {
        CardView cardCourse;
        TextView txtCourseTitle, txtRating, txtUser;
        ImageView cardImage;

        DepartmentViewHolder(View itemView) {
            super(itemView);
            cardCourse = itemView.findViewById(R.id.cardCourse);
            txtCourseTitle = itemView.findViewById(R.id.txtCourseTitle);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtUser = itemView.findViewById(R.id.txtUser);
            cardImage = itemView.findViewById(R.id.cardImage);
        }
    }
}
