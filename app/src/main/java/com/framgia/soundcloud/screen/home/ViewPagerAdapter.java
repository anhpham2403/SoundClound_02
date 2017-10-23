package com.framgia.soundcloud.screen.home;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.screen.home.category.CategoryFragment;
import com.framgia.soundcloud.screen.home.offline.OfflineFragment;

/**
 * Created by anh on 19/10/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int COUNT_FRAGMENT = 2;
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TabMode.CATEGORY:
                return CategoryFragment.newInstance();
            case TabMode.OFFLINE:
                return OfflineFragment.newInstance();
            default:
                return CategoryFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Resources res = mContext.getResources();
        String[] tabNames = res.getStringArray(R.array.tab_name);
        switch (position) {
            case TabMode.CATEGORY:
                return tabNames[TabMode.CATEGORY];
            case TabMode.OFFLINE:
                OfflineFragment.newInstance();
                return tabNames[TabMode.OFFLINE];
            default:
                return tabNames[TabMode.CATEGORY];
        }
    }

    @Override
    public int getCount() {
        return COUNT_FRAGMENT;
    }
}
