package com.framgia.soundcloud.screen.player;

/**
 * Listens to user actions from the UI ({@link PlayerFragment}), retrieves the data and updates
 * the UI as required.
 */
final class PlayerPresenter implements PlayerContract.Presenter {
    private static final String TAG = PlayerPresenter.class.getName();
    private PlayerContract.ViewModel mViewModel;

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(PlayerContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
