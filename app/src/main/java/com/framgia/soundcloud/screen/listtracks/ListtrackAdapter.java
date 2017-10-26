package com.framgia.soundcloud.screen.listtracks;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.databinding.ItemTrackBinding;
import java.util.List;

/**
 * Created by anh on 26/10/2017.
 */

public class ListtrackAdapter extends RecyclerView.Adapter<ListtrackAdapter.BindingHolder> {
    private List<Track> mTracks;
    private OnItemClickListener mListener;

    public ListtrackAdapter(List<Track> tracks, OnItemClickListener listener) {
        mTracks = tracks;
        mListener = listener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTrackBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_track, parent, false);
        return new BindingHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mTracks, position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(List<Track> tracks, int postion);
    }

    /**
     * hold item
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemTrackBinding mBinding;
        private ListtrackAdapter.OnItemClickListener mListener;

        public BindingHolder(ItemTrackBinding binding,
                ListtrackAdapter.OnItemClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
        }

        public void bind(List<Track> tracks, int postion) {
            if (tracks.get(postion) != null) {
                mBinding.setViewModel(tracks.get(postion));
                mBinding.setTracks(tracks);
                mBinding.setListener(mListener);
                mBinding.executePendingBindings();
            }
        }
    }
}
