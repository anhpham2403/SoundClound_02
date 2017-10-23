package com.framgia.soundcloud.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by anh on 23/10/2017.
 */

public class TrackResponse {
    @SerializedName("collection")
    private List<Track> mTracks;
    @SerializedName("next_href")
    private String mNextHref;

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }
}
