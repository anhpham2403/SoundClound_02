package com.framgia.soundcloud.screen.home;

/**
 * Exposes the data to be used in the Home screen.
 */

public class HomeViewModel implements HomeContract.ViewModel {

    private HomeContract.Presenter mPresenter;

    public HomeViewModel() {
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
}
