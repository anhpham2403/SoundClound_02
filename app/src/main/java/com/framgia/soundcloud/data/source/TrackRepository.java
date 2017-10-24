package com.framgia.soundcloud.data.source;

import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.source.remote.TrackRemoteDataSource;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;

/**
 * Created by anh on 23/10/2017.
 */

public class TrackRepository implements TrackDataSource {
    private TrackRemoteDataSource mRemoteDataSource;

    public TrackRepository(TrackRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Track>> getTracks(Map<String, String> params) {
        return mRemoteDataSource.getTracks(params);
    }
}
