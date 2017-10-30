package com.framgia.soundcloud.screen.home.offline;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.screen.detail.DetailActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Offline screen.
 */

public class OfflineViewModel extends BaseObservable
        implements OfflineContract.ViewModel, OfflineAdapter.OnItemClickListener {
    private OfflineContract.Presenter mPresenter;
    private OfflineAdapter mAdapter;
    private Context mContext;
    private List<Track> mTracks;

    public OfflineViewModel(Context context, OfflineContract.Presenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mTracks = new ArrayList<>();
        mAdapter = new OfflineAdapter(mTracks, this);
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
    public void onLoadTrack(List<Track> tracks) {
        mTracks.addAll(tracks);
        mAdapter.updateAdapter(tracks);
    }

    @Override
    public void onItemClick(int postion) {
        mContext.startActivity(DetailActivity.getIntentDetailActivity(mContext, postion));
        DetailActivity.setsTracks(mTracks);
    }

    @Bindable
    public OfflineAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onGetData() {
        mPresenter.getTracks();
    }
}
