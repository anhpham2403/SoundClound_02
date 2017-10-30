package com.framgia.soundcloud.screen.detail;

/**
 * Listens to user actions from the UI ({@link DetailActivity}), retrieves the data and updates
 * the UI as required.
 */
final class DetailPresenter implements DetailContract.Presenter {
    private static final String TAG = DetailPresenter.class.getName();
    private static final int PERCENT = 100;
    private DetailContract.ViewModel mViewModel;

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(DetailContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void updateSeekBar(int duration, int fullDuration) {
        int progressPercentage = duration * PERCENT / fullDuration;
        mViewModel.setSeekBar(progressPercentage);
    }
}
