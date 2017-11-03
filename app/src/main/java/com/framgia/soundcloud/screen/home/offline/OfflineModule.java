package com.framgia.soundcloud.screen.home.offline;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by anh on 02/11/2017.
 */
@Module
public class OfflineModule {
    private Fragment mFragment;

    public OfflineModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    public OfflineContract.ViewModel provideViewModel(OfflineContract.Presenter presenter) {
        return new OfflineViewModel(presenter);
    }

    @Provides
    public OfflineContract.Presenter providePresenter() {
        return new OfflinePresenter();
    }
}
