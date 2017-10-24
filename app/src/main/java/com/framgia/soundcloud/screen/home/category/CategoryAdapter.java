package com.framgia.soundcloud.screen.home.category;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.databinding.ItemCategoryBinding;
import java.util.List;

/**
 * Created by anh on 23/10/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BindingHolder> {
    private List<Category> mCategories;
    private OnItemClickListener mListener;

    public CategoryAdapter(List<Category> categories, OnItemClickListener listener) {
        mCategories = categories;
        mListener = listener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false);
        return new BindingHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bind(mCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mCategories != null ? mCategories.size() : 0;
    }

    /**
     * OnRecyclerViewItemClickListener
     */
    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    /**
     * hold item
     */
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding mBinding;
        private OnItemClickListener mListener;

        public BindingHolder(ItemCategoryBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
        }

        public void bind(Category category) {
            if (category != null) {
                mBinding.setViewModel(category);
                mBinding.setListener(mListener);
                mBinding.executePendingBindings();
            }
        }
    }
}
