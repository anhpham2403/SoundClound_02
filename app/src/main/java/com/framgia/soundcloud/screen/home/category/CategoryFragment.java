package com.framgia.soundcloud.screen.home.category;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.databinding.FragmentCategoryBinding;
import com.framgia.soundcloud.screen.BaseFragment;

/**
 * Category Screen.
 */
public class CategoryFragment extends BaseFragment {

    private CategoryContract.ViewModel mViewModel;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] params = getResources().getStringArray(R.array.params);
        String[] names = getResources().getStringArray(R.array.names);
        mViewModel = new CategoryViewModel(names, params);

        CategoryContract.Presenter presenter = new CategoryPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentCategoryBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        binding.setViewModel((CategoryViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
