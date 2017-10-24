package com.framgia.soundcloud.screen.home.category;

import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.utils.Constant;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void getCategory(String[] names, String[] params) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            categories.add(new Category(Constant.IMAGE_CATEGORY[i], names[i], params[i]));
        }
        mViewModel.showCategory(categories);
    }
}
