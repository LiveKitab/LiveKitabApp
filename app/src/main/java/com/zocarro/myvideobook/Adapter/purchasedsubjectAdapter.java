package com.zocarro.myvideobook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zocarro.myvideobook.Activity.MentorChapterActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Model.purchasedsubject;
import com.zocarro.myvideobook.R;

import java.util.List;

public class purchasedsubjectAdapter extends RecyclerView.Adapter<purchasedsubjectAdapter.ViewHolder>
{
    private static final String TAG = "CategoriesAdapter";
    Context context;
    List<purchasedsubject> subCatArrayList;

    public purchasedsubjectAdapter(Context context, List<purchasedsubject> subCatArrayList)
    {
        this.context = context;
        this.subCatArrayList = subCatArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_purchasedsubjects, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        purchasedsubject model = subCatArrayList.get(position);
        holder.sub_cat.setText(model.getCatName());
        holder.authorname.setText("By:" +model.getCname());
        String image_url = Common.GetBaseImageURL() +"src/subject/"+model.getCatImage();
        Log.d(TAG, "onBindViewHolder: " + image_url);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.catImage.setDefaultImageResId(R.drawable.logo);
        holder.catImage.setErrorImageResId(R.drawable.logo);
        holder.catImage.setImageUrl(image_url, imageLoader);
            holder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(model.getDurability().equalsIgnoreCase("expired purchased course"))
                    {
                        Toast.makeText(context, "Your course is expired", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(context, MentorChapterActivity.class);
                        intent.putExtra("sub_id",model.getSub_id());
                        intent.putExtra("cid",model.getCid());
                        intent.putExtra("c_name",model.getCname());
                        intent.putExtra("subject_name",model.getCatName());
                        Log.d("ddsd",model.getSub_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
    }
    @Override
    public int getItemCount()
    {
        return subCatArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView sub_cat,authorname;
        NetworkImageView catImage;
        CardView cardView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catImage = itemView.findViewById(R.id.catImage);
            sub_cat = itemView.findViewById(R.id.sub_cat);
            authorname = itemView.findViewById(R.id.authorname);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}