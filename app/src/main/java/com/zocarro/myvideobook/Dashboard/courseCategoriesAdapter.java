package com.zocarro.myvideobook.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.zocarro.myvideobook.Category.CategoryActivity;
import com.zocarro.myvideobook.R;

import java.util.List;

public class courseCategoriesAdapter extends RecyclerView.Adapter
{
    Context ctx;
    List<courseCategories> courseCategoriesList;

    public courseCategoriesAdapter(Context ctx, List<courseCategories> courseCategoriesList)
    {
        this.ctx = ctx;
        this.courseCategoriesList = courseCategoriesList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_categories,null);
        CategoriesHolder container = new CategoriesHolder(myview);
        return container;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final courseCategories coursecategories = courseCategoriesList.get(position);
        final CategoriesHolder container =(CategoriesHolder) holder;

        container.categories.setText(coursecategories.getCategories());
        container.categories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i  = new Intent(ctx, CategoryActivity.class);
                i.putExtra("b_id",coursecategories.getB_id());
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return courseCategoriesList.size();
    }
    class CategoriesHolder extends RecyclerView.ViewHolder
    {
        TextView categories;
        public CategoriesHolder(@NonNull View itemView)
        {
            super(itemView);
            categories = itemView.findViewById(R.id.categories);
        }
    }

}
