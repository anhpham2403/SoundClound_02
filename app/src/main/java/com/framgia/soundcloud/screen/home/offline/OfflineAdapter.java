package com.framgia.soundcloud.screen.home.offline;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.databinding.ItemTrackOfflineBinding;
import java.util.List;

/**
 * Created by anh on 26/10/2017.
 */

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.BindingHolder> {
    private List<Track> mTracks;
    private OnItemClickListener mListener;

    public OfflineAdapter(List<Track> tracks, OnItemClickListener listener) {
        mTracks = tracks;
        mListener = listener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTrackOfflineBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_track_offline, parent, false);
        return new BindingHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mTracks.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public void updateAdapter(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    /**
     * OnItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(int postion);
    }

    /**
     * hold item
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemTrackOfflineBinding mBinding;
        private OfflineAdapter.OnItemClickListener mListener;

        public BindingHolder(ItemTrackOfflineBinding binding,
                OfflineAdapter.OnItemClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
        }

        public void bind(Track track, int postion) {
            if (track != null) {
                mBinding.setViewModel(track);
                mBinding.setPostion(postion);
                mBinding.setListener(mListener);
                mBinding.executePendingBindings();
            }
        }
    }
}
