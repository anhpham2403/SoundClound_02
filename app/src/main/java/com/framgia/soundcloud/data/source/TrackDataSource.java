package com.framgia.soundcloud.data.source;

import com.framgia.soundcloud.data.model.TrackResponse;
import io.reactivex.Observable;
import java.util.Map;

public interface TrackDataSource {
    Observable<TrackResponse> getTracks(Map<String, String> params);

    Observable<TrackResponse> getNextHref(String url);
}