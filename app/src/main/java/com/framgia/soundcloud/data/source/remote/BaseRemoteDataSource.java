package com.framgia.soundcloud.data.source.remote;

import com.framgia.soundcloud.data.source.TrackDataSource;
import com.framgia.soundcloud.data.source.remote.service.TrackApi;

/**
 * Created by anh on 21/09/2017.
 */

public abstract class BaseRemoteDataSource implements TrackDataSource {
    TrackApi mTrackApi;

    public BaseRemoteDataSource(TrackApi trackApi) {
        mTrackApi = trackApi;
    }
}
