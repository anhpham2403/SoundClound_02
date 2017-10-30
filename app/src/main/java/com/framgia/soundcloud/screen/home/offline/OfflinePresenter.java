package com.framgia.soundcloud.screen.home.offline;

import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.source.TrackRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link OfflineFragment}), retrieves the data and updates
 * the UI as required.
 */
final class OfflinePresenter implements OfflineContract.Presenter {
    private static final String TAG = OfflinePresenter.class.getName();

    private OfflineContract.ViewModel mViewModel;
    private TrackRepository mRepository;

    public OfflinePresenter(TrackRepository repository) {
        mRepository = repository;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(OfflineContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getTracks() {
        mRepository.getTracks()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Track>>() {
                    @Override
                    public void onNext(List<Track> value) {
                        mViewModel.onGetTracksSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onGetTracksFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
