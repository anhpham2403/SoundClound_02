package com.framgia.soundcloud.screen.home.banner;

/**
 * Exposes the data to be used in the Banner screen.
 */

public class BannerViewModel implements BannerContract.ViewModel {

    private BannerContract.Presenter mPresenter;

    public BannerViewModel(BannerContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

}
