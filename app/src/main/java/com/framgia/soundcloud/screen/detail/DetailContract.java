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
        void onDestroy();

        void onPlayTrack();

        void onPauseTrack();

        void onNextTrack();

        void onPrevTrack();

        boolean isPlaying();

        void setSeekBar(int progressPercentage);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void updateSeekBar(int duration, int fullDuration);
    }
}
