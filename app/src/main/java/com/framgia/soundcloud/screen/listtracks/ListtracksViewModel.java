package com.framgia.soundcloud.screen.listtracks;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.screen.detail.DetailActivity;
import java.util.List;

/**
 * Exposes the data to be used in the Listtracks screen.
 */

public class ListtracksViewModel extends BaseObservable
        implements ListtracksContract.ViewModel, ListtrackAdapter.OnItemClickListener {

    private static List<Track> sTracks;
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

    public static List<Track> getsTracks() {
        return sTracks;
    }

    public static void setsTracks(List<Track> sTracks) {
        ListtracksViewModel.sTracks = sTracks;
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
        setsTracks(tracks);
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
        mContext.startActivity(DetailActivity.getIntentDetailActivity(mContext, postion));
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
