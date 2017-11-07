package com.framgia.soundcloud.screen.home;

import android.app.ActivityManager;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.screen.player.PlayerFragment;

/**
 * Exposes the data to be used in the Home screen.
 */

public class HomeViewModel extends BaseObservable implements HomeContract.ViewModel {
    private static final String SERVICE = "com.framgia.soundcloud.service.MusicService";
    private HomeContract.Presenter mPresenter;
    private ViewPagerAdapter mAdapter;
    private Fragment mFragment;
    private FragmentManager mManager;
    private boolean mIsServiceRunning;
    private Context mContext;

    public HomeViewModel(HomeContract.Presenter presenter, FragmentManager fragmentManager,
            Context context) {
        mPresenter = presenter;
        mAdapter = new ViewPagerAdapter(fragmentManager, context);
        mManager = fragmentManager;
        mFragment = PlayerFragment.newInstance();
        mContext = context;
    }

    public boolean check() {
        ActivityManager manager =
                (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (SERVICE.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
        setServiceRunning(check());
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Bindable
    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }

    @Bindable
    public Fragment getFragment() {
        return mFragment;
    }

    @Bindable
    public FragmentManager getManager() {
        return mManager;
    }

    @Bindable
    public boolean isServiceRunning() {
        return mIsServiceRunning;
    }

    public void setServiceRunning(boolean serviceRunning) {
        mIsServiceRunning = serviceRunning;
        notifyPropertyChanged(BR.serviceRunning);
    }
}

