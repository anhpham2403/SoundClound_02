package com.framgia.soundcloud.screen.home.category;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.screen.listtracks.ListtracksActivity;
import java.util.List;

/**
 * Exposes the data to be used in the Category screen.
 */

public class CategoryViewModel extends BaseObservable
        implements CategoryContract.ViewModel, CategoryAdapter.OnItemClickListener {

    private CategoryContract.Presenter mPresenter;
    private CategoryAdapter mAdapter;
    private Context mContext;

    public CategoryViewModel(CategoryContract.Presenter presenter, String[] names, String[] params,
            Context context) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mPresenter.getCategory(names, params);
        mContext = context;
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
    public void showCategory(List<Category> categories) {
        mAdapter = new CategoryAdapter(categories, this);
    }

    @Bindable
    public CategoryAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onItemClick(Category category) {
        mContext.startActivity(ListtracksActivity.getCategory(mContext, category));
    }
}
