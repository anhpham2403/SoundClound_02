package com.framgia.soundcloud.screen.home.offline;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.databinding.FragmentOfflineBinding;
import com.framgia.soundcloud.screen.BaseFragment;

/**
 * Offline Screen.
 */
public class OfflineFragment extends BaseFragment {

    private OfflineContract.ViewModel mViewModel;

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new OfflineViewModel();

        OfflineContract.Presenter presenter = new OfflinePresenter(mViewModel);
        mViewModel.setPresenter(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentOfflineBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_offline, container, false);
        binding.setViewModel((OfflineViewModel) mViewModel);
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
