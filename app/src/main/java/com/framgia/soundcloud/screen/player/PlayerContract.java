package com.framgia.soundcloud.screen.player;

import com.framgia.soundcloud.screen.BasePresenter;
import com.framgia.soundcloud.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface PlayerContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onDestroy();

        void onPlayTrack();

        void onPauseTrack();

        void onNextTrack();

        void onPrevTrack();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
    }
}
