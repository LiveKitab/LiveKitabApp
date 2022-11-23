package com.zocarro.myvideobook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Model.Notifications;
import com.zocarro.myvideobook.R;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ArrayList<Notifications> list;
    Context context;

    public NotificationsAdapter(ArrayList<Notifications> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Notifications model = list.get(position);

        holder.headingTextView.setText(model.getTitle());
        holder.descriptionTextView.setText(model.getDes());
        holder.dateTextView.setText(model.getDate());

        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.expand.getTag().toString().equalsIgnoreCase("collaps")){
                    holder.notiImg.setVisibility(View.VISIBLE);
                    holder.expand.setTag("expand");
                    holder.expand.setRotation(180);

//                    Toast.makeText(context.getApplicationContext(), model.getLink().toString() , Toast.LENGTH_SHORT).show();

                 Glide.with(holder.dateTextView).load(model.getLink()).into(holder.notiImg);

                }

                else {
                    holder.notiImg.setVisibility(View.GONE);
                    holder.expand.setTag("collaps");
                    holder.expand.setRotation(0);
                }
//                Glide.with(holder.dateTextView).load(model.getLink()).into(holder.notiImg);
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = inflater.inflate(R.layout.layout_read_more, null);
                TextView txtsub, txtdesc;
                ImageView dilImg;
                txtsub = mView.findViewById(R.id.txtsub);
                txtdesc = mView.findViewById(R.id.txtdesc);
                dilImg = mView.findViewById(R.id.dilImg);

                txtsub.setText(model.getTitle());
                txtdesc.setText(model.getDes());
                Glide.with(holder.dateTextView).load(model.getLink()).into(dilImg);



                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                Button cancelview = mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView headingTextView, descriptionTextView, dateTextView;

        ImageView expand , notiImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headingTextView = itemView.findViewById(R.id.headingTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            expand = itemView.findViewById(R.id.expand);
            notiImg = itemView.findViewById(R.id.notificationImg);
        }
    }
}
