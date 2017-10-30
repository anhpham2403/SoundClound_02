package com.framgia.soundcloud.data.source.remote;

import com.framgia.soundcloud.data.model.TrackResponse;
import com.framgia.soundcloud.data.source.remote.service.TrackApi;
import io.reactivex.Observable;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by anh on 21/09/2017.
 */

public class TrackRemoteDataSource extends BaseRemoteDataSource {
    @Inject
    public TrackRemoteDataSource(TrackApi trackApi) {
        super(trackApi);
    }

    @Override
    public Observable<TrackResponse> getTracks(Map<String, String> params) {
        return mTrackApi.getTracks(params);
    }

    @Override
    public Observable<TrackResponse> getNextHref(String url) {
        return mTrackApi.getNextHref(url);
    }
}
