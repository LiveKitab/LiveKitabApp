package com.zocarro.myvideobook.MyPurchases;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zocarro.myvideobook.Activity.InvoiceWebActivity;
import com.zocarro.myvideobook.Activity.MentorChapterActivity;
import com.zocarro.myvideobook.Common;
import com.zocarro.myvideobook.Controller.AppController;
import com.zocarro.myvideobook.R;
import com.zocarro.myvideobook.courses.CourseActivity;

import java.util.ArrayList;

public class PurchasesAdapter extends RecyclerView.Adapter
{
        private Context ctx;
        private ArrayList<Purchases> list;
        public PurchasesAdapter(Context ctx, ArrayList<Purchases> list)
        {
            this.ctx = ctx;
            this.list = list;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View myview= LayoutInflater.from(ctx).inflate(R.layout.layout_my_purchases,null);
            return new ViewHolder(myview);
        }
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
        {
            final Purchases purchases=list.get(position);
            PurchasesAdapter.ViewHolder container=(PurchasesAdapter.ViewHolder) holder;

//            container.oldPriceTextView.setText(purchases.getPrice() + " ₹");
            container.productRatingBar.setRating(Float.parseFloat(purchases.getRating()));
            container.ratingTextView.setText(purchases.getRating());
//            container.oldPriceTextView.setPaintFlags(container.oldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            container.courseDiscount.setText(purchases.getDiscount() +"%");
            int np,op, dis;

            op = Integer.parseInt(purchases.getPrice());
            dis = Integer.parseInt(purchases.getDiscount());
            np = op - (op * dis)/100;
//            container.currentPriceTextView.setText(np+" ₹");
            container.textViewMentorcode.setText("(" +purchases.getSub_code() + ")");

            String image_url = Common.GetBaseImageURL() +"src/subject/"+purchases.getSub_bg();
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            container.mentorImageView.setImageUrl(image_url, imageLoader);
//            container.txtbullet.setText("\u2022");
            container.authorname.setText(purchases.getC_name());
            container.textViewMentorName.setText(purchases.getSubject_name());

            container.cardtestview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                        {
                    if(purchases.getDurability().equalsIgnoreCase("purchased course")) {
                        Intent intent = new Intent(ctx, MentorChapterActivity.class);
                        intent.putExtra("sub_id", purchases.getSub_id());
                        intent.putExtra("cid", purchases.getC_id());
                        intent.putExtra("c_name", purchases.getC_name());
                        intent.putExtra("subject_name", purchases.getSubject_name());
                        Log.d("subject_id", purchases.getSub_id());


//                intent.putExtra("subject",)
                        ctx.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ctx, "Your course is expired please purchase again", Toast.LENGTH_SHORT).show();
                        container.renew.setVisibility(View.VISIBLE);
                    }

//                    Intent intent = new Intent(ctx, CourseActivity.class);
//                    intent.putExtra("sub_id",purchases.getSub_id());
//                    intent.putExtra("cid",purchases.getC_id());
//                    Log.d("subject_id",purchases.getSub_id());

                }

            });
            container.renew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(ctx,CourseActivity.class);
                    intent.putExtra("sub_id",purchases.getSub_id());
                    intent.putExtra("cid",purchases.getC_id());
                    intent.putExtra("c_name",purchases.getC_name());
                    intent.putExtra("novideo",purchases.getNo_of_video());
                    intent.putExtra("nomaterial",purchases.getNo_of_video());
                    intent.putExtra("notest",purchases.getNo_of_test());
                    ctx.startActivity(intent);

                }
            });
//            Glide.with(ctx).load(Common.GetBaseImageURL() + "package/"+purchases.getC_img()).into(container.mentorImageView);

//            container.cardtestview.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    String course_id = purchases.getSub_id();
//                    course_id = course_id.substring(0,3);
//
//                    int  price;
//                    int discount;
//                    int discountedPrice;
//
//                    price = parseInt(purchases.getPrice());
//                    discount = parseInt(purchases.getDiscount());
//                    discountedPrice = price-((price*discount)/100);
//
//
            container.invoice.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(ctx, InvoiceWebActivity.class);
                        intent.putExtra("bill_id", purchases.getBill_id());
                        ctx.startActivity(intent);

                }
            });
//
//                        Intent intent=new Intent(ctx, BillActivity.class);
//                        intent.putExtra("course_id", purchases.getSub_id());
//                        ctx.startActivity(intent);
//
//
//                }
//            });

        }

        @Override
        public int getItemCount()
        {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewMentorName,ratingTextView,videosno,testno,authorname,coursePrice,courseDiscountPrice,courseDiscount,currentPriceTextView,oldPriceTextView,txtbullet,renew;
            NetworkImageView mentorImageView;
            RatingBar productRatingBar;
            TextView textViewMentorcode;
            CardView cardtestview;
            ImageButton invoice;

            ViewHolder(@NonNull View itemView)
            {
                super(itemView);
                textViewMentorName = itemView.findViewById(R.id.textViewMentorName);
                txtbullet = itemView.findViewById(R.id.txtbullet);
//                currentPriceTextView = itemView.findViewById(R.id.currentPriceTextView);
//                oldPriceTextView = itemView.findViewById(R.id.oldPriceTextView);
//                courseDiscount = itemView.findViewById(R.id.discountTextView);
                mentorImageView = itemView.findViewById(R.id.mentorImageView);
                textViewMentorcode = itemView.findViewById(R.id.textViewMentorcode);
                authorname = itemView.findViewById(R.id.authorname);
                productRatingBar = itemView.findViewById(R.id.productRatingBar);
                cardtestview = itemView.findViewById(R.id.shopCardview);
//                testno = itemView.findViewById(R.id.testno);
                invoice = itemView.findViewById(R.id.invoice);
//                videosno = itemView.findViewById(R.id.videosno);
                renew = itemView.findViewById(R.id.renew);
                ratingTextView = itemView.findViewById(R.id.ratingTextView);
            }
        }
    }
