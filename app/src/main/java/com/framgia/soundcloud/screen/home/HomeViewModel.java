package com.framgia.soundcloud.screen.home;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.FragmentManager;

/**
 * Exposes the data to be used in the Home screen.
 */

public class HomeViewModel extends BaseObservable implements HomeContract.ViewModel {

    private HomeContract.Presenter mPresenter;
    private ViewPagerAdapter mAdapter;

    public HomeViewModel(FragmentManager fragmentManager, Context context) {
        mAdapter = new ViewPagerAdapter(fragmentManager, context);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Bindable
    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }
}
