package com.zocarro.myvideobook.Adapter;

import static com.zocarro.myvideobook.MyLog.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.FavouriteVideo;
import com.zocarro.myvideobook.Model.FavVideoModel;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.listener.OnDeleteAddressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteVideoAdapter extends RecyclerView.Adapter<FavouriteVideoAdapter.VideoCourseHolder>
{
    Context ctx;
    List<FavVideoModel> videoViewModelArrayList;
    String video_id;
    boolean isPurchased = false;
    OnDeleteAddressListener onDeleteCartListener;


    public FavouriteVideoAdapter(Context ctx, List<FavVideoModel> videoViewModelArrayList,OnDeleteAddressListener onDeleteCartListener)
    {
        this.ctx = ctx;
        this.videoViewModelArrayList = videoViewModelArrayList;
        this.onDeleteCartListener = onDeleteCartListener;
    }

    @NonNull
    @Override
    public VideoCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview = LayoutInflater.from(ctx).inflate(R.layout.layout_fav_video_rows,null);
        return new VideoCourseHolder(myview);
    }
    @Override
    public void onBindViewHolder(@NonNull VideoCourseHolder holder, int position)
    {
        final FavVideoModel videoViewModel=videoViewModelArrayList.get(position);
        VideoCourseHolder container=(VideoCourseHolder) holder;
        container.txtvideoTitle.setText(videoViewModel.getV_title());
//        container.txtChapName.setText(videoViewModel.getCh_name());
        container.videodescription.setText(videoViewModel.getV_des());
        container.sequence.setText(videoViewModel.getI());

        String img_url = "http://img.youtube.com/vi/" + videoViewModel.getLink() +"/0.jpg";
        Glide.with(ctx).load(img_url).into(container.imageView);
        container.fav_workshop.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setMessage("Are you sure you want to remove?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            removeFromFavourites(videoViewModel.getId());
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        });
        container.cardvideoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, FavouriteVideo.class);
                intent.putExtra("v_id",videoViewModel.getV_id());
                intent.putExtra("v_link", videoViewModel.getLink());
                Log.d("fav_v_link", videoViewModel.getLink());
                Log.d("fav_v_id", videoViewModel.getV_id());
                ctx.startActivity(intent);
            }
        });
    }

    private void removeFromFavourites(String id)
    {
        Log.d(TAG, "removeFromFavourites:called");
        String url = Common.GetWebServiceURL() +"remove_fav_video_wishlist.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String uid = preferences.getString("u_id", "none");
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Log.d(TAG, "onResponse:fav" + response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if (message.equalsIgnoreCase("Deleted"))
                    {
                        onDeleteCartListener.deleteAddress();
                        Toast.makeText(ctx, "Remove from wishlist", Toast.LENGTH_SHORT).show();
                        //    onUpdateAddressListner.updateAddress();
                    }
                    else{
                        Log.d("TAG", "onResponseRemoveFromFav: ");
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("TAG", "onErrorResponse: "+error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
//                data.put("u_id", uid);
                data.put("id", id);
                Log.d("TAG", "getParams: "+data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        AppController.getInstance().addToRequestQueue(sr);
    }

    @Override
    public int getItemCount()
    {
        return videoViewModelArrayList.size();
    }
    class VideoCourseHolder extends RecyclerView.ViewHolder
    {
        TextView txtvideoTitle,txtChapName,videodescription,sequence;
        ImageView imageView,fav_workshop;
        MaterialCardView cardvideoview;
        RelativeLayout relCard;
        public VideoCourseHolder(@NonNull View itemView)
        {
            super(itemView);
            txtvideoTitle=itemView.findViewById(R.id.videotitle);
//            txtvideoName=itemView.findViewById(R.id.txtvideoName);
//            txtChapName=itemView.findViewById(R.id.txtChapName);
            imageView=itemView.findViewById(R.id.imageView);
            fav_workshop=itemView.findViewById(R.id.fav_workshop);
            cardvideoview=itemView.findViewById(R.id.cardvideoview);
            relCard=itemView.findViewById(R.id.relCard);
            videodescription = itemView.findViewById(R.id.videodescription);
            sequence = itemView.findViewById(R.id.sequence);

        }
    }
}
