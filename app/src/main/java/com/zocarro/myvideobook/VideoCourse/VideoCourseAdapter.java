package com.zocarro.myvideobook.VideoCourse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.listener.OnDeleteCartListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VideoCourseAdapter extends RecyclerView.Adapter<VideoCourseAdapter.VideoCourseHolder>
{

    Context ctx;
    ArrayList<VideoViewModel> videoViewModelArrayList;
    String video_id;
    boolean isPurchased = false;
    OnDeleteCartListener onDeleteCartListener;
    public VideoCourseAdapter(Context ctx, ArrayList<VideoViewModel> videoViewModelArrayList, String video_id, boolean isPurchased,OnDeleteCartListener onDeleteCartListener)
    {
        this.ctx = ctx;
        this.videoViewModelArrayList = videoViewModelArrayList;
        this.video_id = video_id;
        this.isPurchased = isPurchased;
        this.onDeleteCartListener = onDeleteCartListener;
    }
    @NonNull
    @Override
    public VideoCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_video_rows,parent, false );
        return new VideoCourseHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCourseHolder holder, int position)
    {
        final VideoViewModel videoViewModel=videoViewModelArrayList.get(position);
        VideoCourseHolder container=(VideoCourseHolder) holder;
        container.txtvideoTitle.setText(videoViewModel.getV_title());
//        container.txtChapName.setText(videoViewModel.getCh_name());
        container.videodescription.setText(videoViewModel.getV_des());
        container.sequence.setText(videoViewModel.getSequence());

        if(videoViewModel.getIsInFav().equals("true"))
        {
            container.removetofav.setVisibility(View.VISIBLE);
            container.addtofav.setVisibility(View.GONE);
        }
        else
        {
            container.addtofav.setVisibility(View.VISIBLE);
            container.removetofav.setVisibility(View.GONE);
        }

        if (video_id.equals(videoViewModel.getV_id()))
        {
            container.relCard.setSelected(true);
            container.relCard.setBackgroundColor(Color.GRAY);
        } else {
            container.relCard.setSelected(false);
        }
        String img_url = "http://img.youtube.com/vi/" + videoViewModel.getLink() +"/0.jpg";
        Log.d("image_url",img_url);
        Glide.with(ctx).load(img_url).into(container.imageView);
        container.cardvideoview.setOnClickListener(v ->
        {
            if (isPurchased)
            {
                Intent intent=new Intent(ctx,ViewCourseVideo.class);
                intent.putExtra("c_id", videoViewModel.getC_id());
                intent.putExtra("courseTitle",videoViewModel.getV_title());
                intent.putExtra("courseDesc", videoViewModel.getV_des());
                intent.putExtra("sub_id", videoViewModel.getSubject_id());
                intent.putExtra("v_id", videoViewModel.getV_id());
                intent.putExtra("v_link", videoViewModel.getLink());
                intent.putExtra("subject_name",videoViewModel.getSub_name());
                intent.putExtra("subject_code",videoViewModel.getSub_code());
                intent.putExtra("chap_id",videoViewModel.getChapter_id());
                intent.putExtra("file",videoViewModel.getFile());
                Log.d("v_link123",videoViewModel.getLink());
                Log.d("file123",videoViewModel.getFile());
                Log.d("v_id123",videoViewModel.getV_id());
                intent.putExtra("pos",position);
                ctx.startActivity(intent);
                ((Activity)ctx).finish();
            }
            else {
                Toast.makeText(ctx, "Please enroll in this course to continue",Toast.LENGTH_SHORT).show();
            }
        });
        container.addtofav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addfavourite(videoViewModel.getV_id(),videoViewModel.getLink(),container.removetofav,container.addtofav);

            }
        });
        container.removetofav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                        removeFavourite(videoViewModel.getV_id(),container.removetofav,container.addtofav);
            }
        });
    }
    private void addfavourite(String v_id,String link, ImageView removetofav, ImageView addtofav)
    {
        final ProgressDialog pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String uid = preferences.getString("u_id", "none");
        String ADD_CART_URL = Common.GetBaseURL() + "add_fav_video.php";
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
                        Toast.makeText(ctx, "Added in your Wishlist", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e)
                {
                    pDialog.dismiss();
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener()
        {
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
                params.put("v_id", v_id);
                params.put("link", link);
                Log.d("params wishlist", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    private void removeFavourite(String v_id, ImageView removetofav, ImageView addfav)
    {
        final ProgressDialog pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String uid = preferences.getString("u_id", "none");
        String ADD_CART_URL = Common.GetBaseURL() + "remove_fav_video.php";
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
                        addfav.setVisibility(View.VISIBLE);
                        removetofav.setVisibility(View.GONE);
                        onDeleteCartListener.deletecart();
                        notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show();
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
                params.put("v_id", v_id);
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
        return videoViewModelArrayList.size();
    }

    static class VideoCourseHolder extends RecyclerView.ViewHolder
    {
        TextView txtvideoTitle,txtChapName,videodescription,sequence;
        ImageView imageView;
        MaterialCardView cardvideoview;
        RelativeLayout relCard;
        ImageView addtofav,removetofav;
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
            removetofav = itemView.findViewById(R.id.fav_workshop);
            addtofav = itemView.findViewById(R.id.favouriteImageview);
        }
    }
}
