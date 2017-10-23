package com.framgia.soundcloud.screen.home.category;

/**
 * Exposes the data to be used in the Category screen.
 */

public class CategoryViewModel implements CategoryContract.ViewModel {

    private CategoryContract.Presenter mPresenter;

    public CategoryViewModel() {
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
    }
}
