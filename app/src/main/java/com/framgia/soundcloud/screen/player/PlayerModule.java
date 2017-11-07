package com.framgia.soundcloud.screen.player;

import android.content.Context;
import android.support.v4.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by anh on 08/11/2017.
 */
@Module
public class PlayerModule {
    private Fragment mFragment;

    public PlayerModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    public PlayerContract.ViewModel providePlayerViewModel(Context context,
            PlayerContract.Presenter presenter) {
        return new PlayerViewModel(context, presenter);
    }

    @Provides
    public PlayerContract.Presenter providePlayerPresenter() {
        return new PlayerPresenter();
    }
}
