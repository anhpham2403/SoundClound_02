package com.framgia.soundcloud.screen.listtracks;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.data.source.TrackRepository;
import com.framgia.soundcloud.data.source.local.TrackLocalDataSource;
import com.framgia.soundcloud.data.source.remote.TrackRemoteDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * Created by anh on 25/10/2017.
 */
@Module
public class ListtracksModule {
    private Activity mActivity;
    private Category mCategory;
    private FragmentManager mManager;

    public ListtracksModule(@NonNull Activity activity, Category category, FragmentManager manager) {
        mActivity = activity;
        mCategory = category;
        mManager = manager;
    }

    @Provides
    public ListtracksContract.ViewModel provideViewModel(Context context,
            ListtracksContract.Presenter presenter) {
        return new ListtracksViewModel(context, presenter, mManager);
    }

    @Provides
    public ListtracksContract.Presenter providePresenter(TrackRepository repository) {
        return new ListtracksPresenter(repository, mCategory);
    }

    @Provides
    public TrackRepository provideTrackRepository(TrackRemoteDataSource remoteDataSource,
            TrackLocalDataSource localDataSource) {
        return new TrackRepository(remoteDataSource, localDataSource);
    }
}
