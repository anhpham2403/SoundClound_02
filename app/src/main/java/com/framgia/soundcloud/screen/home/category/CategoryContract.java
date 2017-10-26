package com.framgia.soundcloud.screen.home.category;

import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.screen.BasePresenter;
import com.framgia.soundcloud.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface CategoryContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void showCategory(List<Category> categories);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getCategory(String[] categories, String[] params);
    }
}
