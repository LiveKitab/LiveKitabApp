package com.zocarro.myvideobook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.zocarro.myvideobook.Activity.ChapterVideoDetailsActivity;
import com.zocarro.myvideobook.Activity.MentorChapterActivity;
import com.zocarro.myvideobook.CircularImageView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Model.MentorModel;
import com.zocarro.myvideobook.Model.chaptermodel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;

import java.util.ArrayList;
import java.util.List;

public class MentorChapAdapter extends RecyclerView.Adapter<MentorChapAdapter.ViewHolder> implements Filterable
{
    private static final String TAG = "CategoriesAdapter";
    Context context;
    List<chaptermodel> subCatArrayList;
    private List<chaptermodel> exampleListFull;

    public MentorChapAdapter(Context context, List<chaptermodel> subCatArrayList)
    {
        this.context = context;
        this.subCatArrayList = subCatArrayList;
        exampleListFull = new ArrayList<>(subCatArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_mentorchapname, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        chaptermodel model = subCatArrayList.get(position);
        holder.chaptnumber.setText(model.getNumber());
        holder.chapter_name.setText(model.getChapter_name());
        holder.countvideo.setText(model.getCountvideo());
        holder.counttest.setText(model.getCounttest());
        holder.countmaterial.setText(model.getCountmaterial());
        holder.txtbullet.setText("\u2022");
        holder.txtbullet1.setText("\u2022");
//        holder.textViewMentorName.setText(model.getC_name());
//        holder.subjectName.setText(model.getSub_name());
////        holder.textViewAddress.setText(model.getC_email());
//        Log.d(TAG, "onBindViewHolder: " + image_url);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.chaptername.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Intent intent=new Intent(context,ViewCourseVideo.class);
//                intent.putExtra("c_id", model.getC_id());
//                intent.putExtra("sub_id", model.getSub_id());
//                intent.putExtra("subject_name",model.getSub_name());
//                intent.putExtra("subject_code",model.getSub_code());
//                Log.d("pos", String.valueOf(position));
//                context.startActivity(intent);

                Intent intent=new Intent(context, ChapterVideoDetailsActivity.class);
                intent.putExtra("c_id", model.getC_id());
                intent.putExtra("sub_id", model.getSub_id());
                intent.putExtra("enroll", model.getCheck());
                intent.putExtra("number", model.getNumber());
                intent.putExtra("chapter_id",model.getChapter_id());
                intent.putExtra("chapter_name",model.getChapter_name());
//                Log.d("pos", String.valueOf(position));
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return subCatArrayList.size();
    }
    @Override
    public Filter getFilter()
    {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<chaptermodel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(exampleListFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (chaptermodel item : exampleListFull)
                {
                    if (item.getChapter_name().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            subCatArrayList.clear();
            subCatArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView chaptnumber,chapter_name,countvideo,counttest,countmaterial,txtbullet,txtbullet1;
        RelativeLayout chaptername;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            chaptnumber = itemView.findViewById(R.id.chaptnumber);
            chapter_name = itemView.findViewById(R.id.chapter_name);
            chaptername = itemView.findViewById(R.id.chaptername);
            countvideo = itemView.findViewById(R.id.countvideo);
            counttest = itemView.findViewById(R.id.counttest);
            countmaterial = itemView.findViewById(R.id.countmaterial);
            txtbullet = itemView.findViewById(R.id.txtbullet);
            txtbullet1 = itemView.findViewById(R.id.txtbullet1);
            countmaterial = itemView.findViewById(R.id.countmaterial);
        }
    }
}
