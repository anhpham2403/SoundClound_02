package com.framgia.soundcloud.screen.home.offline;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
    private static final int REQUEST_CODE = 100;
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
        if (isGrantPermission()) {
            mViewModel.onGetData();
        } else {
            requestPermission();
        }
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

    protected boolean isGrantPermission() {
        return ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mViewModel.onGetData();
                }
                break;
            default:
                break;
        }
    }
}
