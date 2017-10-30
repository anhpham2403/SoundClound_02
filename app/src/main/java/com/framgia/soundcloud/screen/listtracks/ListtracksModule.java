package com.framgia.soundcloud.screen.listtracks;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.soundcloud.data.model.Category;
import com.framgia.soundcloud.data.source.TrackRepository;
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

    public ListtracksModule(@NonNull Activity activity, Category category) {
        mActivity = activity;
        mCategory = category;
    }

    @Provides
    public ListtracksContract.ViewModel provideViewModel(Context context,
            ListtracksContract.Presenter presenter) {
        return new ListtracksViewModel(context, presenter);
    }

    @Provides
    public ListtracksContract.Presenter providePresenter(TrackRepository repository) {
        return new ListtracksPresenter(repository, mCategory);
    }

    @Provides
    public TrackRepository provideTrackRepository(TrackRemoteDataSource dataSource) {
        return new TrackRepository(dataSource);
    }
}
