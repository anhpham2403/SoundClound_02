package com.framgia.soundcloud.screen.home.banner;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.databinding.FragmentBannerBinding;
import com.framgia.soundcloud.screen.BaseFragment;

/**
 * Banner Screen.
 */
public class BannerFragment extends BaseFragment {

    private BannerContract.ViewModel mViewModel;

    public static BannerFragment newInstance() {
        return new BannerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new BannerViewModel();

        BannerContract.Presenter presenter = new BannerPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentBannerBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_banner, container, false);
        binding.setViewModel((BannerViewModel) mViewModel);
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
