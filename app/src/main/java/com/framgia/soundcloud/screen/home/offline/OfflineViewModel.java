package com.framgia.soundcloud.screen.home.offline;

/**
 * Exposes the data to be used in the Offline screen.
 */

public class OfflineViewModel implements OfflineContract.ViewModel {

    private OfflineContract.Presenter mPresenter;

    public OfflineViewModel() {
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
    public void setPresenter(OfflineContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
