package com.path_studio.submission_05.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.path_studio.submission_05.Fragments.FavouriteFragment;
import com.path_studio.submission_05.Fragments.ContactFragment;
import com.path_studio.submission_05.Fragments.HomeFragment;
import com.path_studio.submission_05.Fragments.MovieFragment;
import com.path_studio.submission_05.Fragments.TvShowFragment;
import com.path_studio.submission_05.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private ViewPager viewPager;

    public SectionsPagerAdapter(Context context, FragmentManager fm, ViewPager viewPager) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.viewPager = viewPager;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
            R.string.tab_text_4,
            R.string.tab_text_5

    };
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment(viewPager);
                break;
            case 1:
                fragment = new MovieFragment();
                break;
            case 2:
                fragment = new TvShowFragment();
                break;
            case 3:
                fragment = new FavouriteFragment(viewPager);
                break;
            case 4:
                fragment = new ContactFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
    @Override
    public int getCount() {
        return 5;
    }

}