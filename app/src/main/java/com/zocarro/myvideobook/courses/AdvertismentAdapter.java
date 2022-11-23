package com.zocarro.myvideobook.courses;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.zocarro.myvideobook.Common;

public class AdvertismentAdapter extends PagerAdapter {

    private Context mContext;
    private String[] sliderImageId = new String[]{};
    String ImageUrl ;


    public AdvertismentAdapter(Context mContext, String[] sliderImageId)
    {
        this.mContext = mContext;
        this.sliderImageId = sliderImageId;
    }

    @Override
    public int getCount()
    {
        return sliderImageId.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == ((ImageView) object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            ImageUrl = Common.GetBaseImageURL() + "src/banner/" + sliderImageId[position];
            Log.d("img",ImageUrl);

        Glide.with(mContext).load(ImageUrl).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
