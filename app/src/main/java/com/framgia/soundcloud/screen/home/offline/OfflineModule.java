package com.framgia.soundcloud.screen.home.offline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.soundcloud.data.source.TrackRepository;
import com.framgia.soundcloud.data.source.local.TrackLocalDataSource;
import com.framgia.soundcloud.data.source.remote.TrackRemoteDataSource;
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
    public OfflineContract.ViewModel provideViewModel(Context context,
            OfflineContract.Presenter presenter) {
        return new OfflineViewModel(context, presenter);
    }

    @Provides
    public OfflineContract.Presenter providePresenter(TrackRepository repository) {
        return new OfflinePresenter(repository);
    }

    @Provides
    public TrackRepository provideTrackRepository(TrackRemoteDataSource remoteDataSource,
            TrackLocalDataSource localDataSource) {
        return new TrackRepository(remoteDataSource, localDataSource);
    }
}
