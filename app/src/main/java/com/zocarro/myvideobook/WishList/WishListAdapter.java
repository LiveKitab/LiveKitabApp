package com.zocarro.myvideobook.WishList;

import static com.zocarro.myvideobook.MyLog.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.zocarro.myvideobook.Activity.MentorChapterActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.listener.OnDeleteAddressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListHolder> {

    Context ctx;
    List<WishListModel> wishListModelList;

    OnDeleteAddressListener onDeleteCartListener;


    public WishListAdapter(Context ctx, List<WishListModel> wishListModelList,OnDeleteAddressListener onDeleteCartListener) {
        this.ctx = ctx;
        this.wishListModelList = wishListModelList;
        this.onDeleteCartListener = onDeleteCartListener;

    }

    @NonNull
    @Override
    public WishListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_wishlist,null);
        WishListHolder container=new WishListHolder(myview);
        return  container;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListHolder holder, int position) {
        final WishListModel model=wishListModelList.get(position);
        holder.textViewMentorName.setText(model.getSub_name());
        holder.authorname.setText(model.getC_name());
        holder.textViewAddress.setText(model.getC_email());
        holder.productRatingBar.setRating(Float.parseFloat(model.getRating()));
        holder.ratingTextView.setText(model.getRating());
        String image_url = Common.GetBaseImageURL() +"src/creator/"+model.getC_img();
        Log.d("TAG", "onBindViewHolder: " + image_url);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        holder.mentorImageView.setDefaultImageResId(R.drawable.videobook_logo);
        holder.mentorImageView.setErrorImageResId(R.drawable.videobook_logo);
        holder.mentorImageView.setImageUrl(image_url, imageLoader);

        holder.shopCardview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Intent intent = new Intent(ctx, CourseActivity.class);
//                intent.putExtra("sub_id",model.getSub_id());
//                intent.putExtra("cid",model.getC_id());
//                Log.d("subject_id",model.getSub_id());

                Intent intent = new Intent(ctx, MentorChapterActivity.class);
                intent.putExtra("sub_id",model.getSub_id());
                intent.putExtra("cid",model.getC_id());
                intent.putExtra("c_name",model.getC_name());
                intent.putExtra("subject_name",model.getSub_name());
                Log.d("subject_id",model.getSub_id());

//                intent.putExtra("subject",)
                ctx.startActivity(intent);
                ((Activity)ctx).finish();

            }
        });
        holder.deletelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Are you sure you want to remove?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                removeFromFavourites(model.getId());
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
            }
        });


    }
    private void removeFromFavourites(String id)
    {
        Log.d(TAG, "removeFromFavourites:called");
        String url =Common.GetWebServiceURL() +"removewishlist.php";
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
                        Toast.makeText(ctx, "Remove From Favorite Mentor", Toast.LENGTH_SHORT).show();
                        //    onUpdateAddressListner.updateAddress();
                    }
                    else {
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
    public int getItemCount() {
        return wishListModelList.size();
    }

    class WishListHolder extends RecyclerView.ViewHolder{

        TextView textViewMentorName,textViewAddress,ratingTextView,authorname;
        NetworkImageView mentorImageView;
        RatingBar productRatingBar;
        CardView shopCardview;
        ImageButton deletelist;
        public WishListHolder(@NonNull View itemView)
        {
            super(itemView);

            mentorImageView = itemView.findViewById(R.id.mentorImageView);
            textViewMentorName = itemView.findViewById(R.id.textViewMentorName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            shopCardview = itemView.findViewById(R.id.shopCardview);
            productRatingBar = itemView.findViewById(R.id.productRatingBar);
            authorname = itemView.findViewById(R.id.authorname);
            deletelist = itemView.findViewById(R.id.deletelist);
        }
    }
}
