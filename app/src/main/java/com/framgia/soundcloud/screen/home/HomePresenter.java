package com.framgia.soundcloud.screen.home;

/**
 * Listens to user actions from the UI ({@link HomeActivity}), retrieves the data and updates
 * the UI as required.
 */
final class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = HomePresenter.class.getName();

    private HomeContract.ViewModel mViewModel;

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(HomeContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
