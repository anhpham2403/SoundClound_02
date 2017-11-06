package com.framgia.soundcloud.screen.detail;

import com.framgia.soundcloud.data.source.local.sharedpref.SharedPrefsImplement;
import com.framgia.soundcloud.utils.Constant;

/**
 * Listens to user actions from the UI ({@link DetailActivity}), retrieves the data and updates
 * the UI as required.
 */
final class DetailPresenter implements DetailContract.Presenter {
    private static final String TAG = DetailPresenter.class.getName();
    private static final int PERCENT = 100;
    private DetailContract.ViewModel mViewModel;
    private SharedPrefsImplement mImplement;

    public DetailPresenter(SharedPrefsImplement implement) {
        mImplement = implement;
    }

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

    @Override
    public void getStateMediaPlayer() {
        mViewModel.setStateShuffle(mImplement.get(Constant.PREF_IS_SUFFLE, Boolean.class));
        mViewModel.setStateLoop(mImplement.get(Constant.PREF_LOOP_MODE, Integer.class));
    }
}
