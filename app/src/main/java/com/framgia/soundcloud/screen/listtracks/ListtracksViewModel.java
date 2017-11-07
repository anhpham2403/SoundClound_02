package com.framgia.soundcloud.screen.listtracks;

import android.app.ActivityManager;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.model.TrackResponse;
import com.framgia.soundcloud.screen.detail.DetailActivity;
import com.framgia.soundcloud.screen.player.PlayerFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Listtracks screen.
 */

public class ListtracksViewModel extends BaseObservable
        implements ListtracksContract.ViewModel, ListtrackAdapter.OnItemClickListener {
    private static final String SERVICE = "com.framgia.soundcloud.service.MusicService";
    private List<Track> mTracks;
    private ListtracksContract.Presenter mPresenter;
    private ListtrackAdapter mAdapter;
    private Context mContext;
    private boolean mIsLoading;
    private String mUrl;
    private Fragment mFragment;
    private FragmentManager mManager;
    private boolean mIsServiceRunning;
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy <= 0) {
                return;
            }
            LinearLayoutManager layoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
            if (!isLoading() && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                setLoading(true);
                mPresenter.loadMoreTracks(mUrl);
            }
        }
    };

    public ListtracksViewModel(Context context, ListtracksContract.Presenter presenter,
            FragmentManager manager) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mIsLoading = true;
        mTracks = new ArrayList<>();
        setAdapter(new ListtrackAdapter(mTracks, this));
        mManager = manager;
        mFragment = PlayerFragment.newInstance();
        mPresenter.getTracks();
    }

    public boolean check() {
        ActivityManager manager =
                (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (SERVICE.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
        setServiceRunning(check());
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onGetTrackSuccess(TrackResponse tracks) {
        setLoading(false);
        mTracks.addAll(tracks.getTracks());
        mAdapter.updateAdapter(tracks.getTracks());
        mUrl = tracks.getNextHref();
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
        DetailActivity.setsTracks(mTracks);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public RecyclerView.OnScrollListener getScrollListener() {
        return mScrollListener;
    }

    @Bindable
    public Fragment getFragment() {
        return mFragment;
    }

    @Bindable
    public FragmentManager getManager() {
        return mManager;
    }

    @Bindable
    public boolean isServiceRunning() {
        return mIsServiceRunning;
    }

    public void setServiceRunning(boolean serviceRunning) {
        mIsServiceRunning = serviceRunning;
        notifyPropertyChanged(BR.serviceRunning);
    }
}
