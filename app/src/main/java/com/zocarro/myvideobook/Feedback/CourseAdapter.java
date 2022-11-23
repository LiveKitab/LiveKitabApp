package com.zocarro.myvideobook.Feedback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.zocarro.myvideobook.MyLog.TAG;

public class CourseAdapter extends RecyclerView.Adapter
{
    private Context ctx;
    private ArrayList<UserCourses> list;

    public CourseAdapter(Context ctx, ArrayList<UserCourses> list)
    {
        this.ctx = ctx;
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_user_courses_list,null);
        return new CourseAdapter.ViewHolder(myview);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final UserCourses userCourses =list.get(position);
        CourseAdapter.ViewHolder container = (CourseAdapter.ViewHolder) holder;
        container.courseName.setText(userCourses.getSub_name());
        container.authorname.setText(userCourses.getC_name());
        String image_url = Common.GetBaseImageURL() +"src/creator/"+userCourses.getC_img();


                container.ratingTextView.setText(userCourses.getRating());


        Log.d(TAG, "onBindViewHolder: " + image_url);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        container.courseImageView.setDefaultImageResId(R.drawable.videobook_logo);
        container.courseImageView.setErrorImageResId(R.drawable.videobook_logo);

        container.productRatingBar.setRating(Float.parseFloat(userCourses.getRating()));
        container.courseImageView.setImageUrl(image_url, imageLoader);

//        container.txtbullet.setText("\u2022");
//        container.videosno.setText(userCourses.getNo_of_video());
//        container.testno.setText(userCourses.getNo_of_test());

        container.layoutCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getIfFeedbackIsSubmitted(userCourses.getC_id(),userCourses.getSub_id());
            }
        });
    }
    private void getIfFeedbackIsSubmitted(final String c_id,final String sub_id)
    {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String uid = mPrefs.getString("u_id", "none");
        String wishlistUrl = Common.GetWebServiceURL() + "isfeedbackSubmitted.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, wishlistUrl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Common.progressDialogDismiss(ctx);
                    Log.d("response123", response);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    if(message.equals("Not Submitted"))
                    {
                        Intent i = new Intent(ctx, FeedbackActivity.class);
                        i.putExtra("sub_id", sub_id);
                        i.putExtra("c_id",c_id);
                        ctx.startActivity(i);
                    }
                    else {
                        Intent i = new Intent(ctx, ViewFeedbacksActivity.class);
                        i.putExtra("sub_id", sub_id);
                        i.putExtra("c_id",c_id);
                        ctx.startActivity(i);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(ctx);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Common.progressDialogDismiss(ctx);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> data = new HashMap<>();
                data.put("user_id",uid);
                data.put("sub_id",sub_id);
                data.put("mentor_id",c_id);
                Log.d("LLL",data.toString());
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(sr);
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView courseName,authorname,txtbullet,testno,videosno,ratingTextView;
        NetworkImageView courseImageView;
        CardView layoutCard;
        RatingBar productRatingBar;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseNameTextView);
            courseImageView = itemView.findViewById(R.id.mentorImageView);
            layoutCard = itemView.findViewById(R.id.layoutCard);
            authorname = itemView.findViewById(R.id.authorname);
//            txtbullet = itemView.findViewById(R.id.txtbullet);
//            testno = itemView.findViewById(R.id.testno);
//            videosno = itemView.findViewById(R.id.videosno);
            productRatingBar = itemView.findViewById(R.id.productRatingBar);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }
    }
}
