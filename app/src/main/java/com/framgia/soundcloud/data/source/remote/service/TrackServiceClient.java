package com.framgia.soundcloud.data.source.remote.service;

import com.framgia.soundcloud.utils.Constant;

/**
 * Created by anh on 21/09/2017.
 */

public class TrackServiceClient extends ServiceClient {
    private static TrackApi mTrackApi;

    public static TrackApi getInstance() {
        if (mTrackApi == null) {
            mTrackApi = createService(Constant.BASE_URL, TrackApi.class);
        }
        return mTrackApi;
    }
}
