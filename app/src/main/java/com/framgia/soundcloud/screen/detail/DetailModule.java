package com.framgia.soundcloud.screen.detail;

import android.app.Activity;
import android.content.Context;
import com.framgia.soundcloud.data.model.Track;
import dagger.Module;
import dagger.Provides;
import java.util.List;

/**
 * Created by anh on 28/10/2017.
 */
@Module
public class DetailModule {
    private Activity mActivity;
    private List<Track> mTracks;
    private int mPostion;

    public DetailModule(Activity activity, List<Track> tracks, int postion) {
        mActivity = activity;
        mTracks = tracks;
        mPostion = postion;
    }

    @Provides
    public DetailContract.ViewModel provideViewModel(Context context,
            DetailContract.Presenter presenter) {
        return new DetailViewModel(context, presenter, mTracks, mPostion);
    }

    @Provides
    public DetailContract.Presenter providePresenter() {
        return new DetailPresenter();
    }

    @Provides
    public TrackDownloadManager provideDownloadTrack(Context context) {
        return new TrackDownloadManager(context, mTracks.get(mPostion).getTitle(),
                mTracks.get(mPostion).getUri());
    }
}
