package com.framgia.soundcloud.screen.home.offline;

import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.screen.BasePresenter;
import com.framgia.soundcloud.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface OfflineContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetTracksSuccess(List<Track> tracks);

        void onGetTracksFailure(String msg);

        void onGetData();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getTracks();
    }
}
