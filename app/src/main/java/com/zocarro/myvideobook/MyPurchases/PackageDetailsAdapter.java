package com.zocarro.myvideobook.MyPurchases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zocarro.myvideobook.R;

import java.util.ArrayList;

public class PackageDetailsAdapter extends  RecyclerView.Adapter {

    Context ctx;
    ArrayList<PackageModel> list;

    public PackageDetailsAdapter(Context ctx, ArrayList<PackageModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_package_details, null);
        PackageDetailsAdapter.ViewHolder container = new PackageDetailsAdapter.ViewHolder(myview);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final PackageModel model = list.get(position);
        final PackageDetailsAdapter.ViewHolder container =(PackageDetailsAdapter.ViewHolder) holder;

        container.courseNameTextView.setText(model.getCourse_name());
        container.subjectCodeTextView.setText(model.getSub_code());
        container.universityNameTextView.setText(model.getUni());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseNameTextView, subjectCodeTextView, universityNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            subjectCodeTextView = itemView.findViewById(R.id.subjectCodeTextView);
            universityNameTextView = itemView.findViewById(R.id.universityNameTextView);
        }
    }
}
