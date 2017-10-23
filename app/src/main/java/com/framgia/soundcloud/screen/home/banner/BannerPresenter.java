package com.framgia.soundcloud.screen.home.banner;

/**
 * Listens to user actions from the UI ({@link BannerFragment}), retrieves the data and updates
 * the UI as required.
 */
final class BannerPresenter implements BannerContract.Presenter {
    private static final String TAG = BannerPresenter.class.getName();

    private final BannerContract.ViewModel mViewModel;

    public BannerPresenter(BannerContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
