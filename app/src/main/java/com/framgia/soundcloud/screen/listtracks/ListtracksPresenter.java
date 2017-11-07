package com.framgia.soundcloud.screen.listtracks;

import com.framgia.soundcloud.BuildConfig;
import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.data.model.TrackResponse;
import com.framgia.soundcloud.data.source.TrackRepository;
import com.framgia.soundcloud.utils.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.Map;

/**
 * Listens to user actions from the UI ({@link ListtracksActivity}), retrieves the data and updates
 * the UI as required.
 */
final class ListtracksPresenter implements ListtracksContract.Presenter {
    private static final String TAG = ListtracksPresenter.class.getName();

    private ListtracksContract.ViewModel mViewModel;
    private TrackRepository mRepository;
    private CompositeDisposable mDisposable;
    private Category mCategory;

    public ListtracksPresenter(TrackRepository repository, Category category) {
        mRepository = repository;
        mDisposable = new CompositeDisposable();
        mCategory = category;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ListtracksContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getTracks() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.Param.GENRES, mCategory.getParam());
        params.put(Constant.Param.CLIENT_ID, BuildConfig.API_KEY);
        params.put(Constant.Param.LINKED_PARTITIONING, Constant.NUMBER_LINKED_PARTITIONING);
        params.put(Constant.Param.LIMIT, Constant.PAGE_SIZE);
        params.put(Constant.Param.OFFSET, Constant.PAGE_SIZE);
        mDisposable.add(mRepository.getTracks(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<TrackResponse>() {
                    @Override
                    public void onNext(TrackResponse value) {
                        mViewModel.onGetTrackSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onGetTrackFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
    }

    @Override
    public void loadMoreTracks(String nextHref) {
        mDisposable.add(mRepository.getNextHref(nextHref)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TrackResponse>() {
                    @Override
                    public void onNext(TrackResponse value) {
                        mViewModel.onGetTrackSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.onGetTrackFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
