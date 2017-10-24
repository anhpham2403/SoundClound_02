package com.framgia.soundcloud.data.source.remote;

import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.model.TrackResponse;
import com.framgia.soundcloud.data.source.remote.service.TrackApi;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.List;
import java.util.Map;

/**
 * Created by anh on 21/09/2017.
 */

public class TrackRemoteDataSource extends BaseRemoteDataSource {
    public TrackRemoteDataSource(TrackApi trackApi) {
        super(trackApi);
    }

    @Override
    public Observable<List<Track>> getTracks(Map<String, String> params) {
        return mTrackApi.getTracks(params).map(new Function<TrackResponse, List<Track>>() {
            @Override
            public List<Track> apply(TrackResponse trackResponse) throws Exception {
                return trackResponse.getTracks();
            }
        });
    }
}
