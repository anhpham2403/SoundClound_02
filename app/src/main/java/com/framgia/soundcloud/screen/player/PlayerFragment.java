package com.framgia.soundcloud.screen.player;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.soundcloud.App;
import com.framgia.soundcloud.R;
import com.framgia.soundcloud.databinding.FragmentPlayerBinding;
import com.framgia.soundcloud.screen.BaseFragment;
import javax.inject.Inject;

/**
 * Player Screen.
 */
public class PlayerFragment extends BaseFragment {
    @Inject
    PlayerContract.ViewModel mViewModel;

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerPlayerComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .playerModule(new PlayerModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        FragmentPlayerBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false);
        binding.setViewModel((PlayerViewModel) mViewModel);
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

    @Override
    public void onDestroy() {
        mViewModel.onDestroy();
        super.onDestroy();
    }
}
