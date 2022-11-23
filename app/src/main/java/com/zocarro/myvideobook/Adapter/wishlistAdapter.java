package com.zocarro.myvideobook.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zocarro.myvideobook.fragment.FavouriteMentorFragment;
import com.zocarro.myvideobook.fragment.FavouriteVideoFragment;


public class wishlistAdapter extends FragmentPagerAdapter
{
    int mNumOfTabs;
    public wishlistAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FavouriteMentorFragment tab1 = new FavouriteMentorFragment();
                return tab1;
            case 1:
                FavouriteVideoFragment tab2 = new FavouriteVideoFragment();
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
