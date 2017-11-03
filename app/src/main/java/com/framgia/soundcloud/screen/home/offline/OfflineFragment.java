package com.framgia.soundcloud.screen.home.offline;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.soundcloud.App;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.databinding.FragmentOfflineBinding;
import com.framgia.soundcloud.screen.BaseFragment;
import javax.inject.Inject;

/**
 * Offline Screen.
 */
public class OfflineFragment extends BaseFragment {
    @Inject
    OfflineContract.ViewModel mViewModel;

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        DaggerOfflineComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .offlineModule(new OfflineModule(this))
                .build()
                .inject(this);
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
