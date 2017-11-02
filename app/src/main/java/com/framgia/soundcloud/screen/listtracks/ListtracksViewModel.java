package com.framgia.soundcloud.screen.listtracks;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.framgia.soundcloud.BR;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.model.TrackResponse;
import com.framgia.soundcloud.screen.detail.DetailActivity;
import java.util.ArrayList;
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
    private String mUrl;
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

    public ListtracksViewModel(Context context, ListtracksContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mPresenter.getTracks();
        mContext = context;
        mIsLoading = true;
        setsTracks(new ArrayList<Track>());
        setAdapter(new ListtrackAdapter(sTracks, this));
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
    public void onGetTrackSuccess(TrackResponse tracks) {
        setLoading(false);
        setsTracks(tracks.getTracks());
        mAdapter.updateAdapter(sTracks);
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
}
