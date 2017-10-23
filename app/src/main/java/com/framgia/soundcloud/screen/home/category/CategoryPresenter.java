package com.framgia.soundcloud.screen.home.category;

/**
 * Listens to user actions from the UI ({@link CategoryFragment}), retrieves the data and updates
 * the UI as required.
 */
final class CategoryPresenter implements CategoryContract.Presenter {
    private static final String TAG = CategoryPresenter.class.getName();

    private final CategoryContract.ViewModel mViewModel;

    public CategoryPresenter(CategoryContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
