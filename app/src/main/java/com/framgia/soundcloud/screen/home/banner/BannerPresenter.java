package com.framgia.soundcloud.screen.home.banner;

/**
 * Listens to user actions from the UI ({@link BannerFragment}), retrieves the data and updates
 * the UI as required.
 */
final class BannerPresenter implements BannerContract.Presenter {
    private static final String TAG = BannerPresenter.class.getName();

    private BannerContract.ViewModel mViewModel;

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(BannerContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
