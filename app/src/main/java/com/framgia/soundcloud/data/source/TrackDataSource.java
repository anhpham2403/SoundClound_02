package com.framgia.soundcloud.data.source;

import com.framgia.soundcloud.data.model.Track;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;

public interface TrackDataSource {
    Observable<List<Track>> getTracks(Map<String, String> params);
}