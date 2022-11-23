package com.zocarro.myvideobook.VideoCourse;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zocarro.myvideobook.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<CommentModel> list;
    private Context mCtx;

    public CommentAdapter(final Context mCtx, ArrayList<CommentModel> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        mCtx = view.getContext();
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        final CommentModel model = list.get(position);
        final CommentViewHolder container = (CommentViewHolder) holder;

        container.commentTextView.setText(Html.fromHtml(model.getComment()));
        container.commentUserName.setText(model.getUsername());
        container.commentTimePosted.setText(model.getDate());
        if(model.getComment_mentor().equalsIgnoreCase("not set"))
        {
            container.relmentor.setVisibility(View.GONE);
        }
        else
        {
            container.relmentor.setVisibility(View.VISIBLE);
            container.mentorComment.setText(model.getComment_mentor());
            container.mentorName.setText(model.getC_name()+" "+model.getC_lname());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView commentTextView, commentUserName, commentTimePosted,mentorName,mentorComment,commentTime;
        RelativeLayout user_comment_layout,relmentor;

        CommentViewHolder(View itemView) {
            super(itemView);
            commentTextView = itemView.findViewById(R.id.comment);
            commentUserName = itemView.findViewById(R.id.comment_username);
            commentTimePosted = itemView.findViewById(R.id.comment_time_posted);
            commentTimePosted = itemView.findViewById(R.id.comment_time_posted);
            user_comment_layout = itemView.findViewById(R.id.user_comment_layout);
            relmentor = itemView.findViewById(R.id.relmentor);
            mentorName = itemView.findViewById(R.id.mentorName);
            mentorComment = itemView.findViewById(R.id.mentorComment);
            //commentTime = itemView.findViewById(R.id.commentTime);
        }
    }
}
