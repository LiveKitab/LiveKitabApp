package com.zocarro.myvideobook.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zocarro.myvideobook.Activity.MentorChapterActivity;
import com.zocarro.myvideobook.Activity.TrendingMentorsubjectActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;
import com.zocarro.myvideobook.courses.CourseActivity;
import java.util.List;

public class topCoursesAdapter extends RecyclerView.Adapter<topCoursesAdapter.CourseContentHolder>
{

    Context ctx;
    List<topRatedCourses> topRatedCourseList;

    public topCoursesAdapter(Context ctx, List<topRatedCourses> topRatedCourseList)
    {
        this.ctx = ctx;
        this.topRatedCourseList = topRatedCourseList;
    }
    @NonNull
    @Override
    public CourseContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_toprated_course,null);
        CourseContentHolder container = new CourseContentHolder(myview);
        return container;
    }
    @Override
    public void onBindViewHolder(@NonNull CourseContentHolder holder, int position)
    {
        final topRatedCourses model = topRatedCourseList.get(position);
        holder.textViewMentorName.setText(model.getM_name());
//        holder.authorname.setText(model.getSub_name());
        holder.productRatingBar.setRating(Float.parseFloat(model.getRating()));
        holder.ratingTextView.setText(model.getRating());
        String image_url = Common.GetBaseImageURL() +"src/creator/"+model.getC_img();
        Log.d("TAG", "onBindViewHolder: " + image_url);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.mentorImageView.setDefaultImageResId(R.drawable.videobook_logo);
        holder.mentorImageView.setErrorImageResId(R.drawable.videobook_logo);
        holder.mentorImageView.setImageUrl(image_url, imageLoader);
//        holder.txtbullet.setText("\u2022");
//        holder.videosno.setText(model.getNo_of_video());
//        holder.testno.setText(model.getNo_of_test());

        holder.shopCardview.setOnClickListener(v ->
        {
//            Intent intent = new Intent(ctx, CourseActivity.class);
//            intent.putExtra("sub_id",model.getSub_id());
//            intent.putExtra("cid",model.getC_id());
//            Log.d("subject_id",model.getSub_id());
//            ctx.startActivity(intent);

//            Intent intent=new Intent(ctx, ViewCourseVideo.class);
//            intent.putExtra("c_id", model.getC_id());
//            intent.putExtra("courseTitle",model.getM_name());
//            intent.putExtra("subject_name",model.getSub_name());
//            intent.putExtra("subject_code",model.getSub_code());
//            intent.putExtra("courseDesc", model.getSub_des());
//            intent.putExtra("sub_id", model.getSub_id());
//            intent.putExtra("v_id", model.getVno());

            Intent intent = new Intent(ctx, TrendingMentorsubjectActivity.class);
            intent.putExtra("cid",model.getC_id());
            intent.putExtra("c_name",model.getM_name());
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount()
    {
        return topRatedCourseList.size();
    }
    class CourseContentHolder extends RecyclerView.ViewHolder
    {
        TextView textViewMentorName,textViewAddress,ratingTextView,authorname,videosno,txtbullet,testno;
        NetworkImageView mentorImageView;
        RatingBar productRatingBar;
        CardView shopCardview;
        public CourseContentHolder(@NonNull View itemView)
        {
            super(itemView);
            mentorImageView = itemView.findViewById(R.id.mentorImageView);
            textViewMentorName = itemView.findViewById(R.id.textViewMentorName);
//            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            shopCardview = itemView.findViewById(R.id.shopCardview);
            productRatingBar = itemView.findViewById(R.id.productRatingBar);
//            authorname = itemView.findViewById(R.id.authorname);
//            videosno = itemView.findViewById(R.id.videosno);
//            txtbullet = itemView.findViewById(R.id.txtbullet);
//            testno = itemView.findViewById(R.id.testno);
        }
    }
}
