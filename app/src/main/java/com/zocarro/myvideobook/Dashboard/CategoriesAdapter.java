package com.zocarro.myvideobook.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.R;


import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>
{
    private static final String TAG = "CategoriesAdapter";
    Context context;
    List<Categories> subCatArrayList;

    public CategoriesAdapter(Context context, List<Categories> subCatArrayList)
    {
        this.context = context;
        this.subCatArrayList = subCatArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_subjects, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Categories model = subCatArrayList.get(position);
        holder.sub_cat.setText(model.getCatName());
        holder.sub_code.setText(model.getCatcode());
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
                Intent intent = new Intent(context, MentorActivity.class);
                intent.putExtra("sub_id", model.getSub_id());
                intent.putExtra("sub_name", model.getCatName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
        TextView sub_cat,sub_code;
        NetworkImageView catImage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catImage = itemView.findViewById(R.id.catImage);
            sub_cat = itemView.findViewById(R.id.sub_cat);
            sub_code = itemView.findViewById(R.id.sub_code);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

}
