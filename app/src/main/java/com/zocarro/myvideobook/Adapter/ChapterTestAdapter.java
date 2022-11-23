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
import com.zocarro.myvideobook.Model.chaptertestmodel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.Test.TestActivity;
import com.zocarro.myvideobook.VideoCourse.VideoViewModel;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import java.util.ArrayList;

public class ChapterTestAdapter extends RecyclerView.Adapter<ChapterTestAdapter.VideoCourseHolder>
{
    Context ctx;
    ArrayList<chaptertestmodel> videoViewModelArrayList;
    String video_id;
    boolean isPurchased = false;

    public ChapterTestAdapter(Context ctx, ArrayList<chaptertestmodel> videoViewModelArrayList)
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
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_test_video_rows,null);
        return new VideoCourseHolder(myview);
    }
    @Override
    public void onBindViewHolder(@NonNull VideoCourseHolder holder, int position)
    {
        final chaptertestmodel videoViewModel=videoViewModelArrayList.get(position);
        VideoCourseHolder container=(VideoCourseHolder) holder;
        container.txtvideoTitle.setText(videoViewModel.getV_title());
//        container.txtChapName.setText(videoViewModel.getCh_name());
        container.videodescription.setText(videoViewModel.getV_des());
        container.sequence.setText(videoViewModel.getSequence());
//        img_url="http://img.youtube.com/vi/" + videolink + "/0.jpg";
        String img_url = "http://img.youtube.com/vi/" + videoViewModel.getV_link() +"/0.jpg";
        Log.d("image_url1",img_url);
        Glide.with(ctx).load(img_url).into(container.imageView);
        container.cardvideoview.setOnClickListener(v ->
        {
                Intent intent=new Intent(ctx, TestActivity.class);
//                intent.putExtra("c_id", videoViewModel.getC_id());
                intent.putExtra("courseTitle",videoViewModel.getV_title());
                intent.putExtra("courseDesc", videoViewModel.getV_des());
//                intent.putExtra("sub_id", videoViewModel.getSubject_id());
                intent.putExtra("video_id", videoViewModel.getV_id());
                intent.putExtra("c_id", videoViewModel.getC_id());
                intent.putExtra("sub_id", videoViewModel.getSub_id());
                intent.putExtra("chap_id", videoViewModel.getChap_id());
                intent.putExtra("chap_name", videoViewModel.getChap_name());
                intent.putExtra("number", videoViewModel.getNumber());
                intent.putExtra("chaptertesr","chaptertest");
//                intent.putExtra("v_link", videoViewModel.getLink());
//                intent.putExtra("subject_name",videoViewModel.getSub_name());
//                intent.putExtra("subject_code",videoViewModel.getSub_code());
//                intent.putExtra("chap_id",videoViewModel.getChapter_id());
//                Log.d("v_link123",videoViewModel.getV_link());
//                Log.d("v_id123",videoViewModel.getV_id());
                intent.putExtra("pos",position);
                ctx.startActivity(intent);
                ((Activity)ctx).finish();
        });
    }
    @Override
    public int getItemCount()
    {
        return videoViewModelArrayList.size();
    }
    class VideoCourseHolder extends RecyclerView.ViewHolder
    {
        TextView txtvideoTitle,txtChapName,videodescription,sequence;
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
            videodescription = itemView.findViewById(R.id.videodescription);
            sequence = itemView.findViewById(R.id.sequence);

        }
    }
}
