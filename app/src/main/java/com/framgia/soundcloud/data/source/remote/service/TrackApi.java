package com.framgia.soundcloud.data.source.remote.service;

import com.framgia.soundcloud.data.model.TrackResponse;
import io.reactivex.Observable;
import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by anh on 23/10/2017.
 */

public interface TrackApi {
    @GET("/tracks")
    Observable<TrackResponse> getTracks(@QueryMap Map<String, String> params);
}
