package com.framgia.soundcloud.screen.home.category;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.framgia.soundcloud.data.model.Category;
import java.util.List;

/**
 * Exposes the data to be used in the Category screen.
 */

public class CategoryViewModel extends BaseObservable
        implements CategoryContract.ViewModel, CategoryAdapter.OnItemClickListener {

    private CategoryContract.Presenter mPresenter;
    private CategoryAdapter mAdapter;
    private String[] mParams;
    private String[] mNames;

    public CategoryViewModel(String[] names, String[] params) {
        mNames = names;
        mParams = params;
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
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.getCategory(mNames, mParams);
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
        //TODO
    }
}
