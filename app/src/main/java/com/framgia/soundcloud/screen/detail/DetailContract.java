package com.framgia.soundcloud.screen.detail;

import com.framgia.soundcloud.screen.BasePresenter;
import com.framgia.soundcloud.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface DetailContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void setStateLoop(int loop);

        void setStateShuffle(boolean isShuffle);

        void onDestroy();

        void onPlayTrack();

        void onPauseTrack();

        void onNextTrack();

        void onPrevTrack();

        void onSuffleTrackClick();

        void onLoopTrackClick();

        void setSeekBar(int progressPercentage);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void updateSeekBar(int duration, int fullDuration);

        void getStateMediaPlayer();
    }
}
