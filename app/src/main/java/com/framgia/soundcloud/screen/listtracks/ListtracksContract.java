package com.framgia.soundcloud.screen.listtracks;

import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.screen.BasePresenter;
import com.framgia.soundcloud.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ListtracksContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetTrackSuccess(List<Track> tracks);

        void onGetTrackFailure(String msg);

        void onDestroy();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getTracks();

        void onDestroy();
    }
}
