package com.framgia.soundcloud.data.source.local.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.framgia.soundcloud.data.model.Track;
import com.framgia.soundcloud.data.model.User;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anh on 03/11/2017.
 */

public class ContentResolverTrack {
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private ContentResolver mContentResolver;

    public ContentResolverTrack(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public Observable<List<Track>> getTracks() {
        return Observable.create(new ObservableOnSubscribe<List<Track>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Track>> e) throws Exception {
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                Cursor cursor = mContentResolver.query(uri, null, null, null, null);
                List<Track> tracks = new ArrayList<>();
                if (cursor != null & cursor.moveToFirst()) {
                    do {
                        Track track = new Track();
                        track.setTitle(cursor.getString(
                                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                        track.setUser(new User(cursor.getString(
                                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))));
                        track.setArtworkUrl(String.valueOf(ContentUris.withAppendedId(sArtworkUri,
                                cursor.getInt(cursor.getColumnIndexOrThrow(
                                        MediaStore.Audio.Media.ALBUM_ID)))));
                        track.setDuration(cursor.getInt(
                                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                        track.setUri(cursor.getString(
                                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                        tracks.add(track);
                    } while (cursor.moveToNext());
                    cursor.close();
                    e.onNext(tracks);
                    e.onComplete();
                }
            }
        });
    }
}