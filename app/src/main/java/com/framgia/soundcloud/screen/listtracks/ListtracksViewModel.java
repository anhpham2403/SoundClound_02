package com.framgia.soundcloud.screen.listtracks;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.data.model.Track;
import java.util.List;

/**
 * Exposes the data to be used in the Listtracks screen.
 */

public class ListtracksViewModel extends BaseObservable
        implements ListtracksContract.ViewModel, ListtrackAdapter.OnItemClickListener {

    private ListtracksContract.Presenter mPresenter;
    private ListtrackAdapter mAdapter;
    private Context mContext;
    private boolean mIsLoading;

    public ListtracksViewModel(Context context, ListtracksContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mPresenter.getTracks();
        mContext = context;
        mIsLoading = true;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onGetTrackSuccess(List<Track> tracks) {
        setLoading(false);
        setAdapter(new ListtrackAdapter(tracks, this));
    }

    @Override
    public void onGetTrackFailure(String msg) {
        setLoading(false);
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
    }

    @Bindable
    public ListtrackAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ListtrackAdapter adapter) {
        mAdapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Override
    public void onItemClick(List<Track> tracks, int postion) {
        //TODO
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }
}
