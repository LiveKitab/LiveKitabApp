package com.zocarro.myvideobook.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.VideoViewModel;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import java.util.ArrayList;

public class    ChapterVideoAdapter extends RecyclerView.Adapter<ChapterVideoAdapter.VideoCourseHolder>
{
    Context ctx;
    ArrayList<VideoViewModel> videoViewModelArrayList;
    String video_id;
    boolean isPurchased = false;

    public ChapterVideoAdapter(Context ctx, ArrayList<VideoViewModel> videoViewModelArrayList, String video_id, boolean isPurchased)
    {
        this.ctx = ctx;
        this.videoViewModelArrayList = videoViewModelArrayList;
        this.video_id = video_id;
        this.isPurchased = isPurchased;
    }
    @NonNull
    @Override
    public VideoCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.chapter_video_rows,null);
        return new VideoCourseHolder(myview);
    }
    @Override
    public void onBindViewHolder(@NonNull VideoCourseHolder holder, int position)
    {
        final VideoViewModel videoViewModel=videoViewModelArrayList.get(position);
        VideoCourseHolder container=(VideoCourseHolder) holder;
        container.txtvideoTitle.setText(videoViewModel.getV_title());
//        container.txtChapName.setText(videoViewModel.getCh_name());
//        container.videodescription.setText(videoViewModel.getV_des());

        String img_url = "http://img.youtube.com/vi/" + videoViewModel.getLink() +"/0.jpg";
        Glide.with(ctx).load(img_url).into(container.imageView);
        container.cardvideoview.setOnClickListener(v ->
        {
            if (isPurchased)
            {
                Intent intent=new Intent(ctx, ViewCourseVideo.class);
                intent.putExtra("c_id", videoViewModel.getC_id());
                intent.putExtra("courseTitle",videoViewModel.getV_title());
                intent.putExtra("courseDesc", videoViewModel.getV_des());
                intent.putExtra("sub_id", videoViewModel.getSubject_id());
                intent.putExtra("v_id", videoViewModel.getV_id());
                intent.putExtra("v_link", videoViewModel.getLink());
                intent.putExtra("subject_name",videoViewModel.getSub_name());
                intent.putExtra("subject_code",videoViewModel.getSub_code());
                intent.putExtra("chap_id",videoViewModel.getChapter_id());
                intent.putExtra("chap_name",videoViewModel.getCh_name());
                intent.putExtra("file",videoViewModel.getFile());
                Log.d("filessss",videoViewModel.getFile());

                Log.d("v_link123",videoViewModel.getLink());
                Log.d("v_id123",videoViewModel.getV_id());
                intent.putExtra("pos",position);
                ctx.startActivity(intent);
                ((Activity)ctx).finish();
            }
            else {
                Toast.makeText(ctx, "Please enroll in this course to continue",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return videoViewModelArrayList.size();
    }
    class VideoCourseHolder extends RecyclerView.ViewHolder
    {
        TextView txtvideoTitle,txtChapName,videodescription;
        ImageView imageView;
        MaterialCardView cardvideoview;
        RelativeLayout relCard;
        public VideoCourseHolder(@NonNull View itemView)
        {
            super(itemView);
            txtvideoTitle=itemView.findViewById(R.id.videotitle);
//            txtvideoName=itemView.findViewById(R.id.txtvideoName);
//            txtChapName=itemView.findViewById(R.id.txtChapName);
            imageView=itemView.findViewById(R.id.imageView);
            cardvideoview=itemView.findViewById(R.id.cardvideoview);
            relCard=itemView.findViewById(R.id.relCard);
//            videodescription = itemView.findViewById(R.id.videodescription);

        }
    }
}
