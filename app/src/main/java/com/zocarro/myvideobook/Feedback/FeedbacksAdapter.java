package com.zocarro.myvideobook.Feedback;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbacksAdapter extends RecyclerView.Adapter {
    private ArrayList<Feedbacks> list;
    private Context ctx;

    public FeedbacksAdapter(Context ctx, ArrayList<Feedbacks> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_view_feedbacks,null);
        return new FeedbacksAdapter.ViewHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Feedbacks feedbacks =list.get(position);
        FeedbacksAdapter.ViewHolder container = (FeedbacksAdapter.ViewHolder) holder;

        container.productRatingBar.setRating(Float.parseFloat(feedbacks.getRating()));
//        container.userRating.setChipText("" + feedbacks.getRating());

        container.userName.setText(feedbacks.getUsername());
        container.userReview.setText(feedbacks.getReview());
        if(feedbacks.getU_img().equalsIgnoreCase("not set"))
        {
            container.userImg.setImageResource(R.drawable.v_logo);
        }
        else {
            String img_url= Common.GetBaseImageURL()+"user/"+feedbacks.getU_img();
            String URL_IMAGE = Common.GetBaseImageURL() + "src/user/" + feedbacks.getU_img();
            Log.d("course", URL_IMAGE);
            Glide.with(ctx).load(URL_IMAGE).into(container.userImg);
        }
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView userImg;
        TextView userName, userReview;
    RatingBar productRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.userImg);
            userName = itemView.findViewById(R.id.userName);
            userReview = itemView.findViewById(R.id.userReview);
            productRatingBar = itemView.findViewById(R.id.productRatingBar);
        }
    }
}
