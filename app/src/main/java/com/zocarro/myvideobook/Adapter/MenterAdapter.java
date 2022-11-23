package com.zocarro.myvideobook.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.zocarro.myvideobook.Activity.MentorChapterActivity;
import com.zocarro.myvideobook.CircularImageView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.Model.MentorModel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.VideoCourse.ViewCourseVideo;
import com.zocarro.myvideobook.courses.CourseActivity;
import com.zocarro.myvideobook.listener.OnDeleteCartListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenterAdapter extends RecyclerView.Adapter<MenterAdapter.ViewHolder> implements Filterable
{
    private static final String TAG = "CategoriesAdapter";
    Context context;
    List<MentorModel> subCatArrayList;
    private List<MentorModel> exampleListFull;
    OnDeleteCartListener onDeleteCartListener;

    public MenterAdapter(Context context, List<MentorModel> subCatArrayList,OnDeleteCartListener onDeleteCartListener)
    {
        this.context = context;
        this.subCatArrayList = subCatArrayList;
        this.onDeleteCartListener = onDeleteCartListener;
        exampleListFull = new ArrayList<>(subCatArrayList);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_mentor, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MentorModel model = subCatArrayList.get(position);
        holder.textViewMentorName.setText(model.getC_name());
        holder.subjectName.setText(model.getSub_name());
//        holder.textViewAddress.setText(model.getC_email());
        holder.productRatingBar.setRating(Float.parseFloat(model.getRating()));
        holder.ratingTextView.setText(model.getRating());
        String image_url = Common.GetBaseImageURL() +"src/creator/"+model.getC_img();
        Log.d(TAG, "onBindViewHolder: " + image_url);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.mentorImageView.setDefaultImageResId(R.drawable.logo);
        holder.mentorImageView.setErrorImageResId(R.drawable.logo);
        holder.mentorImageView.setImageUrl(image_url, imageLoader);
//        holder.videosno.setText(model.getVno());
//        holder.testno.setText(model.getTno());
//        holder.txtbullet.setText("\u2022");
        if(model.getIsInFav().equals("true"))
        {
            holder.removetofav.setVisibility(View.VISIBLE);
            holder.addtofav.setVisibility(View.GONE);
        }
        else
        {
            holder.addtofav.setVisibility(View.VISIBLE);
            holder.removetofav.setVisibility(View.GONE);
        }

        holder.addtofav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addfavourite(model.getSub_id(),model.getC_id(),holder.removetofav,holder.addtofav);
            }
        });
        holder.removetofav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                removeFavourite(model.getSub_id(),model.getC_id(),holder.removetofav,holder.addtofav);
            }
        });

        holder.shopCardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, MentorChapterActivity.class);
                intent.putExtra("sub_id",model.getSub_id());
                intent.putExtra("cid",model.getC_id());
                intent.putExtra("c_name",model.getC_name());
                intent.putExtra("subject_name",model.getSub_name());
                Log.d("subject_id",model.getSub_id());
//                Intent intent=new Intent(context,ViewCourseVideo.class);
//                intent.putExtra("c_id", model.getC_id());
////                intent.putExtra("courseTitle",model.getC_name());
////                intent.putExtra("courseDesc", model.getC_name());
//                intent.putExtra("sub_id", model.getSub_id());
//                intent.putExtra("subject_name",model.getSub_name());
//                intent.putExtra("subject_code",model.getSub_code());
////                intent.putExtra("v_id", model.getVno());
////                intent.putExtra("v_link", model.get());
////                intent.putExtra("pos",position);
//                Log.d("pos", String.valueOf(position));
                context.startActivity(intent);
            }
        });
    }
    private void removeFavourite(String sub_id,String mentor_id, ImageView removetofav, ImageView addtofav) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String uid = preferences.getString("u_id", "none");
        String ADD_CART_URL = Common.GetBaseURL() + "removefavmentor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_CART_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    pDialog.dismiss();
                    Log.d("aaa", response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equalsIgnoreCase("success"))
                    {
                        addtofav.setVisibility(View.VISIBLE);
                        removetofav.setVisibility(View.GONE);
                        onDeleteCartListener.deletecart();
                        notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e)
                {
                    pDialog.dismiss();
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", uid);
                params.put("sub_id", sub_id);
                params.put("c_id", mentor_id);
                Log.d("params wishlist", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void addfavourite(String sub_id,String mentor_id, ImageView removetofav, ImageView addtofav)
    {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String uid = preferences.getString("u_id", "none");
        String ADD_CART_URL = Common.GetBaseURL() + "add_fav_mentor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_CART_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    pDialog.dismiss();
                    Log.d("aaa", response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equals("success"))
                    {
                        removetofav.setVisibility(View.VISIBLE);
                        addtofav.setVisibility(View.GONE);
                        onDeleteCartListener.deletecart();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Added in your favourite Mentor", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e)
                {
                    pDialog.dismiss();
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", uid);
                params.put("sub_id", sub_id);
                params.put("c_id", mentor_id);
                Log.d("params wishlist", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(stringRequest);

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
            List<MentorModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(exampleListFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MentorModel item : exampleListFull)
                {
                    if (item.getC_name().toLowerCase().contains(filterPattern))
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
        TextView textViewMentorName,textViewAddress,ratingTextView,subjectName,videosno,testno,txtbullet;
        CircularImageView mentorImageView;
        RatingBar productRatingBar;
        CardView shopCardview;
        ImageView addtofav,removetofav;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mentorImageView = itemView.findViewById(R.id.mentorImageView);
            textViewMentorName = itemView.findViewById(R.id.textViewMentorName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            shopCardview = itemView.findViewById(R.id.shopCardview);
            productRatingBar = itemView.findViewById(R.id.productRatingBar);
            subjectName = itemView.findViewById(R.id.subjectName);
//            videosno = itemView.findViewById(R.id.videosno);
//            testno = itemView.findViewById(R.id.testno);
//            txtbullet = itemView.findViewById(R.id.txtbullet);
            removetofav = itemView.findViewById(R.id.fav_workshop);
            addtofav = itemView.findViewById(R.id.favouriteImageview);
        }
    }
}
