package com.framgia.soundcloud.data.source.local;

import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.source.TrackDataSource;
import com.framgia.soundcloud.data.source.local.provider.ContentResolverTrack;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by anh on 03/11/2017.
 */

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    ContentResolverTrack mResolverTrack;

    @Inject
    public TrackLocalDataSource(ContentResolverTrack resolverTrack) {
        mResolverTrack = resolverTrack;
    }

    @Override
    public Observable<List<Track>> getTracks() {
        return mResolverTrack.getTracks();
    }
}
